import React from "react"
import { expect } from "chai"
import { shallow } from "enzyme"
import { Artist } from "../../../app/models"
import Dashboard from "../../../app/components/Dashboard"
import { Title } from "../../../app/components/layout/Title"
import ArtistCard from "../../../app/components/ArtistCard"
import { createRandomAmountOfArtists } from "../../stubs"

describe("<Dashboard />", () => {
  it("renders number of artists and artists cards", () => {
    const artists: Artist[] = createRandomAmountOfArtists()

    const wrapper = shallow(<Dashboard topArtists={artists} />)
    expect(wrapper.find(Title).children().text()).to.contain(
      `Top ${artists.length} artists`
    )
    expect(wrapper.find(ArtistCard)).to.have.length(artists.length)
  })
})
