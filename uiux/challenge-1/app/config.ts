interface AppConfig {
  apiUrl: string
  apikey: string
}

const config: AppConfig = {
  apiUrl: process.env.MUSIXMATCH_API_URL,
  apikey: process.env.MUSIXMATCH_APIKEY,
}

export default config
