import { NextFunction, Request, Response, Router } from 'express';

import { Pioneer, PioneerStatus } from '../entity/Pioneer.entity';

// Middlewares
import { validateSession } from '../utils/session.middleware';
import { ValidateParams } from '../utils/params.middleware';

// Services
import { PioneerService } from '../services/pioneer.service';

// Interfaces
import {
    AddUserPreRegistration,
    CheckUserPreRegistration
} from '../interfaces/pioneer.params.interface';
import { PioneerInterface } from '../interfaces/pioneer.interface';
import { nextTick } from 'process';

class PioonerRouter {
    public router: Router;
    private pioneerService = new PioneerService();

    constructor() {
        this.router = Router();
        this.init();
    }

    private init() {
        this.router.get('/health-check', (req: any, res: Response) => this.healthCheck(req, res));
        this.router.get('/user_pre_registration/:recognition_number', ValidateParams(CheckUserPreRegistration), (req: any, res: Response, next: NextFunction) => this.checkUserPreRegistration(req, res, next));
        this.router.post('/add_user_pre_registration', validateSession, ValidateParams(AddUserPreRegistration), (req: any, res: Response, next: NextFunction) => this.addUserPreRegistration(req, res, next));
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
        } catch (e) {
            const error = new Error();
            next(error);
        }
    }

    private async addUserPreRegistration(req: Request, res: Response, next: NextFunction) {
        try {
            const status = PioneerStatus.APPROVED;
            const user: PioneerInterface = { ...req.body, status };
            const response = await this.pioneerService.addApprovedUser(user);
            console.log(response);
            return res.status(200).json({ success: true, message: 'Pioneer information added', pioneer: response });
        } catch (e) {
            const error = new Error();
            next(error);
        }
    }

}

const pioonerRouter = new PioonerRouter();

export default pioonerRouter.router;