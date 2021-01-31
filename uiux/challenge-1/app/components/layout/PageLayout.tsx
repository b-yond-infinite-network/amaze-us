import React from "react"
import Head from "next/head"
import { Box } from "@material-ui/core"
import { TopBar, TopBarProps } from "./TopBar"

interface PageLayoutProps
  extends React.PropsWithChildren<unknown>,
    TopBarProps {
  title: string
}

const PageLayout = ({
  children,
  title,
  ...topBarProps
}: PageLayoutProps): JSX.Element => (
  <>
    <Head>
      <title>{title}</title>

      <meta
        name="viewport"
        content="minimum-scale=1, initial-scale=1, width=device-width"
      />
    </Head>
    <TopBar {...topBarProps} />
    <Box mt={1} className="test-content">
      {children}
    </Box>
  </>
)

export default PageLayout
