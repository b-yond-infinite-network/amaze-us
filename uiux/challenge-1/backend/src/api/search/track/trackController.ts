import { Request, Response } from "express";

const trackController = (req: Request, res: Response) => {
  res.status(200).send("Successful from trackController");
};

export default trackController;
