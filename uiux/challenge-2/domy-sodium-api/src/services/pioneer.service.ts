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
        pioneer.password   = Math.random().toString()
        pioneer.status     = PioneerStatus.APPROVED;
        return await getConnection().manager.getRepository(Pioneer).save(pioneer);
    }

    async getUser(recognition_number: string, password_hash: string) {
        return await getConnection().manager.getRepository(Pioneer).findOne({
            where: {
                recognition_number,
                password: password_hash
            }
        });
    }

    async updateUser(user: PioneerInterface) {
        return await getConnection()
        .createQueryBuilder()
        .update(Pioneer)
        .set(user)
        .where('recognition_number = :recognition_number', { recognition_number: user.recognition_number })
        .execute();
    }
}

