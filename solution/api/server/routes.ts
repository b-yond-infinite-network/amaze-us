import { Application } from 'express';
import trackRouter from './api/controllers/track/trackRouter'
import lyricsRouter from './api/controllers/lyrics/lyricsRouter'
import artistRouter from './api/controllers/artist/artistRouter'

export default function routes(app: Application): void {
  app.use('/api/v1/search', trackRouter);
  app.use('/api/v1/lyrics', lyricsRouter);
  app.use('/api/v1/artist', artistRouter);

};