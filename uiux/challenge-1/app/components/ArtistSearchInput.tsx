import React, { useState } from "react"
import { useRouter } from "next/router"
import { TextField, InputAdornment, makeStyles } from "@material-ui/core"
import { stringify } from "query-string"
import SearchIcon from "@material-ui/icons/Search"

interface ArtistSearchInputProps {
  defaultValue?: string
}

const useStyles = makeStyles((theme) => ({
  input: {
    backgroundColor: theme.palette.background.paper,
  },
}))

const ArtistSearchInput = ({ defaultValue }: ArtistSearchInputProps) => {
  const classes = useStyles()
  const router = useRouter()
  const [trigger, setTrigger] = useState(null)

  const searchArtist = (query: string) => () => {
    const queryParams = stringify({ keywords: query })
    router.push(`/artists/search?${queryParams}`)
  }

  const handleChange = (evt: React.ChangeEvent<{ value: string }>) => {
    if (trigger) clearTimeout(trigger)

    setTrigger(setTimeout(searchArtist(evt.target.value), 1000))
  }

  return (
    <TextField
      label="Look for an artist"
      variant="outlined"
      size="small"
      color="primary"
      defaultValue={defaultValue}
      onChange={handleChange}
      InputProps={{
        className: classes.input,
        endAdornment: (
          <InputAdornment position="start">
            <SearchIcon />
          </InputAdornment>
        ),
      }}
    />
  )
}

export default ArtistSearchInput
