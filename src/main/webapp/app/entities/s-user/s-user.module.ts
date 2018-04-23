import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    SUserService,
    SUserPopupService,
    SUserComponent,
    SUserDetailComponent,
    SUserDialogComponent,
    SUserPopupComponent,
    SUserDeletePopupComponent,
    SUserDeleteDialogComponent,
    sUserRoute,
    sUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sUserRoute,
    ...sUserPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SUserComponent,
        SUserDetailComponent,
        SUserDialogComponent,
        SUserDeleteDialogComponent,
        SUserPopupComponent,
        SUserDeletePopupComponent,
    ],
    entryComponents: [
        SUserComponent,
        SUserDialogComponent,
        SUserPopupComponent,
        SUserDeleteDialogComponent,
        SUserDeletePopupComponent,
    ],
    providers: [
        SUserService,
        SUserPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeSUserModule {}
