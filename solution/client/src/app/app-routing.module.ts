import { NgModule } from '@angular/core';
import { RouterModule, Routes, PreloadAllModules } from '@angular/router';
import { ArtistComponent } from './artist/artist/artist.component';
import { LyricComponent } from './lyrics/lyric/lyric.component';
import { SearchContainerComponent } from './search/search-container/search-container.component';
import { ServerErrorComponent } from './server-error/server-error.component';

const app_routes: Routes = [
  { path: 'lyric/:id', component: LyricComponent },
  { path: 'artist', component: ArtistComponent },
  { path: 'search/list', component: SearchContainerComponent},
  { path: 'server-error', component: ServerErrorComponent},
  { path: '**', pathMatch: 'full', redirectTo: '/' } // catch 404 routes and redirect to home page
];

@NgModule({
  imports: [RouterModule.forRoot(app_routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
