import {MigrationInterface, QueryRunner} from "typeorm";

export class FeatureFlag1607871266261 implements MigrationInterface {
    name = 'FeatureFlag1607871266261'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE "feature_flag" ("id" integer NOT NULL, "feature" character varying NOT NULL, "recognition_number" character varying NOT NULL, "created_at" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "PK_f390205410d884907604a90c0f4" PRIMARY KEY ("id"))`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`DROP TABLE "feature_flag"`);
    }

}
