import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BSelfDetailComponent } from '../../../../../../main/webapp/app/entities/b-self/b-self-detail.component';
import { BSelfService } from '../../../../../../main/webapp/app/entities/b-self/b-self.service';
import { BSelf } from '../../../../../../main/webapp/app/entities/b-self/b-self.model';

describe('Component Tests', () => {

    describe('BSelf Management Detail Component', () => {
        let comp: BSelfDetailComponent;
        let fixture: ComponentFixture<BSelfDetailComponent>;
        let service: BSelfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BSelfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BSelfService,
                    JhiEventManager
                ]
            }).overrideTemplate(BSelfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BSelfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BSelfService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BSelf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bSelf).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
