import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {environment} from '../environments/environment';
import {InformationDialogFactory} from './sharing/information-dialog-factory';
import {Severity} from './sharing/utilities/InformationDialogUtils';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  authenticated = false;
  authenticatedHeaders: any;

  constructor(private dialogFactory: InformationDialogFactory, private http: HttpClient, private router: Router) {
  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});
    this.http.get(environment.serverUrl + '/user', {headers: headers}).subscribe(response => {
      this.authenticated = !!(response != undefined && response['name']);
      this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Logowanie powiodło się.');
      this.authenticatedHeaders = headers;
      return callback && callback();
    }, error => {
      this.dialogFactory.openInformationDialog(Severity.ERROR,
        'Logowanie nie powiodło się, złe login lub hasło.');
    });

  }

  logout() {
    this.router.navigateByUrl('/home');
    this.authenticated = false;
    this.authenticatedHeaders = undefined;
  }

}
