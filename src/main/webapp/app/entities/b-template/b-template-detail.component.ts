import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BTemplate } from './b-template.model';
import { BTemplateService } from './b-template.service';

@Component({
    selector: 'jhi-b-template-detail',
    templateUrl: './b-template-detail.component.html'
})
export class BTemplateDetailComponent implements OnInit, OnDestroy {

    bTemplate: BTemplate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bTemplateService: BTemplateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBTemplates();
    }

    load(id) {
        this.bTemplateService.find(id).subscribe((bTemplate) => {
            this.bTemplate = bTemplate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBTemplates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bTemplateListModification',
            (response) => this.load(this.bTemplate.id)
        );
    }
}
