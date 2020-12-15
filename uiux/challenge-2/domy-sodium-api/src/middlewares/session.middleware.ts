import { Request, Response, NextFunction } from 'express';
import { verify, VerifyOptions } from 'jsonwebtoken';

export const validateSession = (req: Request, res: Response, next: NextFunction) => {
    try {
        const privateKEY = process.env.TOKEN_SECURE_KEY;
        const authHeader = req.headers.authorization;
        const token = authHeader && authHeader.split(' ')[1]
        if (!token) return res.sendStatus(401);
        const tokenOpts: VerifyOptions = {
            issuer: 'DOMYSODIUM',
            subject: 'rocamanuelignacio@gmail.com',
            audience: 'http://github.com/rocamanuelignacio',
            algorithms: ["RS256"]
        };
        verify(token, privateKEY, tokenOpts, (err: any, user: any) => {
            if (err) return res.sendStatus(403);
            req.recognition_number = user.recognition_number;
            req.features = user.features;
            next()
        })
    } catch (e) {
        return res.sendStatus(500);
    }
}

export const validateAccessLevel = (feature: string) => {
    return function (req: Request, res: Response, next: NextFunction) {
        try {
            req.features.forEach((item) => {
                if (item.feature === feature) {
                    return next();
                }
            });
            return res.status(403).json({ user_message: 'Forbidden' });

        } catch (e) {
            return res.sendStatus(500);
        }
    }
}
