import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { BSelf } from './b-self.model';
import { BSelfService } from './b-self.service';

@Component({
    selector: 'jhi-b-self-detail',
    templateUrl: './b-self-detail.component.html'
})
export class BSelfDetailComponent implements OnInit, OnDestroy {

    bSelf: BSelf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bSelfService: BSelfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBSelves();
    }

    load(id) {
        this.bSelfService.find(id).subscribe((bSelf) => {
            this.bSelf = bSelf;
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

    registerChangeInBSelves() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bSelfListModification',
            (response) => this.load(this.bSelf.id)
        );
    }
}
