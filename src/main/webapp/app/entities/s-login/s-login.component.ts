import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { SLogin } from './s-login.model';
import { SLoginService } from './s-login.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-s-login',
    templateUrl: './s-login.component.html'
})
export class SLoginComponent implements OnInit, OnDestroy {
sLogins: SLogin[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sLoginService: SLoginService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sLoginService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sLogins = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSLogins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SLogin) {
        return item.id;
    }
    registerChangeInSLogins() {
        this.eventSubscriber = this.eventManager.subscribe('sLoginListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
