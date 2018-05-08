import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BWork } from './b-work.model';
import { BWorkService } from './b-work.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-work',
    templateUrl: './b-work.component.html'
})
export class BWorkComponent implements OnInit, OnDestroy {
bWorks: BWork[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bWorkService: BWorkService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bWorkService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bWorks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBWorks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BWork) {
        return item.id;
    }
    registerChangeInBWorks() {
        this.eventSubscriber = this.eventManager.subscribe('bWorkListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
