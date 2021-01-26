import { expect } from "chai"
import fetchData from "../../../app/services/api"

jest.mock("node-fetch")
import fetch from "node-fetch"

const serviceUrl = "http://some.url"
const apikey = "some-apikey"
jest.mock("../../../app/config", () => ({
  apiUrl: serviceUrl,
  apikey,
}))

beforeEach(() => {
  fetch.mockReset()
})

describe("api", () => {
  it("Fetches data from api without params", async () => {
    const response = {
      json: () => Promise.resolve({}),
    }
    fetch.mockReturnValue(Promise.resolve(response))

    await fetchData("service")
    const fetchCalls = fetch.mock.calls
    expect(fetchCalls.length).to.be.eql(1)
    expect(fetchCalls[0][0]).to.contain("service")
    expect(fetchCalls[0][0]).to.contain(serviceUrl)
    expect(fetchCalls[0][0]).to.contain(apikey)
  })

  it("Fetches data from api with params", async () => {
    const response = {
      json: () => Promise.resolve({}),
    }
    fetch.mockReturnValue(Promise.resolve(response))

    await fetchData("service", { page_size: 3 })
    const fetchCalls = fetch.mock.calls
    expect(fetchCalls.length).to.be.eql(1)
    expect(fetchCalls[0][0]).to.contain("service")
    expect(fetchCalls[0][0]).to.contain(serviceUrl)
    expect(fetchCalls[0][0]).to.contain(apikey)
    expect(fetchCalls[0][0]).to.contain("page_size=3")
  })
})
