import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

import { GeneralRoutes } from "./general-record.routing";
import { AddRecordComponent } from './add-record/add-record.component';

@NgModule({
	imports: [CommonModule, RouterModule.forChild(GeneralRoutes)],
	declarations: [

	AddRecordComponent],
	exports: []
})
export class GeneralRecordModule { }
