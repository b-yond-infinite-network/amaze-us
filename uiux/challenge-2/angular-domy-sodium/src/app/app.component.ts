import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  
  constructor(
    private translateService: TranslateService
  ) {}


  ngOnInit(): void {
    this.setLanguage();
  }

  async setLanguage() {
    const lang = localStorage.getItem('lang');

    if(!lang) { return; }
    await this.translateService.use(lang).toPromise();
  }
}
