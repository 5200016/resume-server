import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ResumeSLoginModule } from './s-login/s-login.module';
import { ResumeSRoleModule } from './s-role/s-role.module';
import { ResumeSUserModule } from './s-user/s-user.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ResumeSLoginModule,
        ResumeSRoleModule,
        ResumeSUserModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeEntityModule {}
