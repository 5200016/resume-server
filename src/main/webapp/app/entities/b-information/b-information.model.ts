import { BaseEntity } from './../../shared';

export class BInformation implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public name?: string,
        public sex?: string,
        public birthday?: string,
        public education?: string,
        public speciality?: string,
        public workTime?: string,
        public politicsStatus?: string,
        public marriage?: string,
        public skill?: any,
        public address?: string,
        public introduction?: any,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
