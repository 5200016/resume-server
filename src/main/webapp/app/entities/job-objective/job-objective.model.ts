import { BaseEntity } from './../../shared';

export class JobObjective implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public jobTitle?: string,
        public jobType?: string,
        public enterTime?: string,
        public salaryStart?: string,
        public salaryEnd?: string,
        public expectCity?: string,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
