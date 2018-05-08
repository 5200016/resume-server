import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BTemplate } from './b-template.model';
import { BTemplateService } from './b-template.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-template',
    templateUrl: './b-template.component.html'
})
export class BTemplateComponent implements OnInit, OnDestroy {
bTemplates: BTemplate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bTemplateService: BTemplateService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bTemplateService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bTemplates = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBTemplates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BTemplate) {
        return item.id;
    }
    registerChangeInBTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('bTemplateListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
