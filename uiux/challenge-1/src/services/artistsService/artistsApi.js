import costumAxios from './../../utils/axionsConfig';

export const fetchArtistsList = keyword => {
    return costumAxios.get(`/artist.search?q_artist=${keyword}`)
};
