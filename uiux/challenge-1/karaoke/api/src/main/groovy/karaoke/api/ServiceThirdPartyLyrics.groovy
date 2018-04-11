package karaoke.api

import karaoke.cache.Cached
import karaoke.lyrics.LyricsService

import javax.ejb.Lock
import javax.ejb.LockType
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Singleton
@Lock(LockType.READ)
@Startup
class ServiceThirdPartyLyrics {

    @Inject
    private LyricsService lyricsService

    @Cached
    DtoTrack getTrack(long trackId) {
        def track = lyricsService.getTrack(trackId)
        return new DtoTrack(
                id: track.id,
                title: track.title,
                artist: track.artist,
                length: track.length
        )
    }

    @Cached
    DtoLyrics getLyrics(DtoTrack track) {
        def lyrics = lyricsService.getLyrics(track.id)
        return new DtoLyrics(
                id: lyrics.id,
                text: lyrics.text,
                track: track
        )
    }

    @Cached
    Collection<DtoTrack> findByArtistName(String artistName) {
        return lyricsService.findByArtistName(artistName)
    }
}
