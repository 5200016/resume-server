import { BaseEntity } from './../../shared';

export class BTemplate implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
        public description?: string,
        public clickCount?: number,
        public price?: string,
        public type?: number,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
        public resume?: BaseEntity,
        public classifies?: BaseEntity[],
    ) {
        this.isActive = false;
    }
}
