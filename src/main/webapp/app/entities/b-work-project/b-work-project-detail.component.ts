import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BWorkProject } from './b-work-project.model';
import { BWorkProjectService } from './b-work-project.service';

@Component({
    selector: 'jhi-b-work-project-detail',
    templateUrl: './b-work-project-detail.component.html'
})
export class BWorkProjectDetailComponent implements OnInit, OnDestroy {

    bWorkProject: BWorkProject;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bWorkProjectService: BWorkProjectService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBWorkProjects();
    }

    load(id) {
        this.bWorkProjectService.find(id).subscribe((bWorkProject) => {
            this.bWorkProject = bWorkProject;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBWorkProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bWorkProjectListModification',
            (response) => this.load(this.bWorkProject.id)
        );
    }
}
