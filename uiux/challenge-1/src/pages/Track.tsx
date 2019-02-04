import { RouteComponentProps, Link } from "@reach/router";
import React, { FunctionComponent, useEffect, useState } from "react";
import { getTrack, getLyrics } from "../shared/api/track";
import Block from "../shared/components/Block";
import Loader from "../shared/components/Loader";
import Artist from "../shared/types/artist";
import Track, { Lyrics } from "../shared/types/track";

type BaseProps = {
  id?: string;
};

type Props = BaseProps & RouteComponentProps;

const TrackPage: FunctionComponent<Props> = ({
  id = ""
}: Props): JSX.Element => {
  const [loading, setLoading] = useState<boolean>(false);
  const [track, setTrack] = useState<Track | null>(null);
  const [lyrics, setLyrics] = useState<Lyrics | null>(null);

  const fetchData = async () => {
    // show loader
    setLoading(true);

    const lyricsResponse = await getLyrics(id);
    setLyrics(lyricsResponse);

    const trackResponse: Track = await getTrack(id);
    setTrack(trackResponse);

    // hide loader
    setLoading(false);
  };

  useEffect(() => {
    if (id) {
      fetchData();
    }
  }, [id]);

  return (
    <section data-testid="page-track">
      {loading && <Loader />}

      {!loading && track && (
        <header>
          <h2>{track.track.track_name}</h2>
          <h3>
            <Link to={`/artist/${track.track.artist_id}`} className="link">
              {track.track.artist_name}
            </Link>
          </h3>
        </header>
      )}

      {!loading && lyrics && (
        <Block variation="dark">
          <div
            dangerouslySetInnerHTML={{
              __html: lyrics.lyrics_body.replace(/[\n]/g, "<br />")
            }}
            data-testid="Track-lyrics"
          />

          <p>{lyrics.lyrics_copyright}</p>
        </Block>
      )}
    </section>
  );
};

export default TrackPage;
