import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SLogin } from './s-login.model';
import { SLoginService } from './s-login.service';

@Component({
    selector: 'jhi-s-login-detail',
    templateUrl: './s-login-detail.component.html'
})
export class SLoginDetailComponent implements OnInit, OnDestroy {

    sLogin: SLogin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sLoginService: SLoginService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSLogins();
    }

    load(id) {
        this.sLoginService.find(id).subscribe((sLogin) => {
            this.sLogin = sLogin;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSLogins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sLoginListModification',
            (response) => this.load(this.sLogin.id)
        );
    }
}
