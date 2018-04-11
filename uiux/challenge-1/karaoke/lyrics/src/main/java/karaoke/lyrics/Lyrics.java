package karaoke.lyrics;

import java.util.Objects;

public class Lyrics {
    private Long id;
    private String text;
    private Track track;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lyrics lyrics = (Lyrics) o;
        return Objects.equals(id, lyrics.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
