import { Request, Response, Router, NextFunction } from "express";
import artistController from "./artistController";
import { HTTP400Error } from "../../../utils/httpErrors";

const router = Router();

const artistAPIQueryValidator = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (!req.query.name || !req.query.pageSize || !req.query.page) {
    const error = new HTTP400Error({
      message: "Bad request. All queries must be present"
    });
    throw error;
  } else {
    next();
  }
};

router.use("/artist", artistAPIQueryValidator, artistController);
export default router;
