import http from 'http';
import https from 'https';
import url from 'url';

const PORT = process.env.PORT ?? 8080;

export const API_URL = 'https://api.musixmatch.com/ws/1.1';

// Never save api key inside your code or repository in real projects
export const API_KEY = '49fbb9509a27e4fb55722a25ae201a0b';

http.createServer(async (req, res) => {
  const { path, search } = url.parse(req.url);
  res.setHeader('Content-Type', 'application/json');
  res.setHeader('Access-Control-Allow-Origin', '*');
  const apiReq = https.request(`${API_URL}${path}${search ? '&' : '?'}apikey=${API_KEY}&format=json`, {
    method: req.method,
  }, apiRes => {
    res.writeHead(apiRes.statusCode);
    apiRes.on('data', chunk => res.write(chunk));
    apiRes.on('end', () => res.end());
  });
  req.on('data', chunk => apiReq.write(chunk));
  req.on('end', () => apiReq.end());
}).listen(PORT, () => console.log(`Listening on http://localhost:${PORT}`));
