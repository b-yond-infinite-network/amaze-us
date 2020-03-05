import { Router, Request, Response, NextFunction } from "express";
import trackController from "./trackController";
import { HTTP400Error } from "../../../utils/httpErrors";

const router = Router();

const trackAPIQueryParameter = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (!req.query.lyricsRequired || !req.query.page || !req.query.pageSize) {
    const error = new HTTP400Error({
      message: "Bad request. All queries must be present"
    });
    throw error;
  } else {
    next();
  }
};

router.use("/track/", trackAPIQueryParameter, trackController);

export default router;
