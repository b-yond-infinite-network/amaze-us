package karaoke.api

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ServiceSongDb {

    @Inject
    private ServiceThirdPartyLyrics lyricsService

    DtoLyrics getLyrics(long trackId) {
        def track = lyricsService.getTrack(trackId)
        return lyricsService.getLyrics(track)
    }

    Collection<DtoTrack> findByArtistName(String artistName, int page, OrderBy orderBy, boolean ascending) {
        List<DtoTrack> allItems = new ArrayList<>(lyricsService.findByArtistName(artistName))
        if (orderBy) {
            def fieldName = orderBy.name()
            allItems = allItems.toSorted {
                a, b -> a[fieldName] <=> b[fieldName]
            }
        }
        if (!ascending) {
            allItems = allItems.reverse()
        }
        def pages = allItems.collate(10)
        if (pages.size() > page) {
            return pages.get(page - 1)
        }
        return []
    }

    enum OrderBy {
        artist,
        title,
        length
    }
}
