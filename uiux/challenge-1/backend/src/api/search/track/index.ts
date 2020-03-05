import { Router } from "express";
import trackController from "./trackController";

const router = Router();

router.use("/track/", trackController);

export default router;
