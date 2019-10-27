import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-listumf',
  templateUrl: './listumf.component.html',
  styleUrls: ['./listumf.component.scss']
})

export class ListumfComponent implements OnInit {

  
  columnDefs = [
    {headerName: 'N/P', field: 'np', width: 80},
    {headerName: 'Name', field: 'name', width: 140},
    {headerName: 'Surnames', field: 'surnames', width: 160},
    {headerName: 'Age', field: 'age', width: 80},
    {headerName: 'RFC', field: 'rfc', width: 150},
    {headerName: 'Country', field: 'country', width: 140},
    {headerName: 'State', field: 'state', width: 150},
    {headerName: 'Municipality', field: 'municipality', width: 150},
    {headerName: 'Telephone', field: 'telephone', width: 120}
  ];

  rowData = [
    { np: '01', name: 'Aracely', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '02', name: 'Mario', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '03', name: 'Alejandro', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '04', name: 'David', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '05', name: 'Aranza', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '06', name: 'Rodrigo', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '07', name: 'Gael', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '08', name: 'Diana', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '09', name: 'Veronica', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '10', name: 'leticia', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '11', name: 'Armando', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '12', name: 'Omar', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '13', name: 'Jose', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
    { np: '14', name: 'Maria', surnames: 'Garcia Hernandez', age: 30, rfc: 'SER57D4Q3CV67', country: 'Mexico', state:'Tamahulipas', municipality: 'reynosa', telephone: '552656451'},
  ];

	constructor() { }

	ngOnInit() {
	
	}
}
