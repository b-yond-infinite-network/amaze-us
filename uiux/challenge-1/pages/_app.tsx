import React, { useEffect } from "react"
import type { AppProps } from "next/app"
import { ThemeProvider, CssBaseline, createMuiTheme } from "@material-ui/core"
import { grey } from "@material-ui/core/colors"

const theme = createMuiTheme({
  palette: {
    primary: {
      main: "#00bcd4",
      light: "#62efff",
      dark: "#008ba3",
    },
    secondary: {
      main: "#3f51b5",
      light: "#757de8",
      dark: "#002984",
    },
    text: {
      primary: grey[900],
      secondary: grey[50],
    },
    background: {
      default: grey[100],
    },
  },
  overrides: {
    MuiFormControl: {
      root: {
        backgroundColor: grey[300],
      },
    },
    MuiInputLabel: {
      root: {
        color: grey[900],
      },
    },
  },
})

function MyApp({ Component, pageProps }: AppProps): JSX.Element {
  useEffect(() => {
    // Remove the server-side injected CSS.
    const jssStyles = document.querySelector("#jss-server-side")
    if (jssStyles) {
      jssStyles.parentElement.removeChild(jssStyles)
    }
  }, [])

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Component {...pageProps} />
    </ThemeProvider>
  )
}

export default MyApp
