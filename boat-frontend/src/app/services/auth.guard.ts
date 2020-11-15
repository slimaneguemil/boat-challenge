import {CanActivate} from "@angular/router";
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService) {
  }

  canActivate() {
    if(this.authService.isAuthenticated()) {
      console.log("can activate / success");
      return true;
    } else {
      console.log("can activate / need login");
      this.authService.login();
    }
  }
}
