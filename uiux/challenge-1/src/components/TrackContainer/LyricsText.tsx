import { Lyrics } from "../../types/DTOs/LyricsDTOs"

type LyricsTextProps = { lyrics?: Lyrics }

export const LyricsText = (props: LyricsTextProps) => {
    return props.lyrics ?
        <div className="text-center">
            {props.lyrics?.lyrics_body.split("\n").map((p, i) => <span key={i}>{p.toString()}<br /></span>)}
        </div>
        ?? <div>"No lyrics found"</div>
        : <></>
}