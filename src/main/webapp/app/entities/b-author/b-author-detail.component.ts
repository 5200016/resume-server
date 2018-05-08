import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { BAuthor } from './b-author.model';
import { BAuthorService } from './b-author.service';

@Component({
    selector: 'jhi-b-author-detail',
    templateUrl: './b-author-detail.component.html'
})
export class BAuthorDetailComponent implements OnInit, OnDestroy {

    bAuthor: BAuthor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private bAuthorService: BAuthorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBAuthors();
    }

    load(id) {
        this.bAuthorService.find(id).subscribe((bAuthor) => {
            this.bAuthor = bAuthor;
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

    registerChangeInBAuthors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bAuthorListModification',
            (response) => this.load(this.bAuthor.id)
        );
    }
}
