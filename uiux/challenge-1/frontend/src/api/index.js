import { API_URL } from './constants';

const baseFetch = (path, method, params, body, ...rest) => fetch(
  `${new URL(path, API_URL)}?${new URLSearchParams(
    params,
  )}`,
  {
    method,
    body,
    ...rest,
  },
)
  .then(res => res.json())
  .then(
    ({
      message: {
        header: { status_code },
        body: resBody,
      },
    }) => {
      if (status_code >= 400) throw new Error(status_code);
      return resBody;
    },
  );

export const client = {
  get: (path, params, ...rest) => baseFetch(path, 'GET', params, null, ...rest),
};
