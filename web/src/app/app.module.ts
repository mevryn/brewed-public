import {BrowserModule, Title} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderModule} from './header/header.module';
import {ContainerModule} from './container/container.module';
import {SearchComponent} from './search/search.component';
import {PredictComponent} from './predict/predict.component';
import {FaqComponent} from './faq/faq.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatDialogModule} from '@angular/material/dialog';
import {CaptureComponent} from './capture/capture.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatTabsModule} from '@angular/material/tabs';
import {BeerTableModule} from './beer/beer-table/beer-table.module';
import {BeerDialogModule} from './beer/beer-dialog/beer-dialog.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppService} from './app.service';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {InformationDialogComponent} from './sharing/utilities/information-dialog/information-dialog.component';
import {BreweryListModule} from './brewery/brewery-list/brewery-list.module';
import {PrivacyComponent} from './privacy/privacy.component';
import {BeerRatingsModule} from './beer-ratings/beer-ratings.module';
import {BeerTypeModule} from './beer-type/beer-type.module';
import {BeerNoteModule} from './beer-note/beer-note.module';
import {BreweryModule} from './brewery/brewery.module';
import {HttpRequestInterceptor} from './http-request-interceptor';
import {MatIconModule} from '@angular/material/icon';
import {SharingModule} from './sharing/sharing.module';
import {AdminPanelModule} from './admin-panel/admin-panel.module';
import {MatCardModule} from '@angular/material/card';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    PredictComponent,
    FaqComponent,
    NotFoundComponent,
    CaptureComponent,
    ProfileComponent,
    HomeComponent,
    InformationDialogComponent,
    PrivacyComponent
  ],
  imports: [
    BrowserModule, BeerDialogModule,
    HttpClientModule,
    AdminPanelModule,
    AppRoutingModule,
    HeaderModule,
    ContainerModule,
    BrowserAnimationsModule,
    BreweryListModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    FormsModule,
    MatPaginatorModule,
    MatExpansionModule,
    MatTooltipModule,
    BeerTypeModule,
    BeerNoteModule,
    BreweryModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    BeerTableModule,
    AdminPanelModule,
    NgbModule
  ],
  providers: [AppService, {
    provide: HTTP_INTERCEPTORS,
    useClass: XhrInterceptor,
    multi: true
  }, {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true},
    Title,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}


