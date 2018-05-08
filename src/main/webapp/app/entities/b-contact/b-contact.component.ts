import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BContact } from './b-contact.model';
import { BContactService } from './b-contact.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-contact',
    templateUrl: './b-contact.component.html'
})
export class BContactComponent implements OnInit, OnDestroy {
bContacts: BContact[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bContactService: BContactService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bContactService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bContacts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBContacts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BContact) {
        return item.id;
    }
    registerChangeInBContacts() {
        this.eventSubscriber = this.eventManager.subscribe('bContactListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
