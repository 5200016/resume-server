import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BWorkDetailComponent } from '../../../../../../main/webapp/app/entities/b-work/b-work-detail.component';
import { BWorkService } from '../../../../../../main/webapp/app/entities/b-work/b-work.service';
import { BWork } from '../../../../../../main/webapp/app/entities/b-work/b-work.model';

describe('Component Tests', () => {

    describe('BWork Management Detail Component', () => {
        let comp: BWorkDetailComponent;
        let fixture: ComponentFixture<BWorkDetailComponent>;
        let service: BWorkService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BWorkDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BWorkService,
                    JhiEventManager
                ]
            }).overrideTemplate(BWorkDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BWorkDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BWorkService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BWork(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bWork).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
