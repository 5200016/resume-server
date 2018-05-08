import { BaseEntity } from './../../shared';

export class BWorkProject implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public responsible?: string,
        public startTime?: string,
        public endTime?: string,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
        public bWork?: BaseEntity,
    ) {
        this.isActive = false;
    }
}
