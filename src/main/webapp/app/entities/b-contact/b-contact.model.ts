import { BaseEntity } from './../../shared';

export class BContact implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public phone?: string,
        public email?: string,
        public qq?: string,
        public wechat?: string,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
