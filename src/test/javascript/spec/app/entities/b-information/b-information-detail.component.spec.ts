import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BInformationDetailComponent } from '../../../../../../main/webapp/app/entities/b-information/b-information-detail.component';
import { BInformationService } from '../../../../../../main/webapp/app/entities/b-information/b-information.service';
import { BInformation } from '../../../../../../main/webapp/app/entities/b-information/b-information.model';

describe('Component Tests', () => {

    describe('BInformation Management Detail Component', () => {
        let comp: BInformationDetailComponent;
        let fixture: ComponentFixture<BInformationDetailComponent>;
        let service: BInformationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BInformationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BInformationService,
                    JhiEventManager
                ]
            }).overrideTemplate(BInformationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BInformationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BInformationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BInformation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bInformation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
