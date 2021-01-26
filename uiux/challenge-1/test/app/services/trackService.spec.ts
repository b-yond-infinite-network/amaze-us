import { expect } from "chai"

jest.mock("../../../app/services/api")
import fetch from "../../../app/services/api"
import { getAlbumTracksMock } from "../../apiMock"
import trackService from "../../../app/services/trackService"

describe("trackService", () => {
  it("obtains album tracks", async () => {
    const [albumId, numberOfTracks, body] = getAlbumTracksMock()
    fetch.mockReturnValue(Promise.resolve(body))

    const tracks = await trackService.getAlbumTracks(albumId)
    expect(tracks.length).to.be.eql(numberOfTracks)
  })
})
