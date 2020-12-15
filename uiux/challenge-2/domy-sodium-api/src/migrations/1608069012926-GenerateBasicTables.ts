import {MigrationInterface, QueryRunner} from "typeorm";

export class GenerateBasicTables1608069012926 implements MigrationInterface {
    name = 'GenerateBasicTables1608069012926'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE "collectors" ("id" SERIAL NOT NULL, "input" integer NOT NULL, "output" integer NOT NULL, "tool_name" character varying NOT NULL, "updated_at" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "PK_da4185226ea730100d5aa647afe" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "feature_flag" ("id" SERIAL NOT NULL, "feature" character varying NOT NULL, "recognition_number" character varying NOT NULL, "created_at" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "PK_f390205410d884907604a90c0f4" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "pioneers" ("id" SERIAL NOT NULL, "password" character varying NOT NULL, "recognition_number" character varying NOT NULL, "first_name" character varying NOT NULL, "last_name" character varying NOT NULL, "status" "pioneers_status_enum" NOT NULL DEFAULT 'pending', "birthdate" TIMESTAMP NOT NULL DEFAULT now(), "created_at" TIMESTAMP NOT NULL DEFAULT now(), "updated_at" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "UQ_c55c4f4cf8b98d7918ef52e4ad6" UNIQUE ("recognition_number"), CONSTRAINT "PK_1337194559d873c99eee3c66ee8" PRIMARY KEY ("id"))`);
        await queryRunner.query(`CREATE TABLE "plantation" ("id" SERIAL NOT NULL, "seed_id" character varying NOT NULL, "seed_name" character varying NOT NULL, "plantation_percent" integer NOT NULL, "plantation_start_date" TIMESTAMP NOT NULL DEFAULT now(), "plantation_ready_date" TIMESTAMP NOT NULL DEFAULT now(), "updated_at" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "PK_636bd32e7158bd559f4440e9af7" PRIMARY KEY ("id"))`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`DROP TABLE "plantation"`);
        await queryRunner.query(`DROP TABLE "pioneers"`);
        await queryRunner.query(`DROP TABLE "feature_flag"`);
        await queryRunner.query(`DROP TABLE "collectors"`);
    }

}
