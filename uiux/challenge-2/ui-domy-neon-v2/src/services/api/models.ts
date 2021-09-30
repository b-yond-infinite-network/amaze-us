export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  access_token: string;
}

export interface NewBirthRequest {
  plannedForYear: number;
  notes?: string;
};

export interface ApiValidationError {
  msg: string;
  param: string;
  value: string;
}

export interface GenericErrorResponse {
  error: string;
}

export interface ValidationErrorResponse {
  errors: ApiValidationError[];
}

export class ValidationError extends Error {
  constructor(
    message: string,
    public readonly errors: ApiValidationError[]) {
    super(message);
  }
}
