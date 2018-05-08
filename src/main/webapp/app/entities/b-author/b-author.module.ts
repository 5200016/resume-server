import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResumeSharedModule } from '../../shared';
import {
    BAuthorService,
    BAuthorPopupService,
    BAuthorComponent,
    BAuthorDetailComponent,
    BAuthorDialogComponent,
    BAuthorPopupComponent,
    BAuthorDeletePopupComponent,
    BAuthorDeleteDialogComponent,
    bAuthorRoute,
    bAuthorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bAuthorRoute,
    ...bAuthorPopupRoute,
];

@NgModule({
    imports: [
        ResumeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BAuthorComponent,
        BAuthorDetailComponent,
        BAuthorDialogComponent,
        BAuthorDeleteDialogComponent,
        BAuthorPopupComponent,
        BAuthorDeletePopupComponent,
    ],
    entryComponents: [
        BAuthorComponent,
        BAuthorDialogComponent,
        BAuthorPopupComponent,
        BAuthorDeleteDialogComponent,
        BAuthorDeletePopupComponent,
    ],
    providers: [
        BAuthorService,
        BAuthorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeBAuthorModule {}
