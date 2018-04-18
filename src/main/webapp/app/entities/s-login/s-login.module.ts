import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    SLoginService,
    SLoginPopupService,
    SLoginComponent,
    SLoginDetailComponent,
    SLoginDialogComponent,
    SLoginPopupComponent,
    SLoginDeletePopupComponent,
    SLoginDeleteDialogComponent,
    sLoginRoute,
    sLoginPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sLoginRoute,
    ...sLoginPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SLoginComponent,
        SLoginDetailComponent,
        SLoginDialogComponent,
        SLoginDeleteDialogComponent,
        SLoginPopupComponent,
        SLoginDeletePopupComponent,
    ],
    entryComponents: [
        SLoginComponent,
        SLoginDialogComponent,
        SLoginPopupComponent,
        SLoginDeleteDialogComponent,
        SLoginDeletePopupComponent,
    ],
    providers: [
        SLoginService,
        SLoginPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeSLoginModule {}
