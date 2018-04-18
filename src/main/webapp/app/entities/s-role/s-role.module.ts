import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    SRoleService,
    SRolePopupService,
    SRoleComponent,
    SRoleDetailComponent,
    SRoleDialogComponent,
    SRolePopupComponent,
    SRoleDeletePopupComponent,
    SRoleDeleteDialogComponent,
    sRoleRoute,
    sRolePopupRoute,
} from './';

const ENTITY_STATES = [
    ...sRoleRoute,
    ...sRolePopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SRoleComponent,
        SRoleDetailComponent,
        SRoleDialogComponent,
        SRoleDeleteDialogComponent,
        SRolePopupComponent,
        SRoleDeletePopupComponent,
    ],
    entryComponents: [
        SRoleComponent,
        SRoleDialogComponent,
        SRolePopupComponent,
        SRoleDeleteDialogComponent,
        SRoleDeletePopupComponent,
    ],
    providers: [
        SRoleService,
        SRolePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeSRoleModule {}
