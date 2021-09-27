import { Router } from 'express';
import SignupController from '../controllers/auth/signup';

const router = Router();

router.post('/auth/signup', SignupController);

export default router;