import { Routes } from '@angular/router';
import { IndexComponent } from '../../index/index.component';
import { GeneralRecordComponent } from './general-record.component';
import { AddRecordComponent } from './add-record/add-record.component';

export const GeneralRoutes: Routes = [
	{
		path: 'grecord',
		children: [
			{
				path: 'allGeneralR',
				component: GeneralRecordComponent
			},
			{
				path: 'addRecord',
				component: AddRecordComponent
			}
		]
	}
];
