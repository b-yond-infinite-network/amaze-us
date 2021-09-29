import { Router } from 'express';
import { requirePermissions } from '../middlewares/authorization';
import { AppPermissions } from '../constants/permissions';
import CreateUserController from '../controllers/users/create-user';
import FetchUsersController from '../controllers/users/fetch-users';
import AssignRoleController from '../controllers/roles/assign-role';

import CreateBabyRequestController from '../controllers/baby-requests/create';
import ReviewBabyRequestController from '../controllers/baby-requests/review-request';
import FetchBabyRequestsController from '../controllers/baby-requests/fetch';

import FetchPlantsController from '../controllers/plants/fetch';
import FetchPlantSeedsController from '../controllers/plants/fetch-seeds';
import CreatePlantController from '../controllers/plants/create';
import HarvestPlantController from '../controllers/plants/harvest';

import FetchFoodProduceController from '../controllers/food-produce/fetch';

import FetchWaterCollectorsController from '../controllers/water-collectors/fetch';
import RefillWaterCollectorController from '../controllers/water-collectors/refill';

const router = Router();

router.get('/users', FetchUsersController);
router.post('/users', requirePermissions({
  requestedPermissions: [AppPermissions.CreateUsers],
}), CreateUserController)

router.post('/roles/:roleId/:userId', requirePermissions({
  requestedPermissions: [AppPermissions.AssignRole]
}), AssignRoleController);

router.get('/babyrequests', FetchBabyRequestsController);
router.post('/babyrequests', CreateBabyRequestController);
router.put('/babyrequests/:requestId', requirePermissions({
  requestedPermissions: [AppPermissions.ReviewBabyMakingRequest]
}), ReviewBabyRequestController);

router.get('/plants', FetchPlantsController);
router.post('/plants', requirePermissions({
  requestedPermissions: [AppPermissions.PlantSeeds]
}), CreatePlantController);
router.post('/harvests', HarvestPlantController);
router.get('/plants/seeds', FetchPlantSeedsController);

router.get('/foodproduce', FetchFoodProduceController);

router.get('/watercollectors', FetchWaterCollectorsController);
// this is to emulate updating the water level for a collector
// in a real-world scenario there would be a messaging queue
// where signals are sent by a sensor
router.post('/watercollectors/:waterCollectorId/refill', RefillWaterCollectorController);

export default router;