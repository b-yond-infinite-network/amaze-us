import React from "react"
import { Typography, Box } from "@material-ui/core"

export const Title = ({
  children,
}: React.PropsWithChildren<any>): JSX.Element => (
  <Box m={1} mb={2}>
    <Typography variant="h4">{children}</Typography>
  </Box>
)
