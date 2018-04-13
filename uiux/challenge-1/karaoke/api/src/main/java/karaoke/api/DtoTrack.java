package karaoke.api;

import java.util.Objects;

public class DtoTrack {
    private Long id;
    private String title;
    private String artist;
    private Long length;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoTrack dtoTrack = (DtoTrack) o;
        return Objects.equals(id, dtoTrack.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}