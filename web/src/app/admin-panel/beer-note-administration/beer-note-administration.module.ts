import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerNoteAdministrationComponent} from './beer-note-administration.component';
import {BeerNoteListModule} from '../../beer-note/beer-note-list/beer-note-list.module';


@NgModule({
    declarations: [BeerNoteAdministrationComponent],
    exports: [
        BeerNoteAdministrationComponent
    ],
    imports: [
        CommonModule,
        BeerNoteListModule
    ]
})
export class BeerNoteAdministrationModule {
}
