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

    @Test
    void 'should get tracks page'() {
        Assert.assertEquals(
                ['a011', 'a012', 'a013', 'a014', 'a015', 'a016', 'a017', 'a018', 'a019', 'a020'],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.artist, true).collect({ it.artist })
        )
        Assert.assertEquals(
                ['a093', 'a092', 'a091', 'a090', 'a089', 'a088', 'a087', 'a086', 'a085', 'a084'],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        Assert.assertEquals(
                ['a103', 'a102', 'a101', 'a100', 'a099', 'a098', 'a097', 'a096', 'a095', 'a094'],
                db.findByArtistName('a', 1, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        Assert.assertEquals(
                ['a003', 'a002', 'a001'],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        Assert.assertEquals(
                ['a101', 'a102', 'a103'],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.artist, true).collect({ it.artist })
        )
        Assert.assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        Assert.assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.artist, true).collect({ it.artist })
        )
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
            if (artistName == 'a') {
                def myList = (1..103).collect({
                    return new Track(
                            id: it,
                            title: '',
                            artist: 'a' + String.format("%03d", it),
                            length: 0
                    )
                })
                Collections.shuffle(myList)
                return myList
            }
            return []
        }
    }
}
