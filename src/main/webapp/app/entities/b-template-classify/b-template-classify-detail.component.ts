import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BTemplateClassify } from './b-template-classify.model';
import { BTemplateClassifyService } from './b-template-classify.service';

@Component({
    selector: 'jhi-b-template-classify-detail',
    templateUrl: './b-template-classify-detail.component.html'
})
export class BTemplateClassifyDetailComponent implements OnInit, OnDestroy {

    bTemplateClassify: BTemplateClassify;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bTemplateClassifyService: BTemplateClassifyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBTemplateClassifies();
    }

    load(id) {
        this.bTemplateClassifyService.find(id).subscribe((bTemplateClassify) => {
            this.bTemplateClassify = bTemplateClassify;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBTemplateClassifies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bTemplateClassifyListModification',
            (response) => this.load(this.bTemplateClassify.id)
        );
    }
}
