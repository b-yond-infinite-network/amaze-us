import costumAxios from './../../utils/axionsConfig';

export const fetchLyrics = track_id => (
    costumAxios.get(`track.lyrics.get?track_id=${track_id}`)
);