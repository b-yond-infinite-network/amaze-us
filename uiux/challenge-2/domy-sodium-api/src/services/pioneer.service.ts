import { Pioneer, PioneerStatus } from '../entity/Pioneer.entity';
import { getConnection } from 'typeorm';
import { PioneerInterface } from '../interfaces/pioneer.interface';

export class PioneerService {

    async checkExistingUser(recognition_number: string, status: PioneerStatus) {
        const data = await getConnection().manager.getRepository(Pioneer).findOne({
            where: {
                recognition_number,
                status
            }
        });
        if (!data) { return false; }
        return true;
    }

    async addApprovedUser(user: PioneerInterface) {
        const pioneer = new Pioneer();
        pioneer.recognition_number = user.recognition_number;
        pioneer.created_at = Date();
        pioneer.updated_at = Date();
        pioneer.first_name = user.first_name;
        pioneer.last_name  = user.last_name;
        pioneer.birthdate  = user.birthdate;
        return await getConnection().manager.getRepository(Pioneer).save(pioneer);
    }
}

