import React from "react"
import Head from "next/head"
import { GetStaticProps } from "next"
import { TopBar } from "../app/components/TopBar"
import Dashboard, { DashboardProps } from "../app/components/Dashboard"
import { Box } from "@material-ui/core"
import artistService from "../app/services/artistService"

export default function Home(props: DashboardProps): JSX.Element {
  return (
    <>
      <Head>
        <title>Sing It With Me</title>
        <link rel="icon" href="/favicon.ico" />
        <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
        />
        <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/icon?family=Material+Icons"
        />
        <meta
          name="viewport"
          content="minimum-scale=1, initial-scale=1, width=device-width"
        />
      </Head>
      <TopBar />
      <Box mt={1}>
        <Dashboard {...props} />
      </Box>
    </>
  )
}

export const getStaticProps: GetStaticProps = async () => {
  const topArtists = await artistService.getTop10Artists()

  return {
    props: { topArtists },
  }
}
