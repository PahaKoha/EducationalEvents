import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        System.out.println(String.format(
            "Проверка выполнена в %s %s (msk)",
            ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("HH:mm"))
        ));
    }
}