interface AppConfig {
  apiUrl: string
  apikey: string
  apiCacheEnabled: boolean
}

const config: AppConfig = {
  apiUrl: process.env.MUSIXMATCH_API_URL,
  apikey: process.env.MUSIXMATCH_APIKEY,
  apiCacheEnabled: process.env.API_CACHE_ENABLED
    ? process.env.API_CACHE_ENABLED === "true"
    : true,
}

export default config
