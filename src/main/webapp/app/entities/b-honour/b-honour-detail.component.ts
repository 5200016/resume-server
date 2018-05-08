import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { BHonour } from './b-honour.model';
import { BHonourService } from './b-honour.service';

@Component({
    selector: 'jhi-b-honour-detail',
    templateUrl: './b-honour-detail.component.html'
})
export class BHonourDetailComponent implements OnInit, OnDestroy {

    bHonour: BHonour;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bHonourService: BHonourService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBHonours();
    }

    load(id) {
        this.bHonourService.find(id).subscribe((bHonour) => {
            this.bHonour = bHonour;
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

    registerChangeInBHonours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bHonourListModification',
            (response) => this.load(this.bHonour.id)
        );
    }
}
