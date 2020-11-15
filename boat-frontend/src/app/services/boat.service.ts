import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class BoatService {

  constructor(private http:HttpClient) { }

  getBoats() {
    let token = localStorage.getItem('access_token');
    return this.http.get('/server/api/v1/boats',
      {headers: new HttpHeaders().set('Authorization', 'Bearer ' + token)}
    );
  }

  getBoat(id: number) {
    let token = localStorage.getItem('access_token');
    return this.http.get('/server/api/v1/boats/' + id,
      {headers: new HttpHeaders().set('Authorization', 'Bearer ' + token)}
    );
  }

  deleteBoat(id: number) {
    let token = localStorage.getItem('access_token');
    console.log("deteting boat with id: "+id);
    return this.http.delete('/server/api/v1/boats/' + id,
      {headers: new HttpHeaders().set('Authorization', 'Bearer ' + token)}
    );
  }

  createBoat(boat) {
    let token = localStorage.getItem('access_token');
    let body = JSON.stringify(boat);
    return this.http.post('/server/api/v1/boats', body,
      {headers: new HttpHeaders()
          .set('Authorization', 'Bearer ' + token)
          .set('Content-Type', 'application/json')
      }
      );
  }
  updateBoat(boat) {
    let token = localStorage.getItem('access_token');
    let body = JSON.stringify(boat);
    return this.http.put('/server/api/v1/boats', body,
      {headers: new HttpHeaders()
          .set('Authorization', 'Bearer ' + token)
          .set('Content-Type', 'application/json')
      }
      );
  }





}
