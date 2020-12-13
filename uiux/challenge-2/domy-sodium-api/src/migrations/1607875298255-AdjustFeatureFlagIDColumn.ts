import {MigrationInterface, QueryRunner} from "typeorm";

export class AdjustFeatureFlagIDColumn1607875298255 implements MigrationInterface {
    name = 'AdjustFeatureFlagIDColumn1607875298255'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`COMMENT ON COLUMN "feature_flag"."id" IS NULL`);
        await queryRunner.query(`CREATE SEQUENCE "feature_flag_id_seq" OWNED BY "feature_flag"."id"`);
        await queryRunner.query(`ALTER TABLE "feature_flag" ALTER COLUMN "id" SET DEFAULT nextval('feature_flag_id_seq')`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "feature_flag" ALTER COLUMN "id" DROP DEFAULT`);
        await queryRunner.query(`DROP SEQUENCE "feature_flag_id_seq"`);
        await queryRunner.query(`COMMENT ON COLUMN "feature_flag"."id" IS NULL`);
    }

}
