// Libraries
import React from 'react';

const ArtistList = ({
  actions,
  searchArtistResults
}) => {

  /**
   * @method selectArtist
   * @description Dispatch user selection
   */
  const selectArtist = artistId => actions.selectArtist(artistId);

  /**
   * @method onClick
   * @description Handle link click
   */
  const onClick = (event, artistData) => {
    event.preventDefault();
    selectArtist(artistData); 
  };

  return (
    <div>
      <ul>
        {searchArtistResults.map(artist => (
          <li
            key={artist.id}
          >
            <a
              href="#0"
              onClick={event => onClick(event, artist)}
            >{artist.name}</a>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ArtistList;