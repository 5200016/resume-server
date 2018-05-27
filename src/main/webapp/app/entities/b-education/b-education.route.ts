import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BEducationComponent } from './b-education.component';
import { BEducationDetailComponent } from './b-education-detail.component';
import { BEducationPopupComponent } from './b-education-dialog.component';
import { BEducationDeletePopupComponent } from './b-education-delete-dialog.component';

import { Principal } from '../../shared';

export const bEducationRoute: Routes = [
    {
        path: 'b-education',
        component: BEducationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bEducation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-education/:id',
        component: BEducationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bEducation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bEducationPopupRoute: Routes = [
    {
        path: 'b-education-new',
        component: BEducationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bEducation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-education/:id/edit',
        component: BEducationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bEducation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-education/:id/delete',
        component: BEducationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bEducation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
