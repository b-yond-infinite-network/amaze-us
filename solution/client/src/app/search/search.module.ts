import { NgModule } from '@angular/core';
import { SearchRoutingModule } from './search-routing.module';
import { SharedModule } from './../shared/shared.module';
import { CommonModule } from "@angular/common"
import {PaginatorModule} from 'primeng/paginator';

@NgModule({
  imports: [SearchRoutingModule, SharedModule,PaginatorModule, CommonModule],
  declarations: [SearchRoutingModule.components]
})
export class SearchModule { }
