import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BContactDetailComponent } from '../../../../../../main/webapp/app/entities/b-contact/b-contact-detail.component';
import { BContactService } from '../../../../../../main/webapp/app/entities/b-contact/b-contact.service';
import { BContact } from '../../../../../../main/webapp/app/entities/b-contact/b-contact.model';

describe('Component Tests', () => {

    describe('BContact Management Detail Component', () => {
        let comp: BContactDetailComponent;
        let fixture: ComponentFixture<BContactDetailComponent>;
        let service: BContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BContactDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BContactService,
                    JhiEventManager
                ]
            }).overrideTemplate(BContactDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BContactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BContactService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BContact(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bContact).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
