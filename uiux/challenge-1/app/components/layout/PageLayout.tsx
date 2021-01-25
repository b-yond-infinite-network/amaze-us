import React from "react"
import Head from "next/head"
import { Box } from "@material-ui/core"
import { TopBar } from "./TopBar"

const PageLayout = ({
  children,
}: React.PropsWithChildren<any>): JSX.Element => (
  <>
    <Head>
      <title>Sing It With Me</title>
    </Head>
    <TopBar />
    <Box mt={1}>{children}</Box>
  </>
)

export default PageLayout
