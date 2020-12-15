import { getConnection, LessThan, MoreThan } from 'typeorm';
import { Plantation } from '../entity/Plantation.entity';

export class PlantationService {

    async getAllActivePlantations() {
        return await getConnection().manager.getRepository(Plantation).find({
            where: {
                plantation_start_date: MoreThan(Date().toString()),
                plantation_ready_date: LessThan(Date().toString())
            }
        });
    }

    async getOnDatePlantation() {
        return await getConnection().manager.getRepository(Plantation).find({
            where: {
                plantation_start_date: Date().toString()
            }
        });
    }

    async addPlantation(plantation: Plantation) {
        return await getConnection().manager.getRepository(Plantation).save(plantation);
    }
}