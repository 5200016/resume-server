import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { BResume } from './b-resume.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BResumeService {

    private resourceUrl = 'api/b-resumes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(bResume: BResume): Observable<BResume> {
        const copy = this.convert(bResume);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(bResume: BResume): Observable<BResume> {
        const copy = this.convert(bResume);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<BResume> {
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

    private convert(bResume: BResume): BResume {
        const copy: BResume = Object.assign({}, bResume);

        copy.createTime = this.dateUtils.toDate(bResume.createTime);

        copy.updateTime = this.dateUtils.toDate(bResume.updateTime);
        return copy;
    }
}
