import express from 'express';
import TrackController from './track'
export default express.Router()
    .get('/', TrackController.search)
