import { Column, CreateDateColumn, Entity, EntityOptions, PrimaryGeneratedColumn } from "typeorm";

const config: EntityOptions = {
    name: 'collectors'
}

@Entity(config)
export class Collectors {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    input: number;

    @Column()
    output: number;

    @Column()
    tool_name: string;

    @Column()
    available_capacity: number;

    @CreateDateColumn()
    updated_at: string; 
}