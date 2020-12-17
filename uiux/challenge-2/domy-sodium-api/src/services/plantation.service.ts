import { getConnection, LessThanOrEqual } from 'typeorm';
import { Plantation, PlantationStatus } from '../entity/Plantation.entity';

export class PlantationService {

    async getAllActivePlantations() {
        const today = new Date().toUTCString();
        return await getConnection().manager.getRepository(Plantation).find({
            where: {
                plantation_start_date: LessThanOrEqual(today),
                status: PlantationStatus.ACTIVE
            }
        });
    }

    async getOnDatePlantation() {
        return await getConnection().manager.getRepository(Plantation).find({
            where: {
                plantation_start_date: new Date().toUTCString()
            }
        });
    }

    async addPlantation(plantation: Plantation) {
        return await getConnection().manager.getRepository(Plantation).save(plantation);
    }
}