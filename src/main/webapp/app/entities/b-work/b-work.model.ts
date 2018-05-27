import { BaseEntity } from './../../shared';

export class BWork implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public workTime?: string,
        public companyName?: string,
        public jobTitle?: string,
        public startTime?: string,
        public endTime?: string,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
