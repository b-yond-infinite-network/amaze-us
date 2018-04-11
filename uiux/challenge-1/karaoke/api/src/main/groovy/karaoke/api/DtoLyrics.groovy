package karaoke.api

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(excludes=['text', 'track'])
class DtoLyrics {
    Long id
    String text
    DtoTrack track
}
