import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-info-about-event-window-component',
  imports: [],
  templateUrl: './info-about-event-window-component.html',
  styleUrl: './info-about-event-window-component.css'
})
export class InfoAboutEventWindowComponent {
  @Input() infoAboutEvent: any;
}
