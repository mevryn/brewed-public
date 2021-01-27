import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {BeerNote} from '../model/BeerNote';
import {AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class BeerNoteService {


  noteUrl = '/beer-note';
  allUrl = '/all';
  private deleteGivenUrl = '/deleteGiven';

  constructor(private appService: AppService, private http: HttpClient) {
  }

  getNote(noteId: string): Observable<BeerNote> {
    return this.http.get<BeerNote>(environment.serverUrl + this.noteUrl + '/' + noteId);
  }

  getAllNotes(): Observable<BeerNote[]> {
    return this.http.get<BeerNote[]>(environment.serverUrl + this.noteUrl + this.allUrl);
  }

  postNote(entryData: BeerNote) {
    return this.http.post(environment.serverUrl + this.noteUrl, entryData, this.appService.authenticatedHeaders);
  }

  deleteNotes(rows: any[]) {
    return this.http.post(environment.serverUrl + this.noteUrl + this.deleteGivenUrl, rows, this.appService.authenticatedHeaders);
  }

  delete(element) {
    return this.http.delete(environment.serverUrl + this.noteUrl + '/' + element.id, this.appService.authenticatedHeaders);
  }

}
