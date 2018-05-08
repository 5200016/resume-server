import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BWorkProjectService,
    BWorkProjectPopupService,
    BWorkProjectComponent,
    BWorkProjectDetailComponent,
    BWorkProjectDialogComponent,
    BWorkProjectPopupComponent,
    BWorkProjectDeletePopupComponent,
    BWorkProjectDeleteDialogComponent,
    bWorkProjectRoute,
    bWorkProjectPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bWorkProjectRoute,
    ...bWorkProjectPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BWorkProjectComponent,
        BWorkProjectDetailComponent,
        BWorkProjectDialogComponent,
        BWorkProjectDeleteDialogComponent,
        BWorkProjectPopupComponent,
        BWorkProjectDeletePopupComponent,
    ],
    entryComponents: [
        BWorkProjectComponent,
        BWorkProjectDialogComponent,
        BWorkProjectPopupComponent,
        BWorkProjectDeleteDialogComponent,
        BWorkProjectDeletePopupComponent,
    ],
    providers: [
        BWorkProjectService,
        BWorkProjectPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBWorkProjectModule {}
