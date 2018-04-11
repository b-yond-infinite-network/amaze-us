package karaoke.api

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(excludes=['title', 'artistName', 'length'])
class DtoTrack {
    Long id
    String title
    String artist
    Long length
}
