// Angular
import "@angular/core";
import "@angular/platform-browser";
import "@angular/forms";
import "@angular/common/http";
// RxJS
import "rxjs";

// Other vendors for example jQuery, Lodash or Bootstrap
// You can import js, ts, css, sass, ...

require("../node_modules/font-awesome/css/font-awesome.css");
require('typeface-lato');

require("@angular/material/prebuilt-themes/indigo-pink.css");

// Note that css-loader will resolve all url(...) in the css files as require(...)
// In the case of font-awesome, all images will match the configuration for images set up in "webpack.common.js"
