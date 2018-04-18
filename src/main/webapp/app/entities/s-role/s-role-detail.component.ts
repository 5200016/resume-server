import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SRole } from './s-role.model';
import { SRoleService } from './s-role.service';

@Component({
    selector: 'jhi-s-role-detail',
    templateUrl: './s-role-detail.component.html'
})
export class SRoleDetailComponent implements OnInit, OnDestroy {

    sRole: SRole;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sRoleService: SRoleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSRoles();
    }

    load(id) {
        this.sRoleService.find(id).subscribe((sRole) => {
            this.sRole = sRole;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSRoles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sRoleListModification',
            (response) => this.load(this.sRole.id)
        );
    }
}
