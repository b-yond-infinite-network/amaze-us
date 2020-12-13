import { NextFunction, Request, Response, Router } from 'express';

// Middlewares
import { ValidateParams } from '../middlewares/params.middleware';
import { json } from 'body-parser';

// Services
import { PioneerService } from '../services/pioneer.service';
import { FeatureFlagService } from '../services/featureFlag.service';
import { JWT } from '../helpers/jwt.helper';
import { EncryptHelper } from '../helpers/encrypt.helper';

// Interfaces
import { LoginParams, RegisterParams } from '../interfaces/auth.params.interface';
import { PioneerStatus } from '../entity/Pioneer.entity';

class PioonerRouter {
    public router: Router;
    private parse = json();
    private pioneerService = new PioneerService();
    private featureFlagService = new FeatureFlagService();
    private encryptHelper = new EncryptHelper();
    private jwt = new JWT();

    constructor() {
        this.router = Router();
        this.init();
    }

    private init() {
        this.router.get('/health-check', this.parse, (req: any, res: Response) => this.healthCheck(req, res));
        this.router.post('/register', this.parse, ValidateParams(RegisterParams), this.registerPreApprovedUser.bind(this));
        this.router.post('/authenticate', this.parse, ValidateParams(LoginParams), this.authenticate.bind(this));
    }

    private healthCheck(req: Request, res: Response) {
        return res.status(200).json({ success: true, date: Date() });
    }

    private async authenticate(req: Request, res: Response, next: NextFunction) {
        try {
            const password = await this.encryptHelper.encryptPassword(req.body.password);
            const user = await this.pioneerService.getUser(req.body.recognition_number, password);
            if (!user) { return res.status(404).json({ success: false, message: 'Invalid Credentials' }); }

            const features = await this.featureFlagService.getUserFeatures(req.body.recognition_number);
            const token = await this.jwt.generateToken({ features, recognition_number: req.body.recognition_number });
            return res.status(200).json({
                user,
                success: true,
                metadata: { token, date: Date() },
            });
        } catch (error) {
            next(error);
        }
    }

    private async registerPreApprovedUser(req: Request, res: Response, next: NextFunction) {
        try {
            const status = PioneerStatus.APPROVED;
            const response = await this.pioneerService.checkExistingUser(req.body.recognition_number, status);
            if (!response) { return res.status(404).json({ success: false, message: 'Recognition Number not found' }); }

            const password = await this.encryptHelper.encryptPassword(req.body.password);
            const user = { password, recognition_number: req.body.recognition_number, status: PioneerStatus.ACTIVE };
            const data = await this.pioneerService.updateUser(user);
            if (!data) { res.status(200).json({ success: true }); }

            const features = await this.featureFlagService.getUserFeatures(req.body.recognition_number);
            const token = await this.jwt.generateToken({ features, recognition_number: req.body.recognition_number });
            return res.status(200).json({
                success: true,
                metadata: { token, date: Date() },
                user: data
            });
        } catch (error) {
            next(error);
        }
    }

}

const pioonerRouter = new PioonerRouter();

export default pioonerRouter.router;