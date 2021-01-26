import appConfig from "../config"
import { stringify } from "query-string"
import fetch from "node-fetch"

export default async function fetchData(
  service: string,
  params: Record<string, any> = {}
): Promise<any> {
  const urlParams = stringify({ ...params, apikey: appConfig.apikey })
  const url = `${appConfig.apiUrl}/${service}?${urlParams}`

  const response = await fetch(url)
  return await response.json()
}
