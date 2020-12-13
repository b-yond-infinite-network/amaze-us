import { Request, Response, NextFunction } from 'express';
import { ExtensionBoundSchema } from 'joi';

export const ValidateParams = (params: ExtensionBoundSchema) => {
    return function (request: Request, response: Response, next: NextFunction) {
        const req = getRequestVerifyItem(request);
        const { value, error } = params.validate(req);
        if (error) {
            console.log(error);
            return response.status(500).json({ error, user_message: 'params_error' });
        }
        next();
    }
}

const getRequestVerifyItem = (request: Request) => {
    let r = {};
    if (request.query)  { r = { ...r, ...request.query  }}
    if (request.params) { r = { ...r, ...request.params }}
    if (request.body)   { r = { ...r, ...request.body   }}
    return r;
}
