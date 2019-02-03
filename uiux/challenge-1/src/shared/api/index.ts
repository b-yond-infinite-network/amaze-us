const API_URL: string =
  process.env.REACT_APP_API_URL ||
  "https://cors-anywhere.herokuapp.com/https://api.musixmatch.com/ws/1.1/";

const API_KEY: string =
  process.env.REACT_APP_API_KEY || "b9b440d034fba7bce1c4a3d09899a0f7";

export async function call(url: string) {
  const response = await fetch(API_URL + url + `&apikey=${API_KEY}`);

  if (!response.ok) {
    throw new Error(response.statusText);
  }

  const {
    message: { body }
  } = await response.json();

  return body;
}
