import { NextFunction, Request, Response } from "express";
import { UserDto } from "models/dto/user-dto";
import { DbContext } from "../../data-store/factories";

export default async (_: Request, res: Response, next: NextFunction) => {
  const users: UserDto[] = (await DbContext.users.find({})).map(({
    id,
    firstName,
    lastName,
    birthDate,
    occupation,
  }) => ({
    id,
    firstName,
    lastName,
    birthDate,
    occupation,
  }));

  return res.json(users);
}
