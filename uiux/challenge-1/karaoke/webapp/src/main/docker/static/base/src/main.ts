import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {enableProdMode} from "@angular/core";
import {AppModule} from "./app/scripts/module_app";

// https://webpack.github.io/docs/list-of-plugins.html#defineplugin
declare var PRODUCTION: boolean;
if (PRODUCTION) {
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule);
