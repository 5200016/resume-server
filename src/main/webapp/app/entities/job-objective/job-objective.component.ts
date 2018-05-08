import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { JobObjective } from './job-objective.model';
import { JobObjectiveService } from './job-objective.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-job-objective',
    templateUrl: './job-objective.component.html'
})
export class JobObjectiveComponent implements OnInit, OnDestroy {
jobObjectives: JobObjective[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jobObjectiveService: JobObjectiveService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.jobObjectiveService.query().subscribe(
            (res: ResponseWrapper) => {
                this.jobObjectives = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInJobObjectives();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: JobObjective) {
        return item.id;
    }
    registerChangeInJobObjectives() {
        this.eventSubscriber = this.eventManager.subscribe('jobObjectiveListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
