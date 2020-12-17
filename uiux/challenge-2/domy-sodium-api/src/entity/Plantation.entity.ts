import { Column, CreateDateColumn, Entity, EntityOptions, PrimaryGeneratedColumn } from "typeorm";

export enum PlantationStatus {
    ACTIVE     = 'active',
    CORRUPTED  = 'corrupted',
    HARVESTED  = 'harvested'
}

const config: EntityOptions = {
    name: 'plantation'
}

@Entity(config)
export class Plantation {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    seed_id: string;

    @Column()
    seed_name: string;

    @Column()
    plantation_percent: number;

    @Column({
        type: 'enum',
        enum: PlantationStatus,
        default: PlantationStatus.ACTIVE
    })
    status: PlantationStatus;

    @CreateDateColumn()
    plantation_start_date: string;

    @CreateDateColumn()
    plantation_ready_date: string;

    @CreateDateColumn()
    updated_at: string;
}