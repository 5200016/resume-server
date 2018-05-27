import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BEducationService,
    BEducationPopupService,
    BEducationComponent,
    BEducationDetailComponent,
    BEducationDialogComponent,
    BEducationPopupComponent,
    BEducationDeletePopupComponent,
    BEducationDeleteDialogComponent,
    bEducationRoute,
    bEducationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bEducationRoute,
    ...bEducationPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BEducationComponent,
        BEducationDetailComponent,
        BEducationDialogComponent,
        BEducationDeleteDialogComponent,
        BEducationPopupComponent,
        BEducationDeletePopupComponent,
    ],
    entryComponents: [
        BEducationComponent,
        BEducationDialogComponent,
        BEducationPopupComponent,
        BEducationDeleteDialogComponent,
        BEducationDeletePopupComponent,
    ],
    providers: [
        BEducationService,
        BEducationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBEducationModule {}
