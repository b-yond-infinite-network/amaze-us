import React from "react"
import { Typography, Box } from "@material-ui/core"

interface TitleProps {
  children: string
}

export const Title = ({ children }: TitleProps): JSX.Element => (
  <Box mb={1}>
    <Typography variant="h4">{children}</Typography>
  </Box>
)
