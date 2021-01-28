import React from "react"
import { GetStaticProps } from "next"
import Dashboard, { DashboardProps } from "../app/components/Dashboard"
import artistService from "../app/services/artistService"
import PageLayout from "../app/components/layout/PageLayout"
import trackService from "../app/services/trackService"

const numberOfArtistsToShow = 6
const numberOfTracksToShow = 8

export default function Home(props: DashboardProps): JSX.Element {
  return (
    <PageLayout title="Sing It With Me!">
      <Dashboard {...props} />
    </PageLayout>
  )
}

export const getStaticProps: GetStaticProps = async () => {
  const topArtists = await artistService.getTopArtists(numberOfArtistsToShow)
  const topTracks = await trackService.getTopTracks(numberOfTracksToShow)

  return {
    props: { topArtists, topTracks },
    revalidate: 24 * 60 * 60, // 24 hours
  }
}
