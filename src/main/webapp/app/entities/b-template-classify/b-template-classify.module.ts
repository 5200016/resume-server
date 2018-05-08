import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BTemplateClassifyService,
    BTemplateClassifyPopupService,
    BTemplateClassifyComponent,
    BTemplateClassifyDetailComponent,
    BTemplateClassifyDialogComponent,
    BTemplateClassifyPopupComponent,
    BTemplateClassifyDeletePopupComponent,
    BTemplateClassifyDeleteDialogComponent,
    bTemplateClassifyRoute,
    bTemplateClassifyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bTemplateClassifyRoute,
    ...bTemplateClassifyPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BTemplateClassifyComponent,
        BTemplateClassifyDetailComponent,
        BTemplateClassifyDialogComponent,
        BTemplateClassifyDeleteDialogComponent,
        BTemplateClassifyPopupComponent,
        BTemplateClassifyDeletePopupComponent,
    ],
    entryComponents: [
        BTemplateClassifyComponent,
        BTemplateClassifyDialogComponent,
        BTemplateClassifyPopupComponent,
        BTemplateClassifyDeleteDialogComponent,
        BTemplateClassifyDeletePopupComponent,
    ],
    providers: [
        BTemplateClassifyService,
        BTemplateClassifyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBTemplateClassifyModule {}
