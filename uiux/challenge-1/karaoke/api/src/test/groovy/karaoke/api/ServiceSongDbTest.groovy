package karaoke.api

import karaoke.lyrics.Lyrics
import karaoke.lyrics.LyricsService
import karaoke.lyrics.Track
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.Test
import org.junit.runner.RunWith

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

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
        assertNotNull(result)
        assertEquals(lyrics.id, result.id)
        assertEquals(track.id, result.track.id)
    }

    @Test
    void 'should get tracks page'() {
        // by artist
        assertEquals(
                ['a011', 'a012', 'a013', 'a014', 'a015', 'a016', 'a017', 'a018', 'a019', 'a020'],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.artist, true).collect({ it.artist })
        )
        assertEquals(
                ['a093', 'a092', 'a091', 'a090', 'a089', 'a088', 'a087', 'a086', 'a085', 'a084'],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        assertEquals(
                ['a103', 'a102', 'a101', 'a100', 'a099', 'a098', 'a097', 'a096', 'a095', 'a094'],
                db.findByArtistName('a', 1, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        assertEquals(
                ['a003', 'a002', 'a001'],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        assertEquals(
                ['a101', 'a102', 'a103'],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.artist, true).collect({ it.artist })
        )
        assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.artist, false).collect({ it.artist })
        )
        assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.artist, true).collect({ it.artist })
        )

        // by title
        assertEquals(
                ['t011', 't012', 't013', 't014', 't015', 't016', 't017', 't018', 't019', 't020'],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.title, true).collect({ it.title })
        )
        assertEquals(
                ['t093', 't092', 't091', 't090', 't089', 't088', 't087', 't086', 't085', 't084'],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.title, false).collect({ it.title })
        )
        assertEquals(
                ['t103', 't102', 't101', 't100', 't099', 't098', 't097', 't096', 't095', 't094'],
                db.findByArtistName('a', 1, ServiceSongDb.OrderBy.title, false).collect({ it.title })
        )
        assertEquals(
                ['t003', 't002', 't001'],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.title, false).collect({ it.title })
        )
        assertEquals(
                ['t101', 't102', 't103'],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.title, true).collect({ it.title })
        )
        assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.title, false).collect({ it.title })
        )
        assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.title, true).collect({ it.title })
        )

        // by length
        assertEquals(
                [11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.length, true).collect({ it.length.intValue() })
        )
        assertEquals(
                [93, 92, 91, 90, 89, 88, 87, 86, 85, 84],
                db.findByArtistName('a', 2, ServiceSongDb.OrderBy.length, false).collect({ it.length.intValue() })
        )
        assertEquals(
                [103, 102, 101, 100, 99, 98, 97, 96, 95, 94],
                db.findByArtistName('a', 1, ServiceSongDb.OrderBy.length, false).collect({ it.length.intValue() })
        )
        assertEquals(
                [3, 2, 1],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.length, false).collect({ it.length.intValue() })
        )
        assertEquals(
                [101, 102, 103],
                db.findByArtistName('a', 11, ServiceSongDb.OrderBy.length, true).collect({ it.length.intValue() })
        )
        assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.length, false).collect({ it.length.intValue() })
        )
        assertEquals(
                [],
                db.findByArtistName('a', 12, ServiceSongDb.OrderBy.length, true).collect({ it.length.intValue() })
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
                            title: 't' + String.format("%03d", it),
                            artist: 'a' + String.format("%03d", it),
                            length: it
                    )
                })
                Collections.shuffle(myList)
                return myList
            }
            return []
        }
    }
}
