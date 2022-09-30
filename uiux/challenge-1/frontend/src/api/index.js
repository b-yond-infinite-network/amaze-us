const baseFetch = (path, method, params, body, ...rest) =>
  fetch(`${new URL(path, import.meta.env.VITE_BASE_URL)}?${new URLSearchParams(params)}`, {
    method,
    body,
    ...rest,
  })
    .then(res => res.json())
    .then(({ message: { header: { status_code }, body } }) => {
      if (status_code >= 400)
        throw new Error(status_code);
      return body;
    });

export const client = {
  get: (path, params, ...rest) =>
    baseFetch(path, 'GET', params, null, ...rest),
};
