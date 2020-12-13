import { NextFunction, Request, Response, Router } from 'express';

import { Pioneer, PioneerStatus } from '../entity/Pioneer.entity';

// Middlewares
import { validateSession } from '../middlewares/session.middleware';
import { ValidateParams } from '../middlewares/params.middleware';
import { json } from 'body-parser';

// Services
import { PioneerService } from '../services/pioneer.service';
import { FeatureFlagService } from '../services/featureFlag.service';

// Interfaces
import {
    AddUserPreRegistration,
    CheckUserPreRegistration
} from '../interfaces/pioneer.params.interface';
import { PioneerInterface } from '../interfaces/pioneer.interface';

class PioonerRouter {
    public router: Router;
    private pioneerService = new PioneerService();
    private parse = json();
    private featureFlagService = new FeatureFlagService();

    constructor() {
        this.router = Router();
        this.init();
    }

    private init() {
        this.router.get('/health-check', (req: any, res: Response) => this.healthCheck(req, res));
        this.router.get('/user_pre_registration/:recognition_number', this.parse, ValidateParams(CheckUserPreRegistration), this.checkUserPreRegistration.bind(this));
        this.router.post('/add_user_pre_registration', validateSession, this.parse, ValidateParams(AddUserPreRegistration), this.addUserPreRegistration.bind(this));
    }

    private healthCheck(req: Request, res: Response) {
        return res.status(200).json({ success: true, date: Date() });
    }

    private async checkUserPreRegistration(req: Request, res: Response, next: NextFunction) {
        try {
            const status = PioneerStatus.APPROVED;
            const response = await this.pioneerService.checkExistingUser(req.params.recognition_number, status);
            if (!response) { return res.status(404).json({ success: false, message: 'Recognition Number not found' }); }
            return res.status(200).json({ success: true });
        } catch (error) {
            next(error);
        }
    }

    private async addUserPreRegistration(req: Request, res: Response, next: NextFunction) {
        try {
            const status = PioneerStatus.APPROVED;
            const user: PioneerInterface = { ...req.body, status };
            const response = await this.pioneerService.addApprovedUser(user);
            const basicFeature = { recognition_number: req.body.recognition_number, feature: 'basic' };
            await this.featureFlagService.addFeatures([basicFeature]);
            return res.status(200).json({ success: true, message: 'Pioneer information added', pioneer: response });
        } catch (error) {
            next(error);
        }
    }

}

const pioonerRouter = new PioonerRouter();

export default pioonerRouter.router;