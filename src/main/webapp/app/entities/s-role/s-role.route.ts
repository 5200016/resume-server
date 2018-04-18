import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SRoleComponent } from './s-role.component';
import { SRoleDetailComponent } from './s-role-detail.component';
import { SRolePopupComponent } from './s-role-dialog.component';
import { SRoleDeletePopupComponent } from './s-role-delete-dialog.component';

import { Principal } from '../../shared';

export const sRoleRoute: Routes = [
    {
        path: 's-role',
        component: SRoleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sRole.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 's-role/:id',
        component: SRoleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sRole.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sRolePopupRoute: Routes = [
    {
        path: 's-role-new',
        component: SRolePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sRole.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 's-role/:id/edit',
        component: SRolePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sRole.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 's-role/:id/delete',
        component: SRoleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sRole.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
