import { RouteComponentProps } from "@reach/router";
import React, { FunctionComponent, useEffect, useState } from "react";
import TrackList from "../features/TrackList";
import { getArtist } from "../shared/api/artist";
import { searchTracks } from "../shared/api/tracks";
import Block from "../shared/components/Block";
import Loader from "../shared/components/Loader";
import Artist from "../shared/types/artist";
import Track from "../shared/types/tracks";

type BaseProps = {
  id?: string;
};

type Props = BaseProps & RouteComponentProps;

const ArtistPage: FunctionComponent<Props> = ({
  id = ""
}: Props): JSX.Element => {
  const [tracks, setTracks] = useState<Track[]>([]);
  const [artist, setArtist] = useState<Artist | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const fetchData = async () => {
    // show loader
    setLoading(true);

    const artistResponse = await getArtist(Number(id));
    setArtist(artistResponse);

    const tracksResponse: Track[] = await searchTracks(id);
    setTracks(tracksResponse);

    // hide loader
    setLoading(false);
  };

  useEffect(() => {
    if (id) {
      fetchData();
    }
  }, [id]);

  return (
    <main data-testid="page-artist">
      {artist && <h1>{artist.artist.artist_name}</h1>}

      {loading && <Loader />}

      {!loading && tracks.length > 0 && artist && (
        <Block variation="dark">
          <h3>
            Lyrics for top songs by <strong>{artist.artist.artist_name}</strong>
          </h3>
          <TrackList items={tracks} initialized={false} />
        </Block>
      )}
    </main>
  );
};

export default ArtistPage;
