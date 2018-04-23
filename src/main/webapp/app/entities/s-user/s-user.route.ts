import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SUserComponent } from './s-user.component';
import { SUserDetailComponent } from './s-user-detail.component';
import { SUserPopupComponent } from './s-user-dialog.component';
import { SUserDeletePopupComponent } from './s-user-delete-dialog.component';

import { Principal } from '../../shared';

export const sUserRoute: Routes = [
    {
        path: 's-user',
        component: SUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 's-user/:id',
        component: SUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sUserPopupRoute: Routes = [
    {
        path: 's-user-new',
        component: SUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 's-user/:id/edit',
        component: SUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 's-user/:id/delete',
        component: SUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
