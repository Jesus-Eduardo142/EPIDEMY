import { Routes } from '@angular/router';
import { IndexComponent } from '../../index/index.component';
import { BusquedaComponent } from './busqueda/busqueda.component';
import { MedicosComponent } from './medicos/medicos.component';
import { UmfComponent } from './umf/umf.component';
import { DengueComponent } from './dengue.component';
import { EmbarazadaComponent } from './embarazada/embarazada.component';
import { AddumfComponent } from './umf/addumf/addumf.component';
import { AddmedicosComponent } from './medicos/addmedicos/addmedicos.component';
import { ListumfComponent } from './umf/listumf/listumf.component';

export const DengueRoutes: Routes = [
	{
		path: 'dengue',
		children: [
			{
				path: 'allDengue',
				component: DengueComponent
			},
			{
				path: 'busqueda',
				component: BusquedaComponent
			},
			{
				path: 'medicos',
				component: MedicosComponent
			},
			{
				path: 'addmedicos',
				component: AddmedicosComponent
			},
			{
				path: 'umf',
				component: UmfComponent
			},

			{
				path: 'addumf',
				component: AddumfComponent	
			},
			{
				path: 'listumf',
				component: ListumfComponent
			},
			{
				path: "embarazada",
				component: EmbarazadaComponent
			}
		]
	}
];
