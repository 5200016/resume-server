import { BaseEntity } from './../../shared';

export class BEducation implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public enterTime?: string,
        public stopTime?: string,
        public school?: string,
        public speciality?: string,
        public description?: any,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
