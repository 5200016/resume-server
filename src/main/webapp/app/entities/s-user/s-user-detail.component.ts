import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SUser } from './s-user.model';
import { SUserService } from './s-user.service';

@Component({
    selector: 'jhi-s-user-detail',
    templateUrl: './s-user-detail.component.html'
})
export class SUserDetailComponent implements OnInit, OnDestroy {

    sUser: SUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sUserService: SUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSUsers();
    }

    load(id) {
        this.sUserService.find(id).subscribe((sUser) => {
            this.sUser = sUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sUserListModification',
            (response) => this.load(this.sUser.id)
        );
    }
}
