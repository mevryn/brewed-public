import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {BeerDto} from '../model/BeerDto';

@Injectable({
  providedIn: 'root'
})
export class PredictService {
  file: File;
  endpoint: string = '/predict';

  constructor(private http: HttpClient) {
  }

  base64ToBlob(base64, mime): Blob {
    mime = mime || '';
    var sliceSize = 1024;
    var byteChars = window.atob(base64.replace(/^data:image\/(png|jpeg|jpg);base64,/, ''));
    var byteArrays = [];

    for (var offset = 0, len = byteChars.length; offset < len; offset += sliceSize) {
      var slice = byteChars.slice(offset, offset + sliceSize);

      var byteNumbers = new Array(slice.length);
      for (var i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      var byteArray = new Uint8Array(byteNumbers);

      byteArrays.push(byteArray);
    }

    return new Blob(byteArrays, {type: mime});
  }

  getPredictedBeer(): Observable<BeerDto[]> {
    const formData: FormData = new FormData();
    let fileToUpload;
    if (!(this.file instanceof Blob)) {
      fileToUpload = this.base64ToBlob(this.file, 'image/png');
    } else {
      fileToUpload = this.file;
    }
    formData.append('image', fileToUpload, 'image.png');
    return this.http.post<BeerDto[]>(environment.serverUrl + this.endpoint, formData);
  }

}
