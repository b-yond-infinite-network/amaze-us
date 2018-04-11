package karaoke.api

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces

@Path('/lyrics')
@Produces('application/json')
class ApiLyrics {

    @Inject
    private ServiceSongDb db

    @GET
    @Path('/{track_id}')
    DtoLyrics getLyrics(@PathParam('track_id') Long trackId) {
        return db.getLyrics(trackId)
    }

}
