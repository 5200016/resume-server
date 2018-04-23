import { BaseEntity } from './../../shared';

export class SUser implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public nickname?: string,
        public avatar?: string,
        public city?: string,
        public jobStatus?: number,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
