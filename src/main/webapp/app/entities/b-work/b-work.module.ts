import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BWorkService,
    BWorkPopupService,
    BWorkComponent,
    BWorkDetailComponent,
    BWorkDialogComponent,
    BWorkPopupComponent,
    BWorkDeletePopupComponent,
    BWorkDeleteDialogComponent,
    bWorkRoute,
    bWorkPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bWorkRoute,
    ...bWorkPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BWorkComponent,
        BWorkDetailComponent,
        BWorkDialogComponent,
        BWorkDeleteDialogComponent,
        BWorkPopupComponent,
        BWorkDeletePopupComponent,
    ],
    entryComponents: [
        BWorkComponent,
        BWorkDialogComponent,
        BWorkPopupComponent,
        BWorkDeleteDialogComponent,
        BWorkDeletePopupComponent,
    ],
    providers: [
        BWorkService,
        BWorkPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBWorkModule {}
