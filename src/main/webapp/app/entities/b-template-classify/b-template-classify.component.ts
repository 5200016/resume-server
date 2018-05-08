import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BTemplateClassify } from './b-template-classify.model';
import { BTemplateClassifyService } from './b-template-classify.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-template-classify',
    templateUrl: './b-template-classify.component.html'
})
export class BTemplateClassifyComponent implements OnInit, OnDestroy {
bTemplateClassifies: BTemplateClassify[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bTemplateClassifyService: BTemplateClassifyService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bTemplateClassifyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bTemplateClassifies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBTemplateClassifies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BTemplateClassify) {
        return item.id;
    }
    registerChangeInBTemplateClassifies() {
        this.eventSubscriber = this.eventManager.subscribe('bTemplateClassifyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
