import express from 'express';
import LyricsController from './lyrics'
export default express.Router()
    .get('/:id', LyricsController.getById);