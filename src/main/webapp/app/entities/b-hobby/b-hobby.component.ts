import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BHobby } from './b-hobby.model';
import { BHobbyService } from './b-hobby.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-b-hobby',
    templateUrl: './b-hobby.component.html'
})
export class BHobbyComponent implements OnInit, OnDestroy {
bHobbies: BHobby[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bHobbyService: BHobbyService,
        private alertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bHobbyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bHobbies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBHobbies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BHobby) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInBHobbies() {
        this.eventSubscriber = this.eventManager.subscribe('bHobbyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
