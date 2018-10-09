import * as fetchJsonp from 'fetch-jsonp';

// TODO: Environment config
const endpoint = 'http://api.musixmatch.com/ws/1.1';

const APIKey = 'f07f8550f90c9002961792c4cb4844e3';

const processResults = results => {
    debugger;
};

export async function searchArtist(glob) {
    const params = `apikey=${APIKey}&q_artist=${glob}&format=jsonp&callback=jsonp_callback`;
    
    return fetchJsonp(`${endpoint}/artist.search?${params}`, { jsonpCallbackFunction: 'processResults' })
        .then(response => {
            if (response.ok)
                return response.json();

            throw new Error('error...');
        })
        .catch(e => {
            console.log('e: ', e);
        });
}
