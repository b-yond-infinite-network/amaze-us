import { NgModule } from '@angular/core';
import { SearchRoutingModule } from './search-routing.module';
import { SharedModule } from './../shared/shared.module';
import { CommonModule } from "@angular/common"

@NgModule({
  imports: [SearchRoutingModule, SharedModule, CommonModule],
  declarations: [SearchRoutingModule.components]
})
export class SearchModule { }
