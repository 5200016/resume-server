import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { BHobby } from './b-hobby.model';
import { BHobbyService } from './b-hobby.service';

@Component({
    selector: 'jhi-b-hobby-detail',
    templateUrl: './b-hobby-detail.component.html'
})
export class BHobbyDetailComponent implements OnInit, OnDestroy {

    bHobby: BHobby;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bHobbyService: BHobbyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBHobbies();
    }

    load(id) {
        this.bHobbyService.find(id).subscribe((bHobby) => {
            this.bHobby = bHobby;
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

    registerChangeInBHobbies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bHobbyListModification',
            (response) => this.load(this.bHobby.id)
        );
    }
}
