import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BResumeComponent } from './b-resume.component';
import { BResumeDetailComponent } from './b-resume-detail.component';
import { BResumePopupComponent } from './b-resume-dialog.component';
import { BResumeDeletePopupComponent } from './b-resume-delete-dialog.component';

import { Principal } from '../../shared';

export const bResumeRoute: Routes = [
    {
        path: 'b-resume',
        component: BResumeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bResume.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-resume/:id',
        component: BResumeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bResume.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bResumePopupRoute: Routes = [
    {
        path: 'b-resume-new',
        component: BResumePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bResume.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-resume/:id/edit',
        component: BResumePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bResume.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-resume/:id/delete',
        component: BResumeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bResume.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
