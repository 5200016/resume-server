import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SUser } from './s-user.model';
import { SUserPopupService } from './s-user-popup.service';
import { SUserService } from './s-user.service';

@Component({
    selector: 'jhi-s-user-dialog',
    templateUrl: './s-user-dialog.component.html'
})
export class SUserDialogComponent implements OnInit {

    sUser: SUser;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sUserService: SUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sUserService.update(this.sUser), false);
        } else {
            this.subscribeToSaveResponse(
                this.sUserService.create(this.sUser), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<SUser>, isCreated: boolean) {
        result.subscribe((res: SUser) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SUser, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.sUser.created'
            : 'resumeApp.sUser.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'sUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-s-user-popup',
    template: ''
})
export class SUserPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sUserPopupService: SUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.sUserPopupService
                    .open(SUserDialogComponent, params['id']);
            } else {
                this.modalRef = this.sUserPopupService
                    .open(SUserDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
