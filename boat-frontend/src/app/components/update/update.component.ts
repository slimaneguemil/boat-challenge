import { Component, OnInit } from '@angular/core';
import { BoatService } from '../../services/boat.service';
import { ActivatedRoute } from '@angular/router';
import {Observable} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-view-registration',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {

  public boatReg;
  boatform: FormGroup;
  validMessage: string = "";
  submitSuccessful = false;

  constructor(private boatService: BoatService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getBoatReg(this.route.snapshot.params.id);
    this.boatform = new FormGroup({
      id: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      version: new FormControl('', Validators.required)
    });
  }

  getBoatReg(id:number) {
    this.boatService.getBoat(id).subscribe(
      data => {
        this.boatReg = data;
        this.boatform.patchValue({
          id: this.boatReg.id,
            name: this.boatReg.name,
            description: this.boatReg.description,
          version: this.boatReg.version

        });
      },
      err => console.error(err),
      () => console.log('boats loaded')
    );
  }

  submitBoat() {
    if (this.boatform.valid) {
      this.validMessage = "Your boat has been updated. Thank you!";
      this.boatService.updateBoat(this.boatform.value).subscribe(
        data => {
          //this.boatform.reset();
          this.submitSuccessful = true;
          return true;
        },
        error => {
          return Observable.throw(error);
        }
      );
    } else {
      this.validMessage = "Please fill out the form before submitting!";
    }
  }

  deleteBoat() {
    if (this.boatform.valid) {
      this.validMessage = "Your boat has been deleted. Thank you!";
      this.boatService.deleteBoat(this.boatform.get('id').value).subscribe(
        data => {
          //this.boatform.reset();
          this.submitSuccessful = true;
          return true;
        },
        error => {
          return Observable.throw(error);
        }
      );
    } else {
      this.validMessage = "Please fill out the form before submitting!";
    }
  }

}
