import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SRoleDetailComponent } from '../../../../../../main/webapp/app/entities/s-role/s-role-detail.component';
import { SRoleService } from '../../../../../../main/webapp/app/entities/s-role/s-role.service';
import { SRole } from '../../../../../../main/webapp/app/entities/s-role/s-role.model';

describe('Component Tests', () => {

    describe('SRole Management Detail Component', () => {
        let comp: SRoleDetailComponent;
        let fixture: ComponentFixture<SRoleDetailComponent>;
        let service: SRoleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [SRoleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SRoleService,
                    JhiEventManager
                ]
            }).overrideTemplate(SRoleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SRoleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SRoleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SRole(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sRole).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
