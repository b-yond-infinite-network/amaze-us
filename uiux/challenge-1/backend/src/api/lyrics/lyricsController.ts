import { Request, Response } from "express";
import axios from "axios";

const lyricsController = async (req: Request, res: Response) => {
  res.status(200).send("Successful");
};

export default lyricsController;
