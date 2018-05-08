import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    JobObjectiveService,
    JobObjectivePopupService,
    JobObjectiveComponent,
    JobObjectiveDetailComponent,
    JobObjectiveDialogComponent,
    JobObjectivePopupComponent,
    JobObjectiveDeletePopupComponent,
    JobObjectiveDeleteDialogComponent,
    jobObjectiveRoute,
    jobObjectivePopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobObjectiveRoute,
    ...jobObjectivePopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JobObjectiveComponent,
        JobObjectiveDetailComponent,
        JobObjectiveDialogComponent,
        JobObjectiveDeleteDialogComponent,
        JobObjectivePopupComponent,
        JobObjectiveDeletePopupComponent,
    ],
    entryComponents: [
        JobObjectiveComponent,
        JobObjectiveDialogComponent,
        JobObjectivePopupComponent,
        JobObjectiveDeleteDialogComponent,
        JobObjectiveDeletePopupComponent,
    ],
    providers: [
        JobObjectiveService,
        JobObjectivePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeJobObjectiveModule {}
