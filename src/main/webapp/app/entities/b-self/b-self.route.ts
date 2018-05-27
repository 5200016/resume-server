import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BSelfComponent } from './b-self.component';
import { BSelfDetailComponent } from './b-self-detail.component';
import { BSelfPopupComponent } from './b-self-dialog.component';
import { BSelfDeletePopupComponent } from './b-self-delete-dialog.component';

import { Principal } from '../../shared';

export const bSelfRoute: Routes = [
    {
        path: 'b-self',
        component: BSelfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bSelf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-self/:id',
        component: BSelfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bSelf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bSelfPopupRoute: Routes = [
    {
        path: 'b-self-new',
        component: BSelfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bSelf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-self/:id/edit',
        component: BSelfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bSelf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-self/:id/delete',
        component: BSelfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bSelf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
