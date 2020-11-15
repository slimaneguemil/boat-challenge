import { Component, OnInit } from '@angular/core';
import {BoatService} from "../../services/boat.service";
@Component({
  selector: 'app-admin',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public boats;

  constructor(private boatService: BoatService) { }

  ngOnInit() {
    console.log("home");
    this.getBoats();
  }

  getBoats() {
    this.boatService.getBoats().subscribe(
      data => { this.boats = data},
      err => console.error(err),
      () => console.log('bikes loaded')
    );
  }

}
