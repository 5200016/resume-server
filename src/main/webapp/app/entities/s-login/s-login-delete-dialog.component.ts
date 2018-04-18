import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SLogin } from './s-login.model';
import { SLoginPopupService } from './s-login-popup.service';
import { SLoginService } from './s-login.service';

@Component({
    selector: 'jhi-s-login-delete-dialog',
    templateUrl: './s-login-delete-dialog.component.html'
})
export class SLoginDeleteDialogComponent {

    sLogin: SLogin;

    constructor(
        private sLoginService: SLoginService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sLoginService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sLoginListModification',
                content: 'Deleted an sLogin'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.sLogin.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-s-login-delete-popup',
    template: ''
})
export class SLoginDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sLoginPopupService: SLoginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.sLoginPopupService
                .open(SLoginDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
