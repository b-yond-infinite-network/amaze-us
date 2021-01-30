import React, { useState } from "react"
import { useRouter } from "next/router"
import {
  TextField,
  InputAdornment,
  makeStyles,
  Hidden,
  IconButton,
  Popover,
  useTheme,
} from "@material-ui/core"
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

const ArtistSearchInput = ({
  defaultValue,
}: ArtistSearchInputProps): JSX.Element => {
  const classes = useStyles()
  const router = useRouter()
  const theme = useTheme()
  const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null)
  const [trigger, setTrigger] = useState(null)

  const open = Boolean(anchorEl)

  const searchArtist = (query: string) => () => {
    const queryParams = stringify({ keywords: query })
    router.push(`/artists/search?${queryParams}`)
  }

  const handleSMTextfieldChange = (
    evt: React.ChangeEvent<{ value: string }>
  ) => {
    if (trigger) clearTimeout(trigger)

    setTrigger(setTimeout(searchArtist(evt.target.value), 800))
  }

  const handleXSButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const handleXSPopoverClose = () => {
    setAnchorEl(null)
  }

  return (
    <>
      <Hidden only="xs">
        <TextField
          label="Look for an artist"
          variant="outlined"
          size="small"
          color="primary"
          defaultValue={defaultValue}
          onChange={handleSMTextfieldChange}
          InputProps={{
            className: classes.input,
            endAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
        />
      </Hidden>
      <Hidden smUp>
        <Popover
          open={open}
          onClose={handleXSPopoverClose}
          anchorReference="anchorPosition"
          anchorPosition={{ top: theme.spacing(2), left: theme.spacing(2) }}
        >
          <TextField
            label="Look for an artist"
            variant="outlined"
            size="small"
            color="primary"
            defaultValue={defaultValue}
            onChange={handleSMTextfieldChange}
          />
        </Popover>
        <IconButton color="inherit" onClick={handleXSButtonClick}>
          <SearchIcon />
        </IconButton>
      </Hidden>
    </>
  )
}

export default ArtistSearchInput
