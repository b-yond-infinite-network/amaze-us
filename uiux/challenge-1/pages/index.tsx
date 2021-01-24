import React from "react"
import Head from "next/head"
import { TopBar } from "../app/TopBar"

export default function Home(): JSX.Element {
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
    </>
  )
}
