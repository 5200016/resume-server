import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BWorkComponent } from './b-work.component';
import { BWorkDetailComponent } from './b-work-detail.component';
import { BWorkPopupComponent } from './b-work-dialog.component';
import { BWorkDeletePopupComponent } from './b-work-delete-dialog.component';

import { Principal } from '../../shared';

export const bWorkRoute: Routes = [
    {
        path: 'b-work',
        component: BWorkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWork.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-work/:id',
        component: BWorkDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWork.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bWorkPopupRoute: Routes = [
    {
        path: 'b-work-new',
        component: BWorkPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWork.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-work/:id/edit',
        component: BWorkPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWork.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-work/:id/delete',
        component: BWorkDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWork.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
