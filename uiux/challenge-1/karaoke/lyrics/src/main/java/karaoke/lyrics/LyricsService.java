package karaoke.lyrics;

import java.util.Collection;

public interface LyricsService {

    Track getTrack(long trackId);

    Lyrics getLyrics(long trackId);

    Collection<Track> findByArtistName(String artistName);

}
