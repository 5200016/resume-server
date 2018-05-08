import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BResume } from './b-resume.model';
import { BResumeService } from './b-resume.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-resume',
    templateUrl: './b-resume.component.html'
})
export class BResumeComponent implements OnInit, OnDestroy {
bResumes: BResume[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bResumeService: BResumeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bResumeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bResumes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBResumes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BResume) {
        return item.id;
    }
    registerChangeInBResumes() {
        this.eventSubscriber = this.eventManager.subscribe('bResumeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
