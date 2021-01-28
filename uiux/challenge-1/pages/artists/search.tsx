import React from "react"
import { GetServerSideProps } from "next"
import artistService from "../../app/services/artistService"
import PageLayout from "../../app/components/layout/PageLayout"
import { Artist } from "../../app/models"
import { Grid, Typography } from "@material-ui/core"
import { Title } from "../../app/components/layout/Title"
import ArtistCard from "../../app/components/ArtistCard"

interface ArtistSearchPageProps {
  query: string
  artists: Artist[]
}

export default function ArtistSearchPage({
  query,
  artists,
}: ArtistSearchPageProps): JSX.Element {
  const pageTitle = `Search - Sing It with Me!`
  return (
    <PageLayout backUrl="/" title={pageTitle} query={query}>
      {artists.length > 0 ? (
        <Grid container direction="column">
          <Grid item>
            <Title>{`Looking for artists like "${query}"`}</Title>
          </Grid>
          <Grid item>
            <Grid container spacing={2}>
              {artists.map((artist, ix) => (
                <Grid item key={ix} xs={12} sm={6} md={2} xl={1}>
                  <ArtistCard {...artist} />
                </Grid>
              ))}
            </Grid>
          </Grid>
        </Grid>
      ) : (
        <Typography variant="subtitle1">
          No artist found for the keywords "{query}"
        </Typography>
      )}
    </PageLayout>
  )
}

export const getServerSideProps: GetServerSideProps = async ({ query }) => {
  const artists = await artistService.searchArtist(
    query.keywords.toString(),
    12
  )
  console.log(artists)
  return {
    props: {
      artists,
      query: query.keywords.toString(),
    },
  }
}
