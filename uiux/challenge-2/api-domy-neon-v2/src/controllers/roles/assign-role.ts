import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import { UserRole } from '../../models/user-role';
import UserValidations from '../../validations/user';
import Logger from '../../providers/logger';
import logger from '../../providers/logger';

export default async (req: Request, res: Response, next: NextFunction) => {
  try {
    const {
      roleId,
      userId,
    } = req.params;

    logger.info(`userId: ${userId} \n roleId: ${roleId}`);

    const user = await DbContext.users.findOne({
      id: userId
    });

    if (!user) {
      return res.status(400).json({ error: 'Invalid user' });
    }

    const role = await DbContext.roles.findOne({
      id: roleId
    });

    if (!role) {
      return res.status(400).json({ error: 'Invalid role' });
    }
    
    const existingAssignement = await DbContext.userRoles.findOne({
      userId,
      roleId,
    });

    if (existingAssignement) {
      return res.status(400).json({ errors: [{ msg: 'This is user is already assigned to the given role.' }] });
    }

    await DbContext.userRoles.insert(new UserRole(userId, roleId));
    return res.json({
      message: 'Role assigned successfully.'
    });
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'Role assignement failure.',
      error
    });
    next(error);
  }
}