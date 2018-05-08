import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BWorkProjectDetailComponent } from '../../../../../../main/webapp/app/entities/b-work-project/b-work-project-detail.component';
import { BWorkProjectService } from '../../../../../../main/webapp/app/entities/b-work-project/b-work-project.service';
import { BWorkProject } from '../../../../../../main/webapp/app/entities/b-work-project/b-work-project.model';

describe('Component Tests', () => {

    describe('BWorkProject Management Detail Component', () => {
        let comp: BWorkProjectDetailComponent;
        let fixture: ComponentFixture<BWorkProjectDetailComponent>;
        let service: BWorkProjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BWorkProjectDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BWorkProjectService,
                    JhiEventManager
                ]
            }).overrideTemplate(BWorkProjectDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BWorkProjectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BWorkProjectService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BWorkProject(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bWorkProject).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
