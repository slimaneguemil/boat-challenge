import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {BoatService} from "./services/boat.service";
import {HomeComponent} from "./components/home/home.component";
import {AuthService} from "./services/auth.service";
import {AuthGuard} from "./services/auth.guard";
import {NewComponent} from "./components/new/new.component";
import {UpdateComponent} from "./components/update/update.component";
import {CallbackComponent} from "./components/callback/callback.component";
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NewComponent,
    UpdateComponent,
    CallbackComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [BoatService, AuthService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
