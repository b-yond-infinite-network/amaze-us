import { NextFunction, Request, Response, Router } from 'express';

// Middlewares
import { validateSession, validateAccessLevel } from '../middlewares/session.middleware';
import { ValidateParams } from '../middlewares/params.middleware';
import { json } from 'body-parser';
import { PlantationService } from '../services/plantation.service';

// Services

// Interfaces


class CollectorRouter {
    public router: Router

    private plantationService = new PlantationService();
    private parse = json();

    constructor() {
        this.router = Router();
        this.init();
    }

    private init() {
        this.router.get('/health-check', (req: any, res: Response) => this.healthCheck(req, res));
        this.router.get(
            '/farm_active_plantation',
            validateSession,
            this.parse,
            this.shipFarmActivePlantation.bind(this)
        );
        this.router.get(
            '/today_harvest',
            validateSession,
            this.parse,
            this.todayHarvest.bind(this)
        );
    }

    private healthCheck(req: Request, res: Response) {
        return res.status(200).json({ success: true, date: Date() });
    }

    private async shipFarmActivePlantation(req: Request, res: Response, next: NextFunction) {
        try {
            const farm_plantation = await this.plantationService.getAllActivePlantations();
            return res.status(200).json({ success: true, plantation: farm_plantation });
        } catch (e) {
            next(e);
        }
    }

    private async todayHarvest(req: Request, res: Response, next: NextFunction) {
        try {
            const farm_plantation = await this.plantationService.getOnDatePlantation();
            return res.status(200).json({ success: true, plantation: farm_plantation });
        } catch (e) {
            next(e);
        }
    }

    private async addPlantation(req: Request, res: Response, next: NextFunction) {
        try {
            const today = new Date();
            const plantation_ready_date = today.setDate(today.getDate() + req.body.readyOnDays );
            const plantation = {
                plantation_ready_date,
                seed_number: req.body.seedNumber,
                seed_name: req.body.seedName,
                plantation_start_date: Date(),
                plantation_percent: req.body.ammount
            }
            const farm_plantation = await this.plantationService.getOnDatePlantation();
            return res.status(200).json({ success: true, plantation: farm_plantation });
        } catch (e) {
            next(e);
        }
    }
}

const pioonerRouter = new CollectorRouter();

export default pioonerRouter.router;