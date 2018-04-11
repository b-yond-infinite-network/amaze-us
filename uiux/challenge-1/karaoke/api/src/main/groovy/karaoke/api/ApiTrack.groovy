package karaoke.api

import javax.inject.Inject
import javax.ws.rs.*

@Path('/track')
@Produces('application/json')
class ApiTrack {

    @Inject
    private ServiceSongDb db

    @GET
    @Path('/{artist_name}')
    Collection<DtoTrack> getSongs(
            @PathParam('artist_name') String artist,
            @QueryParam('page') Integer page,
            @QueryParam('order_by') ServiceSongDb.OrderBy orderBy,
            @QueryParam('ascending') @DefaultValue("true") boolean ascending
    ) {
        return db.findByArtistName(artist, page, orderBy, ascending)
    }
}
