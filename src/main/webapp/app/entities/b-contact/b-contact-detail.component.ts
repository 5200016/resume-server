import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BContact } from './b-contact.model';
import { BContactService } from './b-contact.service';

@Component({
    selector: 'jhi-b-contact-detail',
    templateUrl: './b-contact-detail.component.html'
})
export class BContactDetailComponent implements OnInit, OnDestroy {

    bContact: BContact;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bContactService: BContactService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBContacts();
    }

    load(id) {
        this.bContactService.find(id).subscribe((bContact) => {
            this.bContact = bContact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBContacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bContactListModification',
            (response) => this.load(this.bContact.id)
        );
    }
}
