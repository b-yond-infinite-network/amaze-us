import {MigrationInterface, QueryRunner} from "typeorm";

export class AdjustPioneerPrimaryColumn1607874775721 implements MigrationInterface {
    name = 'AdjustPioneerPrimaryColumn1607874775721'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`COMMENT ON COLUMN "pioneers"."id" IS NULL`);
        await queryRunner.query(`CREATE SEQUENCE "pioneers_id_seq" OWNED BY "pioneers"."id"`);
        await queryRunner.query(`ALTER TABLE "pioneers" ALTER COLUMN "id" SET DEFAULT nextval('pioneers_id_seq')`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "pioneers" ALTER COLUMN "id" DROP DEFAULT`);
        await queryRunner.query(`DROP SEQUENCE "pioneers_id_seq"`);
        await queryRunner.query(`COMMENT ON COLUMN "pioneers"."id" IS NULL`);
    }

}
