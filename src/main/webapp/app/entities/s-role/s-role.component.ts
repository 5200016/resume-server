import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { SRole } from './s-role.model';
import { SRoleService } from './s-role.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-s-role',
    templateUrl: './s-role.component.html'
})
export class SRoleComponent implements OnInit, OnDestroy {
sRoles: SRole[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sRoleService: SRoleService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sRoleService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sRoles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSRoles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SRole) {
        return item.id;
    }
    registerChangeInSRoles() {
        this.eventSubscriber = this.eventManager.subscribe('sRoleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
