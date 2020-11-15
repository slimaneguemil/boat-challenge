import { Component, OnInit } from '@angular/core';
import { BoatService } from '../../services/boat.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit {
  boatform: FormGroup;
  validMessage: string = "";
  submitSuccessful = false;

  constructor(private boatService: BoatService) { }

  ngOnInit() {
    this.boatform = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
  }

  submitRegistration() {

    if (this.boatform.valid) {
      this.validMessage = "Your boat has been submitted. Thank you!";
      this.boatService.createBoat(this.boatform.value).subscribe(
        data => {
         // this.boatform.reset();
          this.submitSuccessful = true;
          return true;
        },
        error => {
          return Observable.throw(error);
        }
      )
    } else {
      this.validMessage = "Please fill out the form before submitting!";
    }
  }

}
