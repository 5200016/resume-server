import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SRole } from './s-role.model';
import { SRolePopupService } from './s-role-popup.service';
import { SRoleService } from './s-role.service';

@Component({
    selector: 'jhi-s-role-delete-dialog',
    templateUrl: './s-role-delete-dialog.component.html'
})
export class SRoleDeleteDialogComponent {

    sRole: SRole;

    constructor(
        private sRoleService: SRoleService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sRoleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sRoleListModification',
                content: 'Deleted an sRole'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.sRole.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-s-role-delete-popup',
    template: ''
})
export class SRoleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sRolePopupService: SRolePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.sRolePopupService
                .open(SRoleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
