import { expect } from "chai"

jest.mock("../../../app/services/api")
import fetch from "../../../app/services/api"
import { getArtistAlbumsMock } from "../../apiMock"
import albumService from "../../../app/services/albumService"

describe("albumService", () => {
  it("obtains artist albums", async () => {
    const [artistId, numberOfAlbums, body] = getArtistAlbumsMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const albums = await albumService.getArtistAlbums(artistId)
    expect(albums.length).to.be.eql(numberOfAlbums)
  })
})
