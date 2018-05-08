import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BTemplateClassifyComponent } from './b-template-classify.component';
import { BTemplateClassifyDetailComponent } from './b-template-classify-detail.component';
import { BTemplateClassifyPopupComponent } from './b-template-classify-dialog.component';
import { BTemplateClassifyDeletePopupComponent } from './b-template-classify-delete-dialog.component';

import { Principal } from '../../shared';

export const bTemplateClassifyRoute: Routes = [
    {
        path: 'b-template-classify',
        component: BTemplateClassifyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplateClassify.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-template-classify/:id',
        component: BTemplateClassifyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplateClassify.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bTemplateClassifyPopupRoute: Routes = [
    {
        path: 'b-template-classify-new',
        component: BTemplateClassifyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplateClassify.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-template-classify/:id/edit',
        component: BTemplateClassifyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplateClassify.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-template-classify/:id/delete',
        component: BTemplateClassifyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bTemplateClassify.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
