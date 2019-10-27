import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Entidades } from "../models/entidades";
import { Municipios } from "../models/municipios";
import { Denues } from "../models/denues";
import { Internets } from "../models/internets";
import { Idhs } from "../models/idhs";
import { Censos } from "../models/censos";
import { Dengue } from "../models/dengue";

import { Observable, throwError } from "rxjs";
import { retry, catchError } from "rxjs/operators";
import { Embarazada } from "../models/embarazada";

@Injectable({
  providedIn: "root"
})

export class RestApiService {

  // Define API
  apiURL = "https://upheld-castle-251021.appspot.com";
 // apiURL = 'http://localhost:10010';

  constructor(private http: HttpClient) { }

  /*========================================
    CRUD Methods for consuming RESTful API
  =========================================*/

  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };


  getInternets(embarazada, dengue): Observable<Internets> {
    return this.http.get<Internets>(this.apiURL + "/internet?embarazada=" + embarazada + "&dengue=" + dengue)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }


  getIdhs(embarazada, dengue): Observable<Idhs> {
    return this.http.get<Idhs>(this.apiURL + "/idh?embarazada=" + embarazada + "&dengue=" + dengue)
    .pipe(
      retry(1),
      catchError(this.handleError)
  );
  }


  getEntidades(): Observable<Entidades> {
    return this.http.get<Entidades>(this.apiURL + "/entidades")
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  getMunicipios(cve_ent): Observable<Municipios> {
    return this.http.get<Municipios>(this.apiURL + "/municipios?entidad=" + cve_ent)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  getDenues(entidad, municipio): Observable<Denues> {
    return this.http.get<Denues>(this.apiURL + "/denues?entidad=" + entidad + "&municipio=" + municipio)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }


  getDengueMunSemCp(year, entidad, municipio, semana, cp): Observable<Dengue> {
    return this.http.get<Dengue>(this.apiURL + "/denguebymunsemcp?year=" + year 
      + "&entidad=" + entidad 
      + "&municipio=" + municipio 
        + "&semana=" + semana 
        + "&cp="  + cp )
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  getDengueMunSem(year, entidad, municipio, semana): Observable<Dengue> {
    return this.http.get<Dengue>(this.apiURL + "/denguebymunsem?year=" + year 
    + "&entidad=" + entidad 
    + "&municipio=" + municipio 
        + "&semana=" + semana)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  getEmbarazada(ssn): Observable<Embarazada> {
    return this.http.get<Embarazada>(this.apiURL + "/embarazadabyssn?ssn=" + ssn)
    .pipe( 
      retry(1),
      catchError(this.handleError)
    );
  }
  
  getCensos(entidad, municipio): Observable<Censos> {
    return this.http.get<Censos>(this.apiURL + "/censos?entidad=" + entidad + "&municipio=" + municipio)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }
  // HttpClient API get() method => Fetch employees list
  /*
  getHtml(rfc, urlxml, xslt): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(this.apiURL + '/gethtml?rfc=' + rfc +
           '&urlxml='+ urlxml +
           '&xslt='+ xslt)

    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }*/

  /*
  // HttpClient API get() method => Fetch employees list
  getEmployees(): Observable<Employee> {
    return this.http.get<Employee>(this.apiURL + '/employees')
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }*/

  /*
  // HttpClient API get() method => Fetch employee
  getEmployee(id): Observable<Employee> {
    return this.http.get<Employee>(this.apiURL + '/employees/' + id)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }  */

  /*
  // HttpClient API post() method => Create employee
  createEmployee(employee): Observable<Employee> {
    return this.http.post<Employee>(this.apiURL + '/employees', JSON.stringify(employee), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }  */


  /*
  // HttpClient API put() method => Update employee
  updateEmployee(id, employee): Observable<Employee> {
    return this.http.put<Employee>(this.apiURL + '/employees/' + id, JSON.stringify(employee), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }
  */

  /*
  // HttpClient API delete() method => Delete employee
  deleteEmployee(id){
    return this.http.delete<Employee>(this.apiURL + '/employees/' + id, this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }*/

  // Error handling
  handleError(error) {
     let errorMessage = "";
     if (error.error instanceof ErrorEvent) {
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
