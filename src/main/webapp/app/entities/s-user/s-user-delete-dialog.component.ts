import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SUser } from './s-user.model';
import { SUserPopupService } from './s-user-popup.service';
import { SUserService } from './s-user.service';

@Component({
    selector: 'jhi-s-user-delete-dialog',
    templateUrl: './s-user-delete-dialog.component.html'
})
export class SUserDeleteDialogComponent {

    sUser: SUser;

    constructor(
        private sUserService: SUserService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sUserListModification',
                content: 'Deleted an sUser'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.sUser.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-s-user-delete-popup',
    template: ''
})
export class SUserDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sUserPopupService: SUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.sUserPopupService
                .open(SUserDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
