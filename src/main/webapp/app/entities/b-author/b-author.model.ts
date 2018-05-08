import { BaseEntity } from './../../shared';

export class BAuthor implements BaseEntity {
    constructor(
        public id?: number,
        public username?: string,
        public likeCount?: number,
        public writeCount?: number,
        public introduction?: any,
        public qualifications?: any,
        public protection?: any,
        public phone?: string,
        public responseTime?: string,
        public extra?: string,
        public isActive?: boolean,
        public createTime?: any,
        public updateTime?: any,
    ) {
        this.isActive = false;
    }
}
