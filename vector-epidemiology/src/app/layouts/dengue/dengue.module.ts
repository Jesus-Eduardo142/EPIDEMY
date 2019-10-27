// Core Dependencies
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

// configuration and services
import { DengueRoutes } from "./dengue.routing";
import { EmbarazadaComponent } from './embarazada/embarazada.component';
import { ListumfComponent } from './umf/listumf/listumf.component';
import { AddmedicosComponent } from './medicos/addmedicos/addmedicos.component';
import { AddumfComponent } from './umf/addumf/addumf.component';

//import { BusquedaComponent } from "./busqueda/busqueda.component";
//import	{ MedicosComponent } from "./medicos/medicos.component";
//import { UmfComponent } from "./umf/umf.component";	

// Components

@NgModule({
	imports: [CommonModule, RouterModule.forChild(DengueRoutes)],
	declarations: [
		//BusquedaComponent,
		//MedicosComponent,
		//UmfComponent

	EmbarazadaComponent,
		AddumfComponent,
		ListumfComponent,
		AddmedicosComponent,
		AddumfComponent],
	exports: []
})
export class DengueModule { }
