import { BaseEntity } from './../../shared';

export class SRole implements BaseEntity {
    constructor(
        public id?: number,
        public roleName?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
        public logins?: BaseEntity[],
    ) {
        this.isActive = false;
    }
}
