import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BContactService,
    BContactPopupService,
    BContactComponent,
    BContactDetailComponent,
    BContactDialogComponent,
    BContactPopupComponent,
    BContactDeletePopupComponent,
    BContactDeleteDialogComponent,
    bContactRoute,
    bContactPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bContactRoute,
    ...bContactPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BContactComponent,
        BContactDetailComponent,
        BContactDialogComponent,
        BContactDeleteDialogComponent,
        BContactPopupComponent,
        BContactDeletePopupComponent,
    ],
    entryComponents: [
        BContactComponent,
        BContactDialogComponent,
        BContactPopupComponent,
        BContactDeleteDialogComponent,
        BContactDeletePopupComponent,
    ],
    providers: [
        BContactService,
        BContactPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBContactModule {}
