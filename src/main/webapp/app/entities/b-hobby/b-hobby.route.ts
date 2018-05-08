import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BHobbyComponent } from './b-hobby.component';
import { BHobbyDetailComponent } from './b-hobby-detail.component';
import { BHobbyPopupComponent } from './b-hobby-dialog.component';
import { BHobbyDeletePopupComponent } from './b-hobby-delete-dialog.component';

import { Principal } from '../../shared';

export const bHobbyRoute: Routes = [
    {
        path: 'b-hobby',
        component: BHobbyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHobby.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-hobby/:id',
        component: BHobbyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHobby.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bHobbyPopupRoute: Routes = [
    {
        path: 'b-hobby-new',
        component: BHobbyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHobby.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-hobby/:id/edit',
        component: BHobbyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHobby.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-hobby/:id/delete',
        component: BHobbyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHobby.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
