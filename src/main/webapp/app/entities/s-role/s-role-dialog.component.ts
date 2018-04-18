import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SRole } from './s-role.model';
import { SRolePopupService } from './s-role-popup.service';
import { SRoleService } from './s-role.service';
import { SLogin, SLoginService } from '../s-login';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-s-role-dialog',
    templateUrl: './s-role-dialog.component.html'
})
export class SRoleDialogComponent implements OnInit {

    sRole: SRole;
    authorities: any[];
    isSaving: boolean;

    slogins: SLogin[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sRoleService: SRoleService,
        private sLoginService: SLoginService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sLoginService.query()
            .subscribe((res: ResponseWrapper) => { this.slogins = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sRole.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sRoleService.update(this.sRole), false);
        } else {
            this.subscribeToSaveResponse(
                this.sRoleService.create(this.sRole), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<SRole>, isCreated: boolean) {
        result.subscribe((res: SRole) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SRole, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.sRole.created'
            : 'resumeApp.sRole.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'sRoleListModification', content: 'OK'});
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

    trackSLoginById(index: number, item: SLogin) {
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
    selector: 'jhi-s-role-popup',
    template: ''
})
export class SRolePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sRolePopupService: SRolePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.sRolePopupService
                    .open(SRoleDialogComponent, params['id']);
            } else {
                this.modalRef = this.sRolePopupService
                    .open(SRoleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
