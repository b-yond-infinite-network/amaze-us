import {MigrationInterface, QueryRunner} from "typeorm";

export class PlantationStatus1608117642092 implements MigrationInterface {
    name = 'PlantationStatus1608117642092'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "plantation" ADD "status" "plantation_status_enum" NOT NULL DEFAULT 'active'`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "plantation" DROP COLUMN "status"`);
    }

}
