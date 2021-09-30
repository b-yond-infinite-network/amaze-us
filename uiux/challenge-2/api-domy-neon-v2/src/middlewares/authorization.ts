import { Response, Request, NextFunction } from 'express';
import { DbContext } from '../data-store/factories';

interface Config {
  requestedPermissions: string[];
  isRequiresAny?: boolean;
};

const hasAllPermissions = (requestedPermissions: string[], userPermissions: Set<string>) =>
  userPermissions.size > 0 &&
  !requestedPermissions.some(permission => !userPermissions.has(permission))

const hasAnyPermission = (requestedPermissions: string[], userPermissions: Set<string>) =>
  userPermissions.size > 0 &&
  requestedPermissions.some(permission => userPermissions.has(permission));

export function requirePermissions(config: Config) {
  return async (req: Request, res: Response, next: NextFunction) => {
    const user = (req.user as any);
    const { sub: userId } = user;

    const userRoleIds = (await DbContext.userRoles.find({
      userId
    })).map(r => r.roleId);

    // a good candidate for caching...
    const roles = await DbContext.roles.find({
      filters: [{
        field: 'id',
        type: 'in',
        value: userRoleIds
      }]
    });

    const { requestedPermissions } = config;
    const permissions = new Set(roles.flatMap(x => x.permissions));
    const isRequiresAny = config.isRequiresAny ?? false;

    const hasAccess = isRequiresAny ? hasAnyPermission(requestedPermissions, permissions) :
      hasAllPermissions(requestedPermissions, permissions);

    if (!hasAccess) {
      return res.status(403).json({ error: 'Unauthorized' });
    }

    return next();
  }
}
