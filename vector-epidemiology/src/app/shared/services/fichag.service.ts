import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FichaGeneral } from '../models/fichageneral';

import { Observable } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { _throw as throwError } from 'rxjs/observable/throw';

@Injectable({
  providedIn: 'root'
})
export class FichagService {

  apiURL = 'https://upheld-castle-251021.appspot.com';

  constructor(private http: HttpClient) { }

   // Http Options
 httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin' : '*',
    'Accept' : 'application/json'

  })
}  


getFichaG(): Observable<FichaGeneral> {
  return this.http.get<FichaGeneral>(this.apiURL + '/gamesystems')
  .pipe(
    retry(1),
    catchError(this.handleError)
  )
}

postFichaG(body) {
 return this.http.post(this.apiURL + '/gamesystems', body, this.httpOptions)
 .pipe(
   retry(1),
   catchError(this.handleError)  
 )
}


deleteFichaG(id) {
 return this.http.delete(this.apiURL + '/gamesystems/' + id, this.httpOptions)
 .pipe(
   retry(1),
   catchError(this.handleError)  
 )
}


// Error handling 
 handleError(error) {
   let errorMessage = '';
   if(error.error instanceof ErrorEvent) {
     // Get client-side error
     errorMessage = error.error.message;
   } else {
     // Get server-side error
     errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
   }
   window.alert(errorMessage);
   return throwError(errorMessage);
}

}
