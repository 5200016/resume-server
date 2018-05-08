import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BTemplateClassifyDetailComponent } from '../../../../../../main/webapp/app/entities/b-template-classify/b-template-classify-detail.component';
import { BTemplateClassifyService } from '../../../../../../main/webapp/app/entities/b-template-classify/b-template-classify.service';
import { BTemplateClassify } from '../../../../../../main/webapp/app/entities/b-template-classify/b-template-classify.model';

describe('Component Tests', () => {

    describe('BTemplateClassify Management Detail Component', () => {
        let comp: BTemplateClassifyDetailComponent;
        let fixture: ComponentFixture<BTemplateClassifyDetailComponent>;
        let service: BTemplateClassifyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BTemplateClassifyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BTemplateClassifyService,
                    JhiEventManager
                ]
            }).overrideTemplate(BTemplateClassifyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BTemplateClassifyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BTemplateClassifyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BTemplateClassify(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bTemplateClassify).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
