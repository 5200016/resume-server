import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BAuthorComponent } from './b-author.component';
import { BAuthorDetailComponent } from './b-author-detail.component';
import { BAuthorPopupComponent } from './b-author-dialog.component';
import { BAuthorDeletePopupComponent } from './b-author-delete-dialog.component';

import { Principal } from '../../shared';

export const bAuthorRoute: Routes = [
    {
        path: 'b-author',
        component: BAuthorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bAuthor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-author/:id',
        component: BAuthorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bAuthor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bAuthorPopupRoute: Routes = [
    {
        path: 'b-author-new',
        component: BAuthorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bAuthor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-author/:id/edit',
        component: BAuthorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bAuthor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-author/:id/delete',
        component: BAuthorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bAuthor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
