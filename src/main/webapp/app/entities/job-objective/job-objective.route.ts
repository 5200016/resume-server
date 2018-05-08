import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { JobObjectiveComponent } from './job-objective.component';
import { JobObjectiveDetailComponent } from './job-objective-detail.component';
import { JobObjectivePopupComponent } from './job-objective-dialog.component';
import { JobObjectiveDeletePopupComponent } from './job-objective-delete-dialog.component';

import { Principal } from '../../shared';

export const jobObjectiveRoute: Routes = [
    {
        path: 'job-objective',
        component: JobObjectiveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.jobObjective.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-objective/:id',
        component: JobObjectiveDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.jobObjective.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobObjectivePopupRoute: Routes = [
    {
        path: 'job-objective-new',
        component: JobObjectivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.jobObjective.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-objective/:id/edit',
        component: JobObjectivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.jobObjective.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-objective/:id/delete',
        component: JobObjectiveDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.jobObjective.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
