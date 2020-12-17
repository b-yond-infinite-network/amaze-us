import { Column, CreateDateColumn, Entity, EntityOptions, PrimaryGeneratedColumn } from "typeorm";

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

    @PrimaryGeneratedColumn()
    id: number;

    @Column({ select: false })
    password: string;

    @Column({
        unique: true
    })
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
