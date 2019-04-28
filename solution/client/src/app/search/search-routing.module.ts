import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SearchComponent } from './main/search.component';
import { SearchContainerComponent } from './search-container/search-container.component';

const routes: Routes = [
    { path: '', component: SearchComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SearchRoutingModule {
    static components = [SearchComponent, SearchContainerComponent];
}
