import { BaseEntity } from './../../shared';

export class BTemplateClassify implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: number,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
        public templates?: BaseEntity[],
    ) {
        this.isActive = false;
    }
}
