import { Router } from "express";
import lyricsController from "./lyricsController";

const router = Router();

router.use("/lyrics/:id", lyricsController);

export default router;
