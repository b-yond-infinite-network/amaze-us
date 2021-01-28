import React from "react"
import { GetServerSideProps } from "next"
import artistService from "../../app/services/artistService"
import PageLayout from "../../app/components/layout/PageLayout"
import albumService from "../../app/services/albumService"
import trackService from "../../app/services/trackService"
import ArtistDetail, {
  ArtistDetailProps,
} from "../../app/components/ArtistDetail"

export default function ArtistPage(props: ArtistDetailProps): JSX.Element {
  const pageTitle = `${props.artist.name} tracks - Sing It with Me!`
  return (
    <PageLayout backUrl="/" title={pageTitle}>
      <ArtistDetail {...props} />
    </PageLayout>
  )
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
