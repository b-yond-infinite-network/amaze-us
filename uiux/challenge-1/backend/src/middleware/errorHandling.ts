import { Request, Response, NextFunction, Router } from "express";
import * as ErrorHandler from "../utils/errorHandler";

const handleClientError = (router: Router) => {
  router.use((err: Error, req: Request, res: Response, next: NextFunction) => {
    ErrorHandler.handleClientError(err, res, next);
  });
};

const handleServerError = (router: Router) => {
  router.use((err: Error, req: Request, res: Response, next: NextFunction) => {
    ErrorHandler.handleServerError(err, res, next);
  });
};

export default [handleClientError, handleServerError];
