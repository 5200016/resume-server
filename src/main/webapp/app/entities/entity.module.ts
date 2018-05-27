import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ResumeSLoginModule } from './s-login/s-login.module';
import { ResumeSRoleModule } from './s-role/s-role.module';
import { ResumeSUserModule } from './s-user/s-user.module';
import { ResumeBAuthorModule } from './b-author/b-author.module';
import { ResumeBTemplateModule } from './b-template/b-template.module';
import { ResumeBResumeModule } from './b-resume/b-resume.module';
import { ResumeBInformationModule } from './b-information/b-information.module';
import { ResumeBContactModule } from './b-contact/b-contact.module';
import { ResumeBWorkModule } from './b-work/b-work.module';
import { ResumeBWorkProjectModule } from './b-work-project/b-work-project.module';
import { ResumeBHonourModule } from './b-honour/b-honour.module';
import { ResumeBHobbyModule } from './b-hobby/b-hobby.module';
import { ResumeJobObjectiveModule } from './job-objective/job-objective.module';
import { ResumeBTemplateClassifyModule } from './b-template-classify/b-template-classify.module';
import { ResumeBEducationModule } from './b-education/b-education.module';
import { ResumeBSelfModule } from './b-self/b-self.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ResumeSLoginModule,
        ResumeSRoleModule,
        ResumeSUserModule,
        ResumeBAuthorModule,
        ResumeBTemplateModule,
        ResumeBResumeModule,
        ResumeBInformationModule,
        ResumeBContactModule,
        ResumeBWorkModule,
        ResumeBWorkProjectModule,
        ResumeBHonourModule,
        ResumeBHobbyModule,
        ResumeJobObjectiveModule,
        ResumeBTemplateClassifyModule,
        ResumeBEducationModule,
        ResumeBSelfModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResumeEntityModule {}
