import React, { useState } from "react"
import { Card, Typography, CardContent, makeStyles } from "@material-ui/core"
import RatingStars from "@material-ui/lab/Rating"
import { Artist } from "../models"
import { useRouter } from "next/dist/client/router"

const useStyles = makeStyles({
  card: {
    height: "100%",
    width: "225px",
  },
})

const ArtistCard = ({ id, name, rating }: Artist): JSX.Element => {
  const classes = useStyles()
  const router = useRouter()
  const [hover, setHover] = useState(false)

  function handleClick() {
    router.push({
      pathname: "/artists/[id]",
      query: { id },
    })
  }

  return (
    <Card
      className={classes.card}
      onClick={handleClick}
      onMouseOver={() => setHover(true)}
      onMouseLeave={() => setHover(false)}
      raised={hover}
    >
      <CardContent>
        <Typography variant="h6">{name}</Typography>
        <RatingStars value={rating} readOnly />
      </CardContent>
    </Card>
  )
}

export default ArtistCard
