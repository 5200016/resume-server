import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SLogin } from './s-login.model';
import { SLoginPopupService } from './s-login-popup.service';
import { SLoginService } from './s-login.service';
import { SRole, SRoleService } from '../s-role';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-s-login-dialog',
    templateUrl: './s-login-dialog.component.html'
})
export class SLoginDialogComponent implements OnInit {

    sLogin: SLogin;
    authorities: any[];
    isSaving: boolean;

    sroles: SRole[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sLoginService: SLoginService,
        private sRoleService: SRoleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sRoleService.query()
            .subscribe((res: ResponseWrapper) => { this.sroles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sLogin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sLoginService.update(this.sLogin), false);
        } else {
            this.subscribeToSaveResponse(
                this.sLoginService.create(this.sLogin), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<SLogin>, isCreated: boolean) {
        result.subscribe((res: SLogin) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SLogin, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.sLogin.created'
            : 'resumeApp.sLogin.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'sLoginListModification', content: 'OK'});
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

    trackSRoleById(index: number, item: SRole) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-s-login-popup',
    template: ''
})
export class SLoginPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sLoginPopupService: SLoginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.sLoginPopupService
                    .open(SLoginDialogComponent, params['id']);
            } else {
                this.modalRef = this.sLoginPopupService
                    .open(SLoginDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
