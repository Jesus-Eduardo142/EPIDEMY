import { ChildsListComponent } from './childs-list/childs-list.component';
// Core Dependencies
//import { NgModule } from "@angular/core";
import { NgModule, NO_ERRORS_SCHEMA, APP_INITIALIZER } from '@angular/core';

import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../../shared/shared.module";
import { HttpClientModule } from '@angular/common/http';

// configuration and services
import { BranchRoutes } from "./childs.routing";
import { AddChildComponent } from './add-child/add-child.component';
//import { IndexComponent } from '../../index/index.component';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

// Components
/*
import { CheckoutModule } from "./checkout/checkout.module";

import { ProductComponent } from "./product.component";
import { BestProductComponent } from "./best-product/best-product.component";
import { ProductListComponent } from "./product-list/product-list.component";
import { AddProductComponent } from "./add-product/add-product.component";
import { ProductDetailComponent } from "./product-detail/product-detail.component";
import { SharedModule } from "../../shared/shared.module";
import { FavouriteProductsComponent } from "./favourite-products/favourite-products.component";
import { CartProductsComponent } from "./cart-products/cart-products.component";
import { CartCalculatorComponent } from "./cart-calculator/cart-calculator.component";
*/
import { AgmCoreModule } from '@agm/core';

@NgModule({
	imports: [HttpClientModule, CommonModule, 
			  RouterModule.forChild(BranchRoutes), SharedModule,
			  AgmCoreModule.forRoot({
				//apiKey: 'AIzaSyD9xXq1L6UtsTBi8miLM0FJU2erOkwW_0I'
				  apiKey: 'AIzaSyCISYKY_t-jlJGxYMe3nBOMTqwRrhKPxMk',
				  libraries: ["places"]
			   }),
			   BrowserModule,
			   FormsModule,
			   ReactiveFormsModule
			],
	declarations: [
		//IndexComponent,
		ChildsListComponent,
		AddChildComponent
		
	],
  exports: [/*BestProductComponent*/],
  schemas: [ NO_ERRORS_SCHEMA ]

})
export class ChildModule { }
