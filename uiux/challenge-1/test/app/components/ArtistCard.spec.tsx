import React from "react"
import { expect } from "chai"
import { shallow } from "enzyme"
import { Typography, Link } from "@material-ui/core"
import RatingStars from "@material-ui/lab/Rating"

import ArtistCard from "../../../app/components/ArtistCard"
import { createArtist } from "../../stubs"

describe("<ArtistCard />", () => {
  const artist = createArtist()

  it("renders artist name and rating", () => {
    const wrapper = shallow(<ArtistCard {...artist} />)
    expect(wrapper.find(Typography).text()).to.be.eql(artist.name)
    expect(wrapper.find(RatingStars).props().value).to.be.eql(artist.rating)
  })

  it("should have a link to artists page", () => {
    const wrapper = shallow(<ArtistCard {...artist} />)
    expect(wrapper.find(Link).prop("href")).to.be.eql(`/artists/${artist.id}`)
  })
})
