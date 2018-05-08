import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BResumeDetailComponent } from '../../../../../../main/webapp/app/entities/b-resume/b-resume-detail.component';
import { BResumeService } from '../../../../../../main/webapp/app/entities/b-resume/b-resume.service';
import { BResume } from '../../../../../../main/webapp/app/entities/b-resume/b-resume.model';

describe('Component Tests', () => {

    describe('BResume Management Detail Component', () => {
        let comp: BResumeDetailComponent;
        let fixture: ComponentFixture<BResumeDetailComponent>;
        let service: BResumeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BResumeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BResumeService,
                    JhiEventManager
                ]
            }).overrideTemplate(BResumeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BResumeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BResumeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BResume(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bResume).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
