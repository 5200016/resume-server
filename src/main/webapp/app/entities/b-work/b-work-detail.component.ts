import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BWork } from './b-work.model';
import { BWorkService } from './b-work.service';

@Component({
    selector: 'jhi-b-work-detail',
    templateUrl: './b-work-detail.component.html'
})
export class BWorkDetailComponent implements OnInit, OnDestroy {

    bWork: BWork;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bWorkService: BWorkService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBWorks();
    }

    load(id) {
        this.bWorkService.find(id).subscribe((bWork) => {
            this.bWork = bWork;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBWorks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bWorkListModification',
            (response) => this.load(this.bWork.id)
        );
    }
}
