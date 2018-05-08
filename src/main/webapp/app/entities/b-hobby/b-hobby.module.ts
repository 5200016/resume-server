import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BHobbyService,
    BHobbyPopupService,
    BHobbyComponent,
    BHobbyDetailComponent,
    BHobbyDialogComponent,
    BHobbyPopupComponent,
    BHobbyDeletePopupComponent,
    BHobbyDeleteDialogComponent,
    bHobbyRoute,
    bHobbyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bHobbyRoute,
    ...bHobbyPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BHobbyComponent,
        BHobbyDetailComponent,
        BHobbyDialogComponent,
        BHobbyDeleteDialogComponent,
        BHobbyPopupComponent,
        BHobbyDeletePopupComponent,
    ],
    entryComponents: [
        BHobbyComponent,
        BHobbyDialogComponent,
        BHobbyPopupComponent,
        BHobbyDeleteDialogComponent,
        BHobbyDeletePopupComponent,
    ],
    providers: [
        BHobbyService,
        BHobbyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBHobbyModule {}
