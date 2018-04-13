package karaoke.api;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(exclude = {"text", "track"})
public class DtoLyrics {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private DtoTrack track;

}
