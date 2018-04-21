/**
 * @method fetchHandler
 * @description Extract json from HTTP request
 */
const fetchHandler = promise => promise
  .then(response => response.json());

/**
 * @method requiredParam
 * @description Throw an error if a required param is not provided
 * @tutorial https://medium.freecodecamp.org/elegant-patterns-in-modern-javascript-roro-be01e7669cbd
 */
const requiredParam = param => {
  const requiredParamError = new Error(
    `Required parameter, "${param}" is missing.`
  );
  if (typeof Error.captureStackTrace === 'function') {
    Error.captureStackTrace(
      requiredParamError, 
      requiredParam
    );
  }
  throw requiredParamError;
};

/**
 * @method serializeUrlParams
 * @description Throw an error if a required param is not provided
 */
const serializeUrlParams = params => Object.entries(params)
  .map(([key, val]) => `${key}=${val}`)
  .join('&');

export {fetchHandler};
export {requiredParam};
export {serializeUrlParams};