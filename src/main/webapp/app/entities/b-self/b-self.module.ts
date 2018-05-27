import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BSelfService,
    BSelfPopupService,
    BSelfComponent,
    BSelfDetailComponent,
    BSelfDialogComponent,
    BSelfPopupComponent,
    BSelfDeletePopupComponent,
    BSelfDeleteDialogComponent,
    bSelfRoute,
    bSelfPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bSelfRoute,
    ...bSelfPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BSelfComponent,
        BSelfDetailComponent,
        BSelfDialogComponent,
        BSelfDeleteDialogComponent,
        BSelfPopupComponent,
        BSelfDeletePopupComponent,
    ],
    entryComponents: [
        BSelfComponent,
        BSelfDialogComponent,
        BSelfPopupComponent,
        BSelfDeleteDialogComponent,
        BSelfDeletePopupComponent,
    ],
    providers: [
        BSelfService,
        BSelfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBSelfModule {}
