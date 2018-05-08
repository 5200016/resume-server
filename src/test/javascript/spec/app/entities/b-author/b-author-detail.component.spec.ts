import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ResumeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BAuthorDetailComponent } from '../../../../../../main/webapp/app/entities/b-author/b-author-detail.component';
import { BAuthorService } from '../../../../../../main/webapp/app/entities/b-author/b-author.service';
import { BAuthor } from '../../../../../../main/webapp/app/entities/b-author/b-author.model';

describe('Component Tests', () => {

    describe('BAuthor Management Detail Component', () => {
        let comp: BAuthorDetailComponent;
        let fixture: ComponentFixture<BAuthorDetailComponent>;
        let service: BAuthorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ResumeTestModule],
                declarations: [BAuthorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BAuthorService,
                    JhiEventManager
                ]
            }).overrideTemplate(BAuthorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BAuthorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BAuthorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BAuthor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bAuthor).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
