import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BHonourDetailComponent } from '../../../../../../main/webapp/app/entities/b-honour/b-honour-detail.component';
import { BHonourService } from '../../../../../../main/webapp/app/entities/b-honour/b-honour.service';
import { BHonour } from '../../../../../../main/webapp/app/entities/b-honour/b-honour.model';

describe('Component Tests', () => {

    describe('BHonour Management Detail Component', () => {
        let comp: BHonourDetailComponent;
        let fixture: ComponentFixture<BHonourDetailComponent>;
        let service: BHonourService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BHonourDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BHonourService,
                    JhiEventManager
                ]
            }).overrideTemplate(BHonourDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BHonourDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BHonourService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BHonour(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bHonour).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
