import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WaterPanelComponent } from './components/water-panel/water-panel.component';

// Components

const routes: Routes = [
  { path: '', redirectTo: '/water/home', pathMatch: 'full'},
  { path: 'home', component: WaterPanelComponent, pathMatch: 'full'},

];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class WaterRoutingModule {}
