package karaoke.musixmatch

import groovy.json.JsonSlurper
import karaoke.lyrics.Lyrics
import karaoke.lyrics.LyricsService
import karaoke.lyrics.Track

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.logging.Level
import java.util.logging.Logger

@ApplicationScoped
class MusixmatchLyricsServiceImpl implements LyricsService {

    private Logger logger = Logger.getLogger(MusixmatchLyricsServiceImpl.class.name)

    private WebTarget target

    @PostConstruct
    void init() {
        def props = new Properties()
        this.class.getResourceAsStream('/system.properties').with {
            if (it) {
                props.load(it)
            }
        }
        def musixmatchApi = props.getProperty('musixmatch_api', 'http://api.musixmatch.com/ws/1.1/')

        def musixmatchApiKey = System.getProperty('musixmatch_api_key')
        if (!musixmatchApiKey?.trim()) {
            musixmatchApiKey = System.getenv('musixmatch_api_key')
        }
        if (!musixmatchApiKey?.trim()) {
            musixmatchApiKey = props.get('musixmatch_api_key')
        }
        if (!musixmatchApiKey?.trim()) {
            throw new ApplicationException('Missing "musixmatch_api_key" property.')
        }
        this.target = ClientBuilder.newClient().target(musixmatchApi).queryParam('apikey', musixmatchApiKey)
    }

    @Override
    Track getTrack(long trackId) {
        def json = new JsonSlurper().parseText(
                target.path('track.get')
                        .queryParam('track_id', trackId)
                        .request().get(String)
        )
        def track = json.message.body.track
        return new Track(
                id: track.track_id,
                title: track.track_name,
                artist: track.artist_name,
                length: track.track_length
        )
    }

    @Override
    Lyrics getLyrics(long trackId) {
        def json = new JsonSlurper().parseText(
                target.path('track.lyrics.get')
                        .queryParam('track_id', trackId)
                        .request().get(String)
        )
        def lyrics = json.message.body.lyrics
        return new Lyrics(
                id: lyrics.lyrics_id,
                text: lyrics.lyrics_body
        )
    }

    @Override
    Collection<Track> findByArtistName(String artistName) {
        def capacity = 4
        def executor = Executors.newFixedThreadPool(capacity)
        def runningTasks = new ArrayBlockingQueue<Runnable>(capacity)
        def allTasks = [] as List<TrackSearchTask>
        def foundAll = new AtomicBoolean(false)
        int i = 1
        while (!foundAll.get()) {
            def task = new TrackSearchTask()
            task.i = i
            task.foundAll = foundAll
            task.artistName = artistName
            task.tasks = runningTasks
            task.executor = executor
            if (runningTasks.offer(task, 5, TimeUnit.SECONDS)) {
                if (!foundAll.get()) {
                    i = i + 1
                    executor.submit(task)
                    allTasks.add(task)
                }
            } else {
                logger.info("[query '${artistName}'; page '${i}'] waiting for free slot...")
            }
        }
        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)
        def result = [] as HashSet<Track>
        allTasks.each { result.addAll(it.pageResult) }
        logger.info("[query '${artistName}'; page 'all ${i} pages'] end. (${result.size()} items)")
        return result
    }

    class TrackSearchTask implements Runnable {
        int i
        AtomicBoolean foundAll
        String artistName
        List<Track> pageResult
        BlockingQueue tasks
        ExecutorService executor

        void run() {
            logger.info("[query '${artistName}'; page '${i}'] start")
            try {
                this.pageResult = findPageByArtistName(artistName, i)
                if (!pageResult) {
                    foundAll.set(true)
                }
                tasks.remove(this)
                logger.info("[query '${artistName}'; page '${i}'] end. (${pageResult.size()} items)")
            } catch (Exception e) {
                logger.log(Level.WARNING, "[query '${artistName}'; page '${i}'] failed.", e)
                logger.info("submit query again [query '${artistName}'; page '${i}'].")
                executor.submit(this) // trying again
                [].collate()
            }
        }
    }

    private Collection<Track> findPageByArtistName(String artistName, int page) {
        def resp = target.path('track.search')
                .queryParam('q_artist', artistName)
                .queryParam('page_size', 10)
                .queryParam('page', page)
                .queryParam('has_lyrics', 1)
                .request().get()
        def json = new JsonSlurper().parse(
                resp.getEntity() as InputStream
        )
        def result = !json.message.body ? [] : json.message.body.track_list
        return result.findAll({
            it.track.lyrics_id
        }).collect {
            new Track(
                    id: it.track.track_id,
                    title: it.track.track_name,
                    artist: it.track.artist_name,
                    length: it.track.track_length
            )
        }
    }
}
