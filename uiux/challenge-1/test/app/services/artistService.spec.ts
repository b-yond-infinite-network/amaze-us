import { expect } from "chai"
import artistService from "../../../app/services/artistService"

jest.mock("../../../app/services/api")
import fetch from "../../../app/services/api"
import {
  getArtistChartMock,
  getArtistMock,
  getArtistSearchMock,
} from "../../apiMock"

describe("artistService", () => {
  it("obtains top N artists", async () => {
    const [pageSize, responseBody] = getArtistChartMock()
    fetch.mockReturnValue(Promise.resolve(responseBody))

    const fetchedArtists = await artistService.getTopArtists(pageSize)

    expect(fetchedArtists.length).to.be.eql(pageSize)
  })

  it("obtains some artist info", async () => {
    const [artistId, body] = getArtistMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const artist = await artistService.getArtist(artistId)
    expect(artist.id).to.be.eql(artistId)
  })

  it("searchs an artist", async () => {
    const [query, pageSize, body] = getArtistSearchMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const artists = await artistService.searchArtist(query, pageSize)
    expect(artists.length).to.be.eql(pageSize)
  })
})
