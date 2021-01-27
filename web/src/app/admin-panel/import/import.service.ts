import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {AppService} from '../../app.service';

@Injectable({
  providedIn: 'root'
})
export class ImportService {

  importUrl = '/beer/import';

  constructor(private appService: AppService, private httpClient: HttpClient) {
  }


  onFileChange(event: Event): File {
    const target = event.target as HTMLInputElement;
    return (target.files as FileList)[0];
  }

  importFromFile(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('data', file);
    const header = new HttpHeaders();
    return this.httpClient.post(environment.serverUrl + this.importUrl, formData, this.appService.authenticatedHeaders);
  }
}
