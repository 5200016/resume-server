import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BAuthor } from './b-author.model';
import { BAuthorService } from './b-author.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-author',
    templateUrl: './b-author.component.html'
})
export class BAuthorComponent implements OnInit, OnDestroy {
bAuthors: BAuthor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bAuthorService: BAuthorService,
        private alertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bAuthorService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bAuthors = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBAuthors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BAuthor) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInBAuthors() {
        this.eventSubscriber = this.eventManager.subscribe('bAuthorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
