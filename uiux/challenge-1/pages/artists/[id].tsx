import React from "react"
import { GetServerSideProps } from "next"
import artistService from "../../app/services/artistService"
import PageLayout from "../../app/components/layout/PageLayout"
import ArtistCard from "../../app/components/ArtistCard"
import { Artist, Track } from "../../app/models"
import albumService from "../../app/services/albumService"
import { Album } from "../../app/models/album"
import trackService from "../../app/services/trackService"

interface ArtistPageProps {
  artist: Artist
  albums: Album[]
  tracks: Track[]
}

export default function ArtistPage({
  artist,
  albums,
  tracks,
}: ArtistPageProps): JSX.Element {
  const pageTitle = `${artist.name} tracks - Sing It with Me!`
  console.log(artist, albums, tracks)
  return <PageLayout backUrl="/" title={pageTitle}></PageLayout>
}

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  const artistId = parseInt(params.id.toString())
  const artist = await artistService.getArtist(artistId)
  const albums = await albumService.getArtistAlbums(artist.id)
  const tracks = await Promise.all(
    albums.map((album) => trackService.getAlbumTracks(album.id))
  )

  return {
    props: {
      artist,
      albums,
      tracks: [].concat(...tracks),
    },
  }
}
