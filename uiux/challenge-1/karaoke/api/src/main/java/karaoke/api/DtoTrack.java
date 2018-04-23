package karaoke.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(exclude = {"title", "artist", "length"})
public class DtoTrack {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String artist;

    @Getter
    @Setter
    private Long length;

}
