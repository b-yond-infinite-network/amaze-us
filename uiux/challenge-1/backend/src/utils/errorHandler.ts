import { Request, Response, NextFunction } from "express";

import { HTTPClientError, HTTPServerError } from "./httpErrors";

export const handleClientError = (
  error: Error,
  res: Response,
  next: NextFunction
) => {
  if (error instanceof HTTPClientError) {
    res.status(error.statusCode).send(error.message);
  } else {
    next(error);
  }
};

export const handleServerError = (
  error: Error,
  res: Response,
  next: NextFunction
) => {
  if (error instanceof HTTPServerError) {
    res.status(error.statusCode).send(error.message);
  } else {
    next(error);
  }
};
