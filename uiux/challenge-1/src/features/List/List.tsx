import { Link } from "@reach/router";
import React, { FunctionComponent } from "react";
import Card from "../../shared/components/Card";
import Artist from "../../shared/types/artist";
import styles from "./List.module.css";
import Block from "../../shared/components/Block";

type Props = { items: Artist[]; initialized: boolean };

const List: FunctionComponent<Props> = ({
  items: initialItems,
  initialized
}: Props) => {
  return (
    <>
      {initialItems.length > 0 && (
        <ul className={styles.list}>
          {initialItems.map((item: Artist, i: number) => (
            <li
              key={item.artist.artist_id}
              style={{ "--i": i } as any}
              data-testid="List-listItem"
            >
              <Link to={`/artist/${item.artist.artist_id}`}>
                <Card title={item.artist.artist_name} />
              </Link>
            </li>
          ))}
        </ul>
      )}
      {!initialItems.length && initialized && (
        <Block>
          <p data-testid="List-notFound">Artist Not found</p>
        </Block>
      )}
    </>
  );
};

export default List;
