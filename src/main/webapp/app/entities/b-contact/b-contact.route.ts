import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BContactComponent } from './b-contact.component';
import { BContactDetailComponent } from './b-contact-detail.component';
import { BContactPopupComponent } from './b-contact-dialog.component';
import { BContactDeletePopupComponent } from './b-contact-delete-dialog.component';

import { Principal } from '../../shared';

export const bContactRoute: Routes = [
    {
        path: 'b-contact',
        component: BContactComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'b-contact/:id',
        component: BContactDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bContactPopupRoute: Routes = [
    {
        path: 'b-contact-new',
        component: BContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-contact/:id/edit',
        component: BContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'b-contact/:id/delete',
        component: BContactDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resumeApp.bContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
