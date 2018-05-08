import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { BHonour } from './b-honour.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BHonourService {

    private resourceUrl = 'api/b-honours';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(bHonour: BHonour): Observable<BHonour> {
        const copy = this.convert(bHonour);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(bHonour: BHonour): Observable<BHonour> {
        const copy = this.convert(bHonour);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<BHonour> {
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

    private convert(bHonour: BHonour): BHonour {
        const copy: BHonour = Object.assign({}, bHonour);

        copy.createTime = this.dateUtils.toDate(bHonour.createTime);

        copy.updateTime = this.dateUtils.toDate(bHonour.updateTime);
        return copy;
    }
}
