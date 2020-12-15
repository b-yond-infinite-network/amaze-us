import express, { Response, Request, NextFunction } from "express";
import helmet from 'helmet'
import cors from 'cors';

import * as Sentry from "@sentry/node";
import * as Tracing from '@sentry/tracing';

// Controllers
import PioneerRoutes from './controllers/pioneer.controller';
import AuthRoutes from './controllers/auth.controller';

export class App {

    app: express.Application = express();


    public getApp() {
        this.startSentry();

        // CORS
        this.app.use(cors());

        // Helmet Security
        this.app.use(helmet.contentSecurityPolicy());
        this.app.use(helmet.dnsPrefetchControl());
        this.app.use(helmet.expectCt());
        this.app.use(helmet.frameguard());
        this.app.use(helmet.hidePoweredBy());
        this.app.use(helmet.hsts());
        this.app.use(helmet.ieNoOpen());
        this.app.use(helmet.noSniff());
        this.app.use(helmet.permittedCrossDomainPolicies());
        this.app.use(helmet.referrerPolicy());
        this.app.use(helmet.xssFilter());

        // Sentry Logger
        this.app.use(Sentry.Handlers.requestHandler());
        this.app.use(Sentry.Handlers.tracingHandler());

        // Routes
        this.setRoutes();

        // Sentry Error Handler
        this.app.use(Sentry.Handlers.errorHandler());
        this.app.use((err: any, req: Request, res: Response, next: NextFunction) => {
            res.status(500).json({ success: false, error: 'Internal Server Error' });
        });
        return this.app;
    }

    private startSentry() {
        Sentry.init({
            dsn: "https://1b57b8e4b09541648f4dd068e4558f85@o490636.ingest.sentry.io/5554865",
            integrations: [
                new Sentry.Integrations.Http({ tracing: true }),
                new Tracing.Integrations.Express({
                    app: this.app,
                }),
            ],
            tracesSampleRate: 1.0,
        });
    }

    private setRoutes() {
        this.app.get('/health-check', (req, res) => res.status(200).json({ success: true, time: Date() }));
        this.app.use('/v1/pioneer', PioneerRoutes);
        this.app.use('/v1/auth', AuthRoutes);
    }

}