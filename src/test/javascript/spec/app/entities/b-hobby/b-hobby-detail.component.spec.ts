import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BHobbyDetailComponent } from '../../../../../../main/webapp/app/entities/b-hobby/b-hobby-detail.component';
import { BHobbyService } from '../../../../../../main/webapp/app/entities/b-hobby/b-hobby.service';
import { BHobby } from '../../../../../../main/webapp/app/entities/b-hobby/b-hobby.model';

describe('Component Tests', () => {

    describe('BHobby Management Detail Component', () => {
        let comp: BHobbyDetailComponent;
        let fixture: ComponentFixture<BHobbyDetailComponent>;
        let service: BHobbyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BHobbyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BHobbyService,
                    JhiEventManager
                ]
            }).overrideTemplate(BHobbyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BHobbyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BHobbyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BHobby(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bHobby).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
