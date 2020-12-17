import { NextFunction, Request, Response, Router } from 'express';
import moment from 'moment';

// Middlewares
import { validateSession, validateAccessLevel } from '../middlewares/session.middleware';
import { ValidateParams } from '../middlewares/params.middleware';
import { json } from 'body-parser';
import { AddPlantation } from '../interfaces/farms.params.interface';

// Services
import { PlantationService } from '../services/plantation.service';

// Interfaces
import { Plantation, PlantationStatus } from '../entity/Plantation.entity';

class CollectorRouter {
    public router: Router

    private plantationService = new PlantationService();
    private parse = json({
    });

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
        this.router.post(
            '/add_plantation',
            validateSession,
            ValidateParams(AddPlantation),
            this.addPlantation.bind(this)
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
            const farm_plantation = await this.plantationService.getAllActivePlantations();
            const available_percent = this.getAvailablePercent(farm_plantation);
            if (req.body.amount > available_percent) {
                return res.status(403).json({ success: false, message: 'There is no enough space for that plantation, try with less amount' });
            }
            
            const today = moment().utc();
            const plantation_ready_date = moment().utc().add(req.body.readyOnDays, 'days').toString();
            const plantation = new Plantation();
            plantation.plantation_percent = req.body.amount;
            plantation.plantation_ready_date = plantation_ready_date;
            plantation.plantation_start_date = today.toString();
            plantation.seed_id = req.body.seedId;
            plantation.seed_name = req.body.seedName;
            plantation.updated_at = today.toString();
            plantation.status = PlantationStatus.ACTIVE;

            this.plantationService.addPlantation(plantation);
            return res.status(200).json({ success: true, plantation: plantation });
        } catch (e) {
            next(e);
        }
    }

    private getAvailablePercent(plantations: any[]) {
        let usedPercent = 0;
        plantations.forEach(plantation => {
            usedPercent = usedPercent + plantation.plantation_percent;
        });
        return 100 - usedPercent;
    }
}


const pioonerRouter = new CollectorRouter();

export default pioonerRouter.router;