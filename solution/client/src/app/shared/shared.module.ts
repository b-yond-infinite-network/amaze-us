import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header.component';
import { FooterComponent } from './footer.component';
import { KeyFilterModule } from 'primeng/keyfilter';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { RouterModule } from '@angular/router';
import { SearchBarComponent } from './search-bar/search-bar.component';

@NgModule({
  declarations: [HeaderComponent, FooterComponent, SearchBarComponent],
  imports: [
    CommonModule, KeyFilterModule, InputTextModule, FormsModule, TableModule, DialogModule, RouterModule
  ],
  exports: [HeaderComponent, FooterComponent, KeyFilterModule, TableModule,
    InputTextModule, FormsModule, DialogModule, SearchBarComponent]
})
export class SharedModule { }
