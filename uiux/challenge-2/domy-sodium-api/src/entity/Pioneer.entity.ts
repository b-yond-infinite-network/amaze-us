import { Column, CreateDateColumn, Entity, EntityOptions, PrimaryColumn } from "typeorm";

export enum PioneerStatus {
    ACTIVE = 'active',
    APPROVED = 'approved',
    DELETED = 'deleted',
    PENDING = 'pending'
}

const config: EntityOptions = {
    name: 'pioneers'
}

@Entity(config)
export class Pioneer {

    @PrimaryColumn()
    id: number;

    @Column()
    password: string;

    @Column()
    recognition_number: string;

    @Column()
    first_name: string;

    @Column()
    last_name: string;

    @Column({
        type: 'enum',
        enum: PioneerStatus,
        default: PioneerStatus.PENDING
    })
    status: PioneerStatus;

    @CreateDateColumn()
    birthdate: string;

    @CreateDateColumn()
    created_at: string;

    @CreateDateColumn()
    updated_at: string;

}
