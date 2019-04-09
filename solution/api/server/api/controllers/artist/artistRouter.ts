import express from 'express';
import ArtistController from './artist'
export default express.Router()
    .get('/:id', ArtistController.getById)
    .get('/', ArtistController.search);