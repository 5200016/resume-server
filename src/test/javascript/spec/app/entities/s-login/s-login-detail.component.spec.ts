import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SLoginDetailComponent } from '../../../../../../main/webapp/app/entities/s-login/s-login-detail.component';
import { SLoginService } from '../../../../../../main/webapp/app/entities/s-login/s-login.service';
import { SLogin } from '../../../../../../main/webapp/app/entities/s-login/s-login.model';

describe('Component Tests', () => {

    describe('SLogin Management Detail Component', () => {
        let comp: SLoginDetailComponent;
        let fixture: ComponentFixture<SLoginDetailComponent>;
        let service: SLoginService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [SLoginDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SLoginService,
                    JhiEventManager
                ]
            }).overrideTemplate(SLoginDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SLoginDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SLoginService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SLogin(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sLogin).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
