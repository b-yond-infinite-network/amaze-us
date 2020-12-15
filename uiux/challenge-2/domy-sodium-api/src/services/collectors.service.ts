import { getConnection } from 'typeorm';
import { Collectors } from '../entity/Collectors.entity';
import { TOOLS_KEY } from '../configs/tools.config';

export class CollectorService {

    async getFoodCollector() {
        return await getConnection().manager.getRepository(Collectors).find({
            where: {
                tool_name: TOOLS_KEY.food
            }
        });
    }

    async getWaterCollector() {
        return await getConnection().manager.getRepository(Collectors).find({
            where: {
                tool_name: TOOLS_KEY.water
            }
        });
    }

}