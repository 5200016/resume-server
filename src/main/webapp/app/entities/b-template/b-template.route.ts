import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BTemplateComponent } from './b-template.component';
import { BTemplateDetailComponent } from './b-template-detail.component';
import { BTemplatePopupComponent } from './b-template-dialog.component';
import { BTemplateDeletePopupComponent } from './b-template-delete-dialog.component';

import { Principal } from '../../shared';

export const bTemplateRoute: Routes = [
    {
        path: 'b-template',
        component: BTemplateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-template/:id',
        component: BTemplateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bTemplatePopupRoute: Routes = [
    {
        path: 'b-template-new',
        component: BTemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-template/:id/edit',
        component: BTemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-template/:id/delete',
        component: BTemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
