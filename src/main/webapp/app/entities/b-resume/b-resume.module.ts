import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BResumeService,
    BResumePopupService,
    BResumeComponent,
    BResumeDetailComponent,
    BResumeDialogComponent,
    BResumePopupComponent,
    BResumeDeletePopupComponent,
    BResumeDeleteDialogComponent,
    bResumeRoute,
    bResumePopupRoute,
} from './';

const ENTITY_STATES = [
    ...bResumeRoute,
    ...bResumePopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BResumeComponent,
        BResumeDetailComponent,
        BResumeDialogComponent,
        BResumeDeleteDialogComponent,
        BResumePopupComponent,
        BResumeDeletePopupComponent,
    ],
    entryComponents: [
        BResumeComponent,
        BResumeDialogComponent,
        BResumePopupComponent,
        BResumeDeleteDialogComponent,
        BResumeDeletePopupComponent,
    ],
    providers: [
        BResumeService,
        BResumePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBResumeModule {}
