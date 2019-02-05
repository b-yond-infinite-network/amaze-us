import { Link } from "@reach/router";
import React, { FunctionComponent } from "react";
import Block from "../../shared/components/Block";
import Card from "../../shared/components/Card";
import List, { ListItem } from "../../shared/components/List";
import Artist from "../../shared/types/artist";

type Props = { items: Artist[]; initialized: boolean };

const ArtistList: FunctionComponent<Props> = ({
  items: initialItems,
  initialized
}: Props) => {
  return (
    <>
      {initialItems.length > 0 && (
        <List>
          {initialItems.map((item: Artist, i: number) => (
            <ListItem key={item.artist.artist_id} style={{ "--i": i }}>
              <Link to={`/artist/${item.artist.artist_id}`}>
                <Card
                  title={item.artist.artist_name}
                  text={`Rating: ${item.artist.artist_rating}` || ""}
                />
              </Link>
            </ListItem>
          ))}
        </List>
      )}
      {!initialItems.length && initialized && (
        <Block>
          <p data-testid="ArtistList-notFound">Artist Not found</p>
        </Block>
      )}
    </>
  );
};

export default ArtistList;
