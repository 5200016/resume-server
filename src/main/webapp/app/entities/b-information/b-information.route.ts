import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BInformationComponent } from './b-information.component';
import { BInformationDetailComponent } from './b-information-detail.component';
import { BInformationPopupComponent } from './b-information-dialog.component';
import { BInformationDeletePopupComponent } from './b-information-delete-dialog.component';

import { Principal } from '../../shared';

export const bInformationRoute: Routes = [
    {
        path: 'b-information',
        component: BInformationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-information/:id',
        component: BInformationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bInformation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bInformationPopupRoute: Routes = [
    {
        path: 'b-information-new',
        component: BInformationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-information/:id/edit',
        component: BInformationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-information/:id/delete',
        component: BInformationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bInformation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
