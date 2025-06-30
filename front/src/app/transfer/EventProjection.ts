export interface EventProjection {
  id: string;

  name: string;
  description: string | null;
  isInternal: boolean;

  eventType: String;
  sphere: String;


  startAt: Date;
  endAt: Date;

  maxParticipants: number | null;
  infoLink: string | null;

  format: String | null;
}
