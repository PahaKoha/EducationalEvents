from __future__ import annotations

import json
import logging
import time

import pika
from pika.exceptions import AMQPChannelError, AMQPConnectionError

logger = logging.getLogger(__name__)


class RabbitConsumer:
    def __init__(self, rabbit_url: str, message_queue: str, reconnect_delay: int):
        self.rabbit_url = rabbit_url
        self.message_queue = message_queue
        self.reconnect_delay = reconnect_delay

    def connect(self):
        try:
            self.connection = pika.BlockingConnection(pika.URLParameters(self.rabbit_url))
            self.channel = self.connection.channel()
            self.channel.queue_declare(queue=self.message_queue, durable=True)

        except AMQPConnectionError as e:
            logger.error(f"Connection failed: {e}")
            self._reconnect()
        except Exception as e:
            logger.critical(f"Unexpected error: {e}", exc_info=True)
            raise

    def run(self):
        self.connect()
        self._start_consuming()

        while True:
            try:
                self.connection.process_data_events()
                if self.should_reconnect:
                    self._reconnect()
            except KeyboardInterrupt:
                logger.info("Keyboard interrupt received")
                self._close_connection()
                break
            except (AMQPConnectionError, AMQPChannelError):
                self._reconnect()
            except Exception as e:
                logger.critical(f"Unhandled error: {e}", exc_info=True)
                self._reconnect()

    def _close_connection(self):
        if self.connection and self.connection.is_open:
            logger.info("Closing connection...")
            self.connection.close()

    def _start_consuming(self):
        if self.channel:
            logger.info("Starting consumer...")
            self._consumer_tag = self.channel.basic_consume(
                queue=self.message_queue, on_message_callback=self._on_message, auto_ack=False
            )
            self.channel.start_consuming()

    def _reconnect(self):
        self.should_reconnect = True
        self._close_connection()

        logger.warning(f"Reconnecting in {self.reconnect_delay} seconds...")
        time.sleep(self.reconnect_delay)
        self.connect()
        self._start_consuming()

    def _on_message(self, channel, method, properties, body):
        try:
            message = json.loads(body)

            result = self._process_message(message)

            if result:
                channel.basic_ack(delivery_tag=method.delivery_tag)
                logger.info(f"Message processed: {method.delivery_tag}")
            else:
                channel.basic_nack(delivery_tag=method.delivery_tag, requeue=False)
                logger.warning(f"Message rejected: {method.delivery_tag}")
        except json.JSONDecodeError:
            logger.error("Invalid JSON format. Rejecting message")
            channel.basic_nack(delivery_tag=method.delivery_tag, requeue=False)
        except Exception as e:
            logger.error(f"Processing failed: {e}", exc_info=True)
            channel.basic_nack(delivery_tag=method.delivery_tag, requeue=False)

    def _process_message(self, message): ...
