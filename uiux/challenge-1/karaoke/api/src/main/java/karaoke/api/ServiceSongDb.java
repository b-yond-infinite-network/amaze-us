package karaoke.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class ServiceSongDb {

    private int pageSize = Integer.getInteger("tracks_page_size", 30);

    @Inject
    private ServiceThirdPartyLyrics lyricsService;

    public DtoLyrics getLyrics(long trackId) {
        final DtoTrack track = lyricsService.getTrack(trackId);
        return lyricsService.getLyrics(track);
    }

    public Collection<DtoTrack> findByArtistName(String artistName, int page, OrderBy orderBy, boolean ascending) {
        List<DtoTrack> allItems = new ArrayList<>(lyricsService.findByArtistName(artistName));
        switch (orderBy) {
            case artist:
                allItems.sort(Comparator.comparing(DtoTrack::getArtist));
                break;
            case title:
                allItems.sort(Comparator.comparing(DtoTrack::getTitle));
                break;
            case length:
                allItems.sort(Comparator.comparing(DtoTrack::getLength));
                break;
            default:
                // no-op
        }
        if (!ascending) {
            Collections.reverse(allItems);
        }
        int index = page - 1;
        int start = index * pageSize;
        int end = Math.min(start + pageSize, allItems.size());
        try {
            return allItems.subList(start, end);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    public enum OrderBy {
        artist,
        title,
        length
    }
}
