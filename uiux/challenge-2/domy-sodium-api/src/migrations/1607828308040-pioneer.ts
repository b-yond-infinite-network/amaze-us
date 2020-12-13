import {MigrationInterface, QueryRunner} from "typeorm";

export class pioneer1607828308040 implements MigrationInterface {
    name = 'pioneer1607828308040'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "pioneers" ADD "created_at" TIMESTAMP NOT NULL DEFAULT now()`);
        await queryRunner.query(`ALTER TABLE "pioneers" ADD "updated_at" TIMESTAMP NOT NULL DEFAULT now()`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "pioneers" DROP COLUMN "updated_at"`);
        await queryRunner.query(`ALTER TABLE "pioneers" DROP COLUMN "created_at"`);
    }

}
