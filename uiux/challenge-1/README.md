# Sing It With Me!

This is a project created for b-yond-infinite-network. You can see the fully working demo in [https://sing-it-with-me.herokuapp.com/] (keep in mind that this uses a test plan from Musix Match, so it might stop working if the daily request rates have been consumed).

## Highlights

- **React** with **Typescript** as development framework
- **Next.js** as the SSR framework
- **Material-UI** for material design and responsive components.
- **cache-manager** for caching api requests (memory cache)
- **Jest** for unit testing
- **Cypress** for end to end testing
- **Eslint**
- **Fully responsive**

## Development

First, install the required dependencies: `yarn install` and then run the development server: `yarn dev`. Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Testing

For unit tests run `yarn test` or `yarn test --watchAll`.

If you wish to run end to end tests, you must have a development or production instance running in http://localhost:3000 and execute `yarn cypress:open`.

## Production build

Just build the app with `yarn build` and run the server with `yarn start` (you must have environment variables set or an `.env` file)

### Environment variables

- **PORT**: port in with the server will run
- **MUSIXMATCH_API_URL**: Musix Match api url ([https://api.musixmatch.com/ws/1.1/])
- **MUSIXMATCH_APIKEY**: Api key provided by Musix Match
- **API_CACHE_ENABLED**: "true" if you want to enable api memory cache (default value). "false" if you want to disable caching. If you're running a production environment with high concurrency, you might want to set this to "false" and use a proxy like Nginx to cache pages (this also will help you increase response times).
