import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BTemplateService,
    BTemplatePopupService,
    BTemplateComponent,
    BTemplateDetailComponent,
    BTemplateDialogComponent,
    BTemplatePopupComponent,
    BTemplateDeletePopupComponent,
    BTemplateDeleteDialogComponent,
    bTemplateRoute,
    bTemplatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...bTemplateRoute,
    ...bTemplatePopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BTemplateComponent,
        BTemplateDetailComponent,
        BTemplateDialogComponent,
        BTemplateDeleteDialogComponent,
        BTemplatePopupComponent,
        BTemplateDeletePopupComponent,
    ],
    entryComponents: [
        BTemplateComponent,
        BTemplateDialogComponent,
        BTemplatePopupComponent,
        BTemplateDeleteDialogComponent,
        BTemplateDeletePopupComponent,
    ],
    providers: [
        BTemplateService,
        BTemplatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBTemplateModule {}
