import React from "react"
import { GetServerSideProps } from "next"
import artistService from "../../app/services/artistService"
import PageLayout from "../../app/components/layout/PageLayout"
import ArtistCard from "../../app/components/ArtistCard"
import { Artist } from "../../app/models"

interface ArtistPageProps {
  artist: Artist
}

export default function ArtistPage({ artist }: ArtistPageProps): JSX.Element {
  const pageTitle = `${artist.name} tracks - Sing It with Me!`
  return (
    <PageLayout backUrl="/" title={pageTitle}>
      <ArtistCard {...artist} />
    </PageLayout>
  )
}

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  const artistId = parseInt(params.id.toString())
  const artist = await artistService.getArtist(artistId)

  return {
    props: {
      artist,
    },
  }
}
