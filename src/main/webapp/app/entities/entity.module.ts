import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ResumeSLoginModule } from './s-login/s-login.module';
import { ResumeSRoleModule } from './s-role/s-role.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ResumeSLoginModule,
        ResumeSRoleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeEntityModule {}
