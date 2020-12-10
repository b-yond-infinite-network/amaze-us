
import { TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

export class CustomTranslationLoader extends TranslateLoader {
  private defaultLanguage = 'en';

  getTranslation(lang: string): Observable<any> {
    let allTranslations;

    try {
      allTranslations = require(`../assets/i18n/${lang}.json`);
    } catch (err) {
      allTranslations = require(`../assets/i18n/${this.defaultLanguage}.json`);
    }

    return of(allTranslations);
  }
}