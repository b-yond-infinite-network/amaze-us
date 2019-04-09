import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArtistComponent } from './artist/artist.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [ArtistComponent],
  imports: [
    CommonModule, SharedModule
  ]
})
export class ArtistModule { }
