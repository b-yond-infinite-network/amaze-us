package karaoke.lyrics;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(exclude = {"text", "track"})
public class Lyrics {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Track track;

}
