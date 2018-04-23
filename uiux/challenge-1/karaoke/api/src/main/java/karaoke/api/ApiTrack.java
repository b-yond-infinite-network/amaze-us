package karaoke.api;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Collection;

@Path("/track")
@Produces("application/json")
public class ApiTrack {

    @Inject
    private ServiceSongDb db;

    @GET
    @Path("/{artist_name}")
    public Collection<DtoTrack> getSongs(
            @PathParam("artist_name") String artist,
            @QueryParam("page") Integer page,
            @QueryParam("order_by") ServiceSongDb.OrderBy orderBy,
            @QueryParam("ascending") @DefaultValue("true") boolean ascending
    ) {
        return db.findByArtistName(artist, page, orderBy, ascending);
    }
}
