import React from "react"
import { expect } from "chai"
import { shallow } from "enzyme"
import Dashboard from "../../../app/components/Dashboard"
import { Title } from "../../../app/components/layout/Title"
import ArtistCard from "../../../app/components/ArtistCard"
import {
  createRandomAmountOfArtists,
  createRandomAmountOfTracks,
} from "../../stubs"
import TrackCard from "../../../app/components/TrackCard"

describe("<Dashboard />", () => {
  it("renders number of artists and artists cards", () => {
    const artists = createRandomAmountOfArtists()

    const wrapper = shallow(<Dashboard topArtists={artists} />)
    expect(wrapper.find(Title).children().text()).to.contain(
      `Top ${artists.length} artists`
    )
    expect(wrapper.find(ArtistCard)).to.have.length(artists.length)
  })

  it("renders number of tracks and track cards", () => {
    const tracks = createRandomAmountOfTracks()

    const wrapper = shallow(<Dashboard topTracks={tracks} />)
    expect(wrapper.find(Title).children().text()).to.contain(
      `Top ${tracks.length} songs`
    )
    expect(wrapper.find(TrackCard)).to.have.length(tracks.length)
  })
})
