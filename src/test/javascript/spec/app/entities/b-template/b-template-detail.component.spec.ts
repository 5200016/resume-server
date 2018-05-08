import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BTemplateDetailComponent } from '../../../../../../main/webapp/app/entities/b-template/b-template-detail.component';
import { BTemplateService } from '../../../../../../main/webapp/app/entities/b-template/b-template.service';
import { BTemplate } from '../../../../../../main/webapp/app/entities/b-template/b-template.model';

describe('Component Tests', () => {

    describe('BTemplate Management Detail Component', () => {
        let comp: BTemplateDetailComponent;
        let fixture: ComponentFixture<BTemplateDetailComponent>;
        let service: BTemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BTemplateDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BTemplateService,
                    JhiEventManager
                ]
            }).overrideTemplate(BTemplateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BTemplateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BTemplateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BTemplate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bTemplate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
