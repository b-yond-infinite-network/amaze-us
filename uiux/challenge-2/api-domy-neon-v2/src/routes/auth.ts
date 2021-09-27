import { Router } from 'express';

import SignupController from '../controllers/auth/signup';
import LoginController from '../controllers/auth/login';

const router = Router();

router.post('/signup', SignupController);
router.post('/login', LoginController);

export default router;