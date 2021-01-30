import React from "react"
import { expect } from "chai"
import { shallow } from "enzyme"
import { createTrack } from "../../stubs"
import TrackCard from "../../../app/components/TrackCard"
import RatingStars from "@material-ui/lab/Rating"
import { Link, Chip, Card } from "@material-ui/core"

describe("<TrackCard />", () => {
  it("renders a track card with lyrics available", () => {
    const track = createTrack(true)
    const wrapper = shallow(<TrackCard {...track} />)

    const wrapperText = wrapper.text()
    expect(wrapperText).to.contain(track.name)
    expect(wrapperText).to.contain(track.artistName)
    expect(wrapperText).to.contain(track.albumName)
    expect(wrapper.find(RatingStars).props().value).to.be.eql(track.rating)
    expect(wrapper.find(Link).prop("href")).to.be.eql(`/tracks/${track.id}`)
    expect(wrapper.find(Chip).prop("label")).to.be.eql(track.numFavourite)

    wrapper.find(Card).simulate("mouseOver")
    expect(wrapper.find(".no-lyrics-found").exists()).to.be.eql(false)
  })

  it("renders a track card without lyrics available", () => {
    const track = createTrack(false)
    const wrapper = shallow(<TrackCard {...track} />)

    expect(wrapper.find(Link).prop("href")).to.be.undefined

    wrapper.find(Card).simulate("mouseOver")
    expect(wrapper.find(".no-lyrics-found").exists()).to.be.eql(true)

    wrapper.find(Card).simulate("mouseLeave")
    expect(wrapper.find(".no-lyrics-found").exists()).to.be.eql(false)
  })
})
