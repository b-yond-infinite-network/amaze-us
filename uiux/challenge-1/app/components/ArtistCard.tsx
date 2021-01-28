import React, { useState } from "react"
import {
  Card,
  Typography,
  CardContent,
  makeStyles,
  Link,
} from "@material-ui/core"
import RatingStars from "@material-ui/lab/Rating"
import { Artist } from "../models"

const useStyles = makeStyles({
  card: {
    height: "100%",
  },
})

const ArtistCard = ({ id, name, rating }: Artist): JSX.Element => {
  const classes = useStyles()
  const [hover, setHover] = useState(false)

  return (
    <Link href={`/artists/${id}`}>
      <Card
        className={classes.card}
        onMouseOver={() => setHover(true)}
        onMouseLeave={() => setHover(false)}
        raised={hover}
      >
        <CardContent>
          <Typography variant="h6">{name}</Typography>
          <RatingStars value={rating} readOnly />
        </CardContent>
      </Card>
    </Link>
  )
}

export default ArtistCard
