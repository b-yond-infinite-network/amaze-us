import { getConnection } from 'typeorm';
import { Collectors } from '../entity/Collectors.entity';
import { TOOLS_KEY } from '../configs/tools.config';
import moment from 'moment';

export class CollectorService {

    async getFoodCollector() {
        return await getConnection().manager.getRepository(Collectors).findOne({
            where: {
                tool_name: TOOLS_KEY.food
            }
        });
    }

    async getWaterCollector() {
        return await getConnection().manager.getRepository(Collectors).findOne({
            where: {
                tool_name: TOOLS_KEY.water
            }
        });
    }
    
    async createFoodCollector() {
        const collector = new Collectors();
        collector.input = 12;
        collector.output = 10;
        collector.tool_name = TOOLS_KEY.food;
        collector.updated_at = moment().utc().toString();
        collector.available_capacity = 60;
        return await getConnection().manager.getRepository(Collectors).save(collector);
    }

    async createWaterCollector() {
        const collector = new Collectors();
        collector.input = 30;
        collector.output = 23;
        collector.tool_name = TOOLS_KEY.water;
        collector.updated_at = moment().utc().toString();
        collector.available_capacity = 45;
        return await getConnection().manager.getRepository(Collectors).save(collector);
    }
}