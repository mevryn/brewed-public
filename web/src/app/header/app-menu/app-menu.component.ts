import {Component, OnInit} from '@angular/core';
import {AppService} from '../../app.service';

@Component({
  selector: 'app-app-menu',
  templateUrl: './app-menu.component.html',
  styleUrls: ['./app-menu.component.css']
})
export class AppMenuComponent implements OnInit {

  constructor(private appService: AppService) {
  }

  ngOnInit(): void {
  }

  authenticated(): boolean {
    return this.appService.authenticated;
  }
}
