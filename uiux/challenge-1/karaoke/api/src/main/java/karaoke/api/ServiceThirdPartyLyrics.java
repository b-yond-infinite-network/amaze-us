package karaoke.api;

import karaoke.cache.Cached;
import karaoke.lyrics.Lyrics;
import karaoke.lyrics.LyricsService;
import karaoke.lyrics.Track;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServiceThirdPartyLyrics {

    @Inject
    private LyricsService lyricsService;

    @Cached
    public DtoTrack getTrack(long trackId) {
        final Track track = lyricsService.getTrack(trackId);
        final DtoTrack result = new DtoTrack();
        result.setId(track.getId());
        result.setArtist(track.getArtist());
        result.setTitle(track.getTitle());
        result.setLength(track.getLength());
        return result;
    }

    @Cached
    public DtoLyrics getLyrics(DtoTrack track) {
        final Lyrics lyrics = lyricsService.getLyrics(track.getId());
        final DtoLyrics result = new DtoLyrics();
        result.setId(lyrics.getId());
        result.setText(lyrics.getText());
        result.setTrack(track);
        return result;
    }

    @Cached
    public Collection<DtoTrack> findByArtistName(String artistName) {
        return lyricsService.findByArtistName(artistName).stream().map(track -> {
            final DtoTrack dto = new DtoTrack();
            dto.setId(track.getId());
            dto.setTitle(track.getTitle());
            dto.setLength(track.getLength());
            dto.setArtist(track.getArtist());
            return dto;
        }).collect(Collectors.toList());
    }
}
