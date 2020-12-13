import { Column, CreateDateColumn, Entity, EntityOptions, PrimaryGeneratedColumn } from "typeorm";

const config: EntityOptions = {
    name: 'feature_flag'
}

@Entity(config)
export class FeatureFlag {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    feature: string;

    @Column()
    recognition_number: string;

    @CreateDateColumn()
    created_at: string; 
}