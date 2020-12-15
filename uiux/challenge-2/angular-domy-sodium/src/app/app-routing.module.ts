import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', redirectTo: '/auth/login', pathMatch: 'full'
  },
  {
    path: 'auth',
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./auth/auth.module').then(m => m.AuthModule)
      }
    ]
  },
  {
    path: 'food',
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./food/food.module').then(m => m.FoodModule)
      }
    ]
  },
  {
    path: 'population',
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./population/population.module').then(m => m.PopulationModule)
      }
    ]
  },
  {
    path: 'water',
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./water/water.module').then(m => m.WaterModule)
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
