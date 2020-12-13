import {MigrationInterface, QueryRunner} from "typeorm";

export class Pioneer1607871207101 implements MigrationInterface {
    name = 'Pioneer1607871207101'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE "pioneers" ("id" integer NOT NULL, "password" character varying NOT NULL, "recognition_number" character varying NOT NULL, "first_name" character varying NOT NULL, "last_name" character varying NOT NULL, "status" "pioneers_status_enum" NOT NULL DEFAULT 'pending', "birthdate" TIMESTAMP NOT NULL DEFAULT now(), "created_at" TIMESTAMP NOT NULL DEFAULT now(), "updated_at" TIMESTAMP NOT NULL DEFAULT now(), CONSTRAINT "PK_1337194559d873c99eee3c66ee8" PRIMARY KEY ("id"))`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`DROP TABLE "pioneers"`);
    }

}
