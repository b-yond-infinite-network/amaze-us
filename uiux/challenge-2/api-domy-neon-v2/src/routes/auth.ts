import { Router } from 'express';
import LoginController from '../controllers/auth/login';

const router = Router();

router.post('/login', LoginController);

export default router;