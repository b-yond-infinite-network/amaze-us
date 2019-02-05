import React, { useState } from "react";
import Select from "../../shared/components/Select";
import Track from "../../shared/types/track";
import TrackList from "../TrackList";
import styles from "./SortableTrackList.module.css";

// sort comparator
const compare = (a: string, b: string) => (a < b ? -1 : a > b ? 1 : 0);

// sort tracks by a given order (asc, desc)
const sortBy = (tracks: Track[], value: string) => {
  // get a new copy of the sorted array
  return [...tracks].sort((a, b) => {
    var nameA = a.track.track_name.toUpperCase();
    var nameB = b.track.track_name.toUpperCase();
    const result = /desc/.test(value)
      ? compare(nameA, nameB)
      : compare(nameB, nameA);
    return result;
  });
};

type Props = {
  artistName: string;
  tracks: Track[];
};

const SortableTrackList: React.FC<Props> = ({
  artistName,
  tracks: initialTracks
}: Props): JSX.Element => {
  const [tracks, setTracks] = useState<Track[]>(initialTracks);
  const [sort, setSort] = useState<string>("");

  const onChange = (e: React.FormEvent<HTMLSelectElement>) => {
    const sortValue: string = e.currentTarget.value;

    setSort(sortValue);

    const sortedTracks: Track[] =
      sortValue === "" ? initialTracks : sortBy(tracks, sortValue);

    // update tracks list
    setTracks(sortedTracks);
  };
  return (
    <>
      <div className={styles.sortableTrackList}>
        <h3>
          Lyrics for top songs by <strong>{artistName}</strong>
        </h3>
        <Select
          id="sort"
          label="Sort by"
          value={sort}
          onChange={onChange}
          options={[
            {
              label: "Popularity",
              value: ""
            },
            {
              label: "Track name (desc)",
              value: "track-desc"
            },
            { label: "Track name (asc)", value: "track-asc" }
          ]}
        />
      </div>
      <TrackList items={tracks} initialized={false} />
    </>
  );
};

export default SortableTrackList;
