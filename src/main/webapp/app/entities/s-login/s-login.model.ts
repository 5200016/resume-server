import { BaseEntity } from './../../shared';

export class SLogin implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public password?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
        public roles?: BaseEntity[],
    ) {
        this.isActive = false;
    }
}
