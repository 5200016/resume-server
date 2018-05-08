import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BResume } from './b-resume.model';
import { BResumeService } from './b-resume.service';

@Component({
    selector: 'jhi-b-resume-detail',
    templateUrl: './b-resume-detail.component.html'
})
export class BResumeDetailComponent implements OnInit, OnDestroy {

    bResume: BResume;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bResumeService: BResumeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBResumes();
    }

    load(id) {
        this.bResumeService.find(id).subscribe((bResume) => {
            this.bResume = bResume;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBResumes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bResumeListModification',
            (response) => this.load(this.bResume.id)
        );
    }
}
