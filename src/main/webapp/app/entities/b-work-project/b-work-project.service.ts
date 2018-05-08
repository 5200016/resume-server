import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { BWorkProject } from './b-work-project.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BWorkProjectService {

    private resourceUrl = 'api/b-work-projects';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(bWorkProject: BWorkProject): Observable<BWorkProject> {
        const copy = this.convert(bWorkProject);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(bWorkProject: BWorkProject): Observable<BWorkProject> {
        const copy = this.convert(bWorkProject);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<BWorkProject> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(entity.createTime);
        entity.updateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateTime);
    }

    private convert(bWorkProject: BWorkProject): BWorkProject {
        const copy: BWorkProject = Object.assign({}, bWorkProject);

        copy.createTime = this.dateUtils.toDate(bWorkProject.createTime);

        copy.updateTime = this.dateUtils.toDate(bWorkProject.updateTime);
        return copy;
    }
}
