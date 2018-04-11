package karaoke.api

import karaoke.lyrics.Lyrics
import karaoke.lyrics.LyricsService
import karaoke.lyrics.Track
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@RunWith(Arquillian)
class ServiceSongDbTest {

    private static Lyrics lyrics = new Lyrics(
            id: 101
    )

    private static Track track = new Track(
            id: 100
    )

    @Inject
    ServiceSongDb db

    @Deployment
    static WebArchive war() { // use test name for the war otherwise arquillian ejb enricher doesn't work
        return ShrinkWrap.create(WebArchive, "test.war").addClasses(
                ServiceSongDb,
                ServiceThirdPartyLyrics,
                DtoLyrics,
                DtoTrack,
                LyricsService,
                DymmyLyricsService,
        )
    }

    @Test
    void 'should get lyrics'() {
        def result = db.getLyrics(100)
        Assert.assertNotNull(result)
        Assert.assertEquals(lyrics.id, result.id)
    }

    @ApplicationScoped
    static class DymmyLyricsService implements LyricsService {

        @Override
        Track getTrack(long trackId) {
            if (trackId == 100) {
                return track
            }
            return null
        }

        @Override
        Lyrics getLyrics(long trackId) {
            if (trackId == 100) {
                return lyrics
            }
            return null
        }

        @Override
        Collection<Track> findByArtistName(String artistName) {
            return null
        }
    }
}
