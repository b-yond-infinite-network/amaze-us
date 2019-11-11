import costumAxios from './../../utils/axionsConfig';

export const fetchTracksList = artist_id => (
    costumAxios.get(`/track.search?q_artist=${artist_id}`)
);