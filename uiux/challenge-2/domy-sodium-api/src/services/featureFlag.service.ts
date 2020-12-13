import { Pioneer, PioneerStatus } from '../entity/Pioneer.entity';
import { getConnection } from 'typeorm';
import { FeatureFlag } from '../entity/FeatureFlag.entity';
import { Feature } from '../interfaces/features.interface';

export class FeatureFlagService {

    async getUserFeatures(recognition_number: string) {
        return await getConnection().manager.getRepository(FeatureFlag).find({
            where: {
                recognition_number
            }
        });
    }

    async addFeatures(features: Feature[]) {
        return await new Promise((resolve, reject) => {
            features.forEach(async (feature) => {
                try {
                    feature.created_at = Date();
                    await getConnection().manager.getRepository(FeatureFlag).save(feature);
                } catch (e) {
                    reject(e);
                }
            });
            resolve({ success: true });
        });
    }

}