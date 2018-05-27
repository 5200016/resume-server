import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { BEducation } from './b-education.model';
import { BEducationService } from './b-education.service';

@Component({
    selector: 'jhi-b-education-detail',
    templateUrl: './b-education-detail.component.html'
})
export class BEducationDetailComponent implements OnInit, OnDestroy {

    bEducation: BEducation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bEducationService: BEducationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBEducations();
    }

    load(id) {
        this.bEducationService.find(id).subscribe((bEducation) => {
            this.bEducation = bEducation;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBEducations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bEducationListModification',
            (response) => this.load(this.bEducation.id)
        );
    }
}
