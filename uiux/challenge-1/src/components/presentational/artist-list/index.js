// Libraries
import React from 'react';

const ArtistList = ({
  actions,
  artists
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
    <ul style={{
      listStyleType: 'none',
      margin: '1rem 0',
      padding: 0
    }}>
      {artists.map((artist, index) => (
        <li
          key={artist.id}
          style={{
            fontWeight: 'bold',
            margin: '0 0 1rem 0'
          }}
        >
          <a
            className="ui-hoverable"
            href="#0"
            onClick={event => onClick(event, artist)}
            style={{
              display: 'block',
            }}
          >
            <span
              style={{
                background: `rgba(255, 255, 255, ${1 - (index / 3)})`,
                borderRadius: '100%',
                display: 'inline-block',
                height: '4rem',
                marginRight: '0.5rem',
                textDecoration: 'none',
                width: '4rem',
              }}
            ></span>
            <span                
              style={{
                color: `rgba(255, 255, 255, ${1 - (index / 3)})`,
                display: 'inline-block',
                fontSize: '2rem',
                height: '4rem',
                lineHeight: '4rem',
                textDecoration: 'none',
                verticalAlign: 'top'
              }}>{artist.name}</span>
          </a>
        </li>
      ))}
    </ul>
  );
};

export default ArtistList;