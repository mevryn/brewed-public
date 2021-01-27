import {OnClickEvent} from './on-click-event';

export class DoNothingOnClickEvent implements OnClickEvent<any> {
  onClick(entry: any) {
  }
}
