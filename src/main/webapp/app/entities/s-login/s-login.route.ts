import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SLoginComponent } from './s-login.component';
import { SLoginDetailComponent } from './s-login-detail.component';
import { SLoginPopupComponent } from './s-login-dialog.component';
import { SLoginDeletePopupComponent } from './s-login-delete-dialog.component';

import { Principal } from '../../shared';

export const sLoginRoute: Routes = [
    {
        path: 's-login',
        component: SLoginComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sLogin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 's-login/:id',
        component: SLoginDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sLogin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sLoginPopupRoute: Routes = [
    {
        path: 's-login-new',
        component: SLoginPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sLogin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 's-login/:id/edit',
        component: SLoginPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sLogin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 's-login/:id/delete',
        component: SLoginDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.sLogin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
