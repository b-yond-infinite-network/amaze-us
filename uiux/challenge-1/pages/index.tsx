import React from "react"
import { GetStaticProps } from "next"
import Dashboard, { DashboardProps } from "../app/components/Dashboard"
import artistService from "../app/services/artistService"
import PageLayout from "../app/components/layout/PageLayout"

const numberOfArtistsToShow = 10

export default function Home(props: DashboardProps): JSX.Element {
  return (
    <PageLayout>
      <Dashboard {...props} />
    </PageLayout>
  )
}

export const getStaticProps: GetStaticProps = async () => {
  const topArtists = await artistService.getTopArtists(numberOfArtistsToShow)

  return {
    props: { topArtists },
    revalidate: 24 * 60 * 60, // 24 hours
  }
}
