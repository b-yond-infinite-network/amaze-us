import appConfig from "../config"
import { stringify } from "query-string"
import fetch from "node-fetch"
import cacheManager from "cache-manager"

async function getJsonFromUrl(url) {
  const response = await fetch(url)
  return await response.json()
}

async function fetchData(
  service: string,
  params: Record<string, any> = {}
): Promise<any> {
  const urlParams = stringify({ ...params, apikey: appConfig.apikey })
  const url = `${appConfig.apiUrl}/${service}?${urlParams}`
  return await getJsonFromUrl(url)
}

const memoryCache = cacheManager.caching({ store: "memory", ttl: 60 * 60 * 24 })

async function cachedFetchData(
  service: string,
  params: Record<string, any> = {}
): Promise<any> {
  const urlParams = stringify({ ...params, apikey: appConfig.apikey })
  const url = `${appConfig.apiUrl}/${service}?${urlParams}`

  return await memoryCache.wrap(url, () => {
    return getJsonFromUrl(url)
  })
}

export default appConfig.apiCacheEnabled ? cachedFetchData : fetchData
