import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IsLoggedGuard } from '../guards/logged.guard';
import { FoodPanelComponent } from './components/food-panel/food-panel.component';

// Components

const routes: Routes = [
  { path: '', redirectTo: '/food/home', pathMatch: 'full'},
  { path: 'home', component: FoodPanelComponent, pathMatch: 'full', canActivate: [IsLoggedGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FoodRoutingModule {}
