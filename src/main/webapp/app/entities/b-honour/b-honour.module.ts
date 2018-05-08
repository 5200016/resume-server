import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BHonourService,
    BHonourPopupService,
    BHonourComponent,
    BHonourDetailComponent,
    BHonourDialogComponent,
    BHonourPopupComponent,
    BHonourDeletePopupComponent,
    BHonourDeleteDialogComponent,
    bHonourRoute,
    bHonourPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bHonourRoute,
    ...bHonourPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BHonourComponent,
        BHonourDetailComponent,
        BHonourDialogComponent,
        BHonourDeleteDialogComponent,
        BHonourPopupComponent,
        BHonourDeletePopupComponent,
    ],
    entryComponents: [
        BHonourComponent,
        BHonourDialogComponent,
        BHonourPopupComponent,
        BHonourDeleteDialogComponent,
        BHonourDeletePopupComponent,
    ],
    providers: [
        BHonourService,
        BHonourPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBHonourModule {}
