import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BHonourComponent } from './b-honour.component';
import { BHonourDetailComponent } from './b-honour-detail.component';
import { BHonourPopupComponent } from './b-honour-dialog.component';
import { BHonourDeletePopupComponent } from './b-honour-delete-dialog.component';

import { Principal } from '../../shared';

export const bHonourRoute: Routes = [
    {
        path: 'b-honour',
        component: BHonourComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHonour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-honour/:id',
        component: BHonourDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHonour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bHonourPopupRoute: Routes = [
    {
        path: 'b-honour-new',
        component: BHonourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHonour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-honour/:id/edit',
        component: BHonourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHonour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-honour/:id/delete',
        component: BHonourDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bHonour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
