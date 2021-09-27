import { Router } from 'express';
import { DbContext } from '../data-store/factories';

const router = Router();

router.get('/users', async (req, res) => {
  return res.json(await DbContext.users.find({}));
});

export default router;