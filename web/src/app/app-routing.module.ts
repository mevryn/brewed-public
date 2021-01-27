import {Injectable, NgModule} from '@angular/core';
import {CanActivate, RouterModule, Routes} from '@angular/router';
import {PredictComponent} from './predict/predict.component';
import {SearchComponent} from './search/search.component';
import {FaqComponent} from './faq/faq.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {PrivacyComponent} from './privacy/privacy.component';
import {AppService} from './app.service';
import {BeerRatingsComponent} from './beer-ratings/beer-ratings.component';

@Injectable()
class AuthGuardService implements CanActivate {

  constructor(private appService: AppService) {
  }

  canActivate() {
    return this.appService.authenticated;
  }
}

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'predict', canActivate: [AuthGuardService], component: PredictComponent},
  {path: 'search', canActivate: [AuthGuardService], component: SearchComponent},
  {path: 'faq', component: FaqComponent},
  {path: 'admin-panel', canActivate: [AuthGuardService], component: AdminPanelComponent},
  {path: 'beer-ratings', canActivate: [AuthGuardService], component: BeerRatingsComponent},
  {path: 'profile', canActivate: [AuthGuardService], component: ProfileComponent},
  {path: 'home', component: HomeComponent},
  {path: 'privacy', component: PrivacyComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
  providers: [AuthGuardService]
})
export class AppRoutingModule {
}


