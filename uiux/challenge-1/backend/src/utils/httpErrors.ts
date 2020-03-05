export abstract class HTTPError extends Error {
  readonly statusCode!: number;
  readonly errorMessage!: string | object;

  constructor(errorMessage: string | object) {
    if (errorMessage instanceof String) {
      super(errorMessage as string);
    } else {
      super(JSON.stringify(errorMessage));
    }
    this.name = this.constructor.name;
    Error.captureStackTrace(this, this.constructor);
  }
}

export abstract class HTTPClientError extends HTTPError {
  constructor(errorMessage: string | object) {
    super(errorMessage);
  }
}

export abstract class HTTPServerError extends HTTPError {
  constructor(errorMessage: string | object) {
    super(errorMessage);
  }
}

//--------------------------
// To be used by controllers
export class HTTP404Error extends HTTPClientError {
  readonly statusCode = 404;
  constructor(errorMessage: string | object = "Resource not found") {
    super(errorMessage);
  }
}

export class HTTP400Error extends HTTPClientError {
  readonly statusCode = 400;
  constructor(errorMessage: string | object = "Bad request") {
    super(errorMessage);
  }
}

export class HTTP500Error extends HTTPServerError {
  readonly statusCode = 500;
  constructor(errorMessage: string | object = "Internal error") {
    super(errorMessage);
  }
}
