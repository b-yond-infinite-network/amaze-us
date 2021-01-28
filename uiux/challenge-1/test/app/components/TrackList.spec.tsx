import React from "react"
import { expect } from "chai"
import { shallow } from "enzyme"
import TrackList from "../../../app/components/TrackList"
import { createRandomAmountOfTracks } from "../../stubs"
import { TrackOrdering } from "../../../app/models"
import TrackCard from "../../../app/components/TrackCard"
import sortBy from "lodash/sortBy"
import reverse from "lodash/reverse"

describe("<TrackList />", () => {
  it("renders a list of tracks with a reverse order", () => {
    const tracks = createRandomAmountOfTracks()
    const wrapper = shallow(
      <TrackList tracks={tracks} order={TrackOrdering.LIKES} />
    )

    const orderedTrackIds = reverse(
      sortBy(tracks, ["numFavourite"]).map((track) => track.id)
    )

    expect(wrapper.find(TrackCard)).to.have.length(tracks.length)
    expect(wrapper.find(TrackCard).map((card) => card.prop("id"))).to.be.eql(
      orderedTrackIds
    )
  })

  it("renders a list of tracks with an order", () => {
    const tracks = createRandomAmountOfTracks()
    const wrapper = shallow(
      <TrackList tracks={tracks} order={TrackOrdering.NAME} />
    )

    const orderedTrackIds = sortBy(tracks, ["name"]).map((track) => track.id)

    expect(wrapper.find(TrackCard)).to.have.length(tracks.length)
    expect(wrapper.find(TrackCard).map((card) => card.prop("id"))).to.be.eql(
      orderedTrackIds
    )
  })
})
