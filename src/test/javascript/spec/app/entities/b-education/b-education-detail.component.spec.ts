import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BEducationDetailComponent } from '../../../../../../main/webapp/app/entities/b-education/b-education-detail.component';
import { BEducationService } from '../../../../../../main/webapp/app/entities/b-education/b-education.service';
import { BEducation } from '../../../../../../main/webapp/app/entities/b-education/b-education.model';

describe('Component Tests', () => {

    describe('BEducation Management Detail Component', () => {
        let comp: BEducationDetailComponent;
        let fixture: ComponentFixture<BEducationDetailComponent>;
        let service: BEducationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BEducationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BEducationService,
                    JhiEventManager
                ]
            }).overrideTemplate(BEducationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BEducationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BEducationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BEducation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bEducation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
