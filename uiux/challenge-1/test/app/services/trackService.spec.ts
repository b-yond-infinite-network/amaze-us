import { expect } from "chai"

jest.mock("../../../app/services/api")
import fetch from "../../../app/services/api"
import {
  getAlbumTracksMock,
  getTrackChartMock,
  getTrackMock,
  getTrackLyricsMock,
} from "../../apiMock"
import trackService from "../../../app/services/trackService"

describe("trackService", () => {
  it("obtains top N songs", async () => {
    const [pageSize, responseBody] = getTrackChartMock()
    fetch.mockReturnValue(Promise.resolve(responseBody))

    const fetchedTracks = await trackService.getTopTracks(pageSize)

    expect(fetchedTracks.length).to.be.eql(pageSize)
  })

  it("obtains album tracks", async () => {
    const [albumId, numberOfTracks, body] = getAlbumTracksMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const tracks = await trackService.getAlbumTracks(albumId)
    expect(tracks.length).to.be.eql(numberOfTracks)
  })

  it("obtains a single track", async () => {
    const [trackId, body] = getTrackMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const track = await trackService.getTrack(trackId)
    expect(track.id).to.be.eql(trackId)
  })

  it("obtains track lyrics", async () => {
    const [trackId, body] = getTrackLyricsMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const lyrics = await trackService.getTrackLyrics(trackId)
    expect(lyrics.id).to.be.eql(body.message.body.lyrics.lyrics_id)
  })
})
