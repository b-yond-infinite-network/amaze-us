import { NextFunction, Request, Response, Router } from 'express';

import { Pioneer, PioneerStatus } from '../entity/Pioneer.entity';

// Middlewares
import { validateSession, validateAccessLevel } from '../middlewares/session.middleware';
import { ValidateParams } from '../middlewares/params.middleware';
import { json } from 'body-parser';

// Services
import { PioneerService } from '../services/pioneer.service';
import { FeatureFlagService } from '../services/featureFlag.service';
import { CollectorService } from '../services/collectors.service';

// Interfaces


class CollectorRouter {
    public router: Router

    private collectorService = new CollectorService();
    private parse = json();

    constructor() {
        this.router = Router();
        this.init();
    }

    private init() {
        this.router.get('/health-check', (req: any, res: Response) => this.healthCheck(req, res));
        this.router.get(
            '/water_level',
            validateSession,
            this.parse,
            this.shipWaterStorage.bind(this)
        );
        this.router.get(
            '/food_level',
            validateSession,
            this.parse,
            this.shipFoodStorage.bind(this)
        );
        this.router.post('/add_collectors', this.addCollectors.bind(this));
    }

    private healthCheck(req: Request, res: Response) {
        return res.status(200).json({ success: true, date: Date() });
    }

    private async shipFoodStorage(req: Request, res: Response, next: NextFunction) {
        try {
            const food_sotrage = await this.collectorService.getFoodCollector();
            return res.status(200).json({ success: true, sotrage: food_sotrage });
        } catch(e) {
            next(e);
        }
    }

    private async shipWaterStorage(req: Request, res: Response, next: NextFunction) {
        try {
            const water_sotrage = await this.collectorService.getWaterCollector();
            return res.status(200).json({ success: true, sotrage: water_sotrage });
        } catch(e) {
            next(e);
        }
    }

    private async addCollectors(req: Request, res: Response, next: NextFunction) {
        try {
            await this.collectorService.createFoodCollector();
            await this.collectorService.createWaterCollector();
            return res.status(200).json({ success: true })    
        } catch(e) {
            next(e);
        }
    }

}

const pioonerRouter = new CollectorRouter();

export default pioonerRouter.router;