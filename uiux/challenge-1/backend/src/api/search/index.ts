import { Router } from "express";
import artistRoutes from "./artist";
import trackRoutes from "./track";

const router = Router();

router.use("/search/", artistRoutes);
router.use("/search/", trackRoutes);

export default router;
