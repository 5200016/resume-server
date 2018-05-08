import { BaseEntity } from './../../shared';

export class BHobby implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public description?: any,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
