import React, { FunctionComponent } from "react";
import Track from "../../shared/types/track";
import { Link } from "@reach/router";
import Card from "../../shared/components/Card";
import Block from "../../shared/components/Block";
import List, { ListItem } from "../../shared/components/List";

type Props = { items: Track[]; initialized: boolean };

const TrackList: FunctionComponent<Props> = ({
  items: initialItems,
  initialized
}: Props) => {
  return (
    <>
      {initialItems.length > 0 && (
        <List>
          {initialItems.map((item: Track, i: number) => (
            <ListItem key={i} style={{ "--i": i }}>
              <Link to={`/track/${item.track.track_id}`}>
                <Card title={item.track.track_name}>
                  <p>
                    Album: <strong>{item.track.album_name}</strong>
                  </p>
                </Card>
              </Link>
            </ListItem>
          ))}
        </List>
      )}
      {!initialItems.length && initialized && (
        <Block>
          <p data-testid="TrackList-notFound">Tracks not found</p>
        </Block>
      )}
    </>
  );
};

export default TrackList;
