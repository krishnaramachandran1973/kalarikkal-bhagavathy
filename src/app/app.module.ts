import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './navigation/navigation.component';
import {FooterComponent} from './footer/footer.component';
import {RouterModule, Routes} from "@angular/router";
import {BreadcrumbModule} from "xng-breadcrumb";
import {HomeComponent} from './home/home.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AboutComponent} from './about/about.component';
import {NgwWowModule} from "ngx-wow";
import { ContactComponent } from './contact/contact.component';
import { VerifyComponent } from './verify/verify.component';
import { VideosComponent } from './videos/videos.component';
import {NgxPaginationModule} from "ngx-pagination";

const routes: Routes = [
  {path: '', component: HomeComponent, data: {breadcrumb: 'Home'}},
  {path: 'about', component: AboutComponent, data: {breadcrumb: 'About'}},
  {path: 'contact', component: ContactComponent, data: {breadcrumb: 'Contact'}},
  {path: 'videos', component: VideosComponent, data: {breadcrumb: 'Videos'}},
  {path: 'verify/:code', component: VerifyComponent, data: {breadcrumb: 'Verification'}}
]

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    FooterComponent,
    HomeComponent,
    AboutComponent,
    ContactComponent,
    VerifyComponent,
    VideosComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    BreadcrumbModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgwWowModule,
    NgxPaginationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule
{
}
