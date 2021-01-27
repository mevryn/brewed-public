import {Component, OnInit} from '@angular/core';
import {Title} from '@angular/platform-browser';
import {AppService} from './app.service';
import {LoadingService} from './loading.service';
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'Brewed';
  greeting = {};
  loading: any;

  public constructor(private _loading: LoadingService,
                     public appService: AppService, private titleService: Title) {
    this.setTitle(this.title);

  }


  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.listenToLoading();
  }

  private listenToLoading() {
    this._loading.loadingSub
      .pipe(delay(0)) // This prevents a ExpressionChangedAfterItHasBeenCheckedError for subsequent requests
      .subscribe((loading) => {
        this.loading = loading;
      });
  }
}

