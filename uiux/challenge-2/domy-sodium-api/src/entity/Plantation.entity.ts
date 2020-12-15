import { Column, CreateDateColumn, Entity, EntityOptions, PrimaryGeneratedColumn } from "typeorm";

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

    @CreateDateColumn()
    plantation_start_date: string;

    @CreateDateColumn()
    plantation_ready_date: string;

    @CreateDateColumn()
    updated_at: string;
}