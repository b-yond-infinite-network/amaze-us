export const artistAdapter = ({artist}) => {
  return {
    artistId: artist.artist_id,
    artistName: artist.artist_name,
    ...artist
  };
};