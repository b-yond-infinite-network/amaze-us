package karaoke.api;

import java.util.Objects;

public class DtoLyrics {
    private Long id;
    private String text;
    private DtoTrack track;

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

    public DtoTrack getTrack() {
        return track;
    }

    public void setTrack(DtoTrack track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoLyrics dtoLyrics = (DtoLyrics) o;
        return Objects.equals(id, dtoLyrics.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
