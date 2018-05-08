import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BWorkProjectComponent } from './b-work-project.component';
import { BWorkProjectDetailComponent } from './b-work-project-detail.component';
import { BWorkProjectPopupComponent } from './b-work-project-dialog.component';
import { BWorkProjectDeletePopupComponent } from './b-work-project-delete-dialog.component';

import { Principal } from '../../shared';

export const bWorkProjectRoute: Routes = [
    {
        path: 'b-work-project',
        component: BWorkProjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWorkProject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-work-project/:id',
        component: BWorkProjectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWorkProject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bWorkProjectPopupRoute: Routes = [
    {
        path: 'b-work-project-new',
        component: BWorkProjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWorkProject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-work-project/:id/edit',
        component: BWorkProjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWorkProject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-work-project/:id/delete',
        component: BWorkProjectDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bWorkProject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
