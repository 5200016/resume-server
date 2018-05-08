import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { JobObjective } from './job-objective.model';
import { JobObjectiveService } from './job-objective.service';

@Component({
    selector: 'jhi-job-objective-detail',
    templateUrl: './job-objective-detail.component.html'
})
export class JobObjectiveDetailComponent implements OnInit, OnDestroy {

    jobObjective: JobObjective;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobObjectiveService: JobObjectiveService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobObjectives();
    }

    load(id) {
        this.jobObjectiveService.find(id).subscribe((jobObjective) => {
            this.jobObjective = jobObjective;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobObjectives() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobObjectiveListModification',
            (response) => this.load(this.jobObjective.id)
        );
    }
}
