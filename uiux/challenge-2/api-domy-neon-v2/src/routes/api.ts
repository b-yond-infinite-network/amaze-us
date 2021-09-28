import { Router } from 'express';
import { requirePermissions } from '../middlewares/authorization';
import { DbContext } from '../data-store/factories';
import { AppPermissions } from '../constants/permissions';
import CreateUserController from '../controllers/users/create-user';
import FetchUsersController from '../controllers/users/fetch-users';

const router = Router();

router.get('/users', FetchUsersController);

router.post('/users', requirePermissions({
  requestedPermissions: [AppPermissions.CreateUsers],
}), CreateUserController)

export default router;