import { BaseEntity } from './../../shared';

export class BResume implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public url?: string,
        public status?: number,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
