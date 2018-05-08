import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JobObjectiveDetailComponent } from '../../../../../../main/webapp/app/entities/job-objective/job-objective-detail.component';
import { JobObjectiveService } from '../../../../../../main/webapp/app/entities/job-objective/job-objective.service';
import { JobObjective } from '../../../../../../main/webapp/app/entities/job-objective/job-objective.model';

describe('Component Tests', () => {

    describe('JobObjective Management Detail Component', () => {
        let comp: JobObjectiveDetailComponent;
        let fixture: ComponentFixture<JobObjectiveDetailComponent>;
        let service: JobObjectiveService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [JobObjectiveDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JobObjectiveService,
                    JhiEventManager
                ]
            }).overrideTemplate(JobObjectiveDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobObjectiveDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobObjectiveService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JobObjective(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jobObjective).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
