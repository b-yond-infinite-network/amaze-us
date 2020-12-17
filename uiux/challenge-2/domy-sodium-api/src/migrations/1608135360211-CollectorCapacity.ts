import {MigrationInterface, QueryRunner} from "typeorm";

export class CollectorCapacity1608135360211 implements MigrationInterface {
    name = 'CollectorCapacity1608135360211'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "collectors" ADD "available_capacity" integer NOT NULL`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "collectors" DROP COLUMN "available_capacity"`);
    }

}
