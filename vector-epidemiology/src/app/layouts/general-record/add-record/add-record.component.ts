import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { FichagService } from 'src/app/shared/services/fichag.service';
import { FichaGeneral } from 'src/app/shared/models/fichageneral'

@Component({
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.scss'],
  
})
export class AddRecordComponent implements OnInit {
  itemCount : number = 1
  btnTxt : string = "Add an item"
  goalText : string = "My first life goal"
  goals = [];

  constructor(private _data: FichagService) { }

  ngOnInit() {
   this.getFichaGeneral();
  }

  getFichaGeneral() {
      
    return this._data.getFichaG()
     .subscribe((data: any) => {
      console.log("ficha general :" + data );
      this.goals = data;
    }) 

  } 
 
 
  addItem() {
    var mydata = new FichaGeneral;
     
    mydata.consultorio = this.goalText;
    mydata.turno = this.goalText;
    mydata.nss = this.goalText;
    mydata.agregado = this.goalText;
    mydata.apellidopaterno = this.goalText;
    mydata.apellidomaterno = this.goalText;
    mydata.nombres = this.goalText;
    mydata.fechanacimiento = this.goalText;
    mydata.numexterior = this.goalText;
    mydata.numinterior = this.goalText;
    mydata.calle = this.goalText;
    mydata.entrecalle = this.goalText;
    mydata.ycalle = this.goalText;
    mydata.localidad = this.goalText;
    mydata.municipio = this.goalText;
    mydata.estado = this.goalText;
    mydata.codigopostal = this.goalText;
    mydata.edad = this.goalText;
    mydata.peso = this.goalText;
    mydata.estatura = this.goalText;
  
    return this._data.postFichaG(mydata)
     .subscribe((data: any) => {
      console.log("post ficha :" + data );
      this.goalText = '';
      this.getFichaGeneral();
    }) 
  }

  removeItem(i) {
  return this._data.deleteFichaG(i)
    .subscribe((data: any) => {
     console.log("deleted ficha general :" + data );
     this.getFichaGeneral();
   }) 



  }
}
