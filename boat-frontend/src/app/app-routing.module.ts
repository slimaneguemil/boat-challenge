import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {AuthGuard} from "./services/auth.guard";
import {NewComponent} from "./components/new/new.component";
import {UpdateComponent} from "./components/update/update.component";
import {CallbackComponent} from "./components/callback/callback.component";

const routes: Routes = [

  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'boat',
    component: NewComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'update/:id',
    component: UpdateComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'callback',
    component: CallbackComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
