import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BInformationService,
    BInformationPopupService,
    BInformationComponent,
    BInformationDetailComponent,
    BInformationDialogComponent,
    BInformationPopupComponent,
    BInformationDeletePopupComponent,
    BInformationDeleteDialogComponent,
    bInformationRoute,
    bInformationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bInformationRoute,
    ...bInformationPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BInformationComponent,
        BInformationDetailComponent,
        BInformationDialogComponent,
        BInformationDeleteDialogComponent,
        BInformationPopupComponent,
        BInformationDeletePopupComponent,
    ],
    entryComponents: [
        BInformationComponent,
        BInformationDialogComponent,
        BInformationPopupComponent,
        BInformationDeleteDialogComponent,
        BInformationDeletePopupComponent,
    ],
    providers: [
        BInformationService,
        BInformationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBInformationModule {}
