import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SUserDetailComponent } from '../../../../../../main/webapp/app/entities/s-user/s-user-detail.component';
import { SUserService } from '../../../../../../main/webapp/app/entities/s-user/s-user.service';
import { SUser } from '../../../../../../main/webapp/app/entities/s-user/s-user.model';

describe('Component Tests', () => {

    describe('SUser Management Detail Component', () => {
        let comp: SUserDetailComponent;
        let fixture: ComponentFixture<SUserDetailComponent>;
        let service: SUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [SUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(SUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SUserService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sUser).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
