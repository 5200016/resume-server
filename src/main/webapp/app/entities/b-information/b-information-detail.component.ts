import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { BInformation } from './b-information.model';
import { BInformationService } from './b-information.service';

@Component({
    selector: 'jhi-b-information-detail',
    templateUrl: './b-information-detail.component.html'
})
export class BInformationDetailComponent implements OnInit, OnDestroy {

    bInformation: BInformation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bInformationService: BInformationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBInformations();
    }

    load(id) {
        this.bInformationService.find(id).subscribe((bInformation) => {
            this.bInformation = bInformation;
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

    registerChangeInBInformations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bInformationListModification',
            (response) => this.load(this.bInformation.id)
        );
    }
}
