import { ACCESS_TOKEN_STORAGE_KEY } from "./constants";
import { ValidationError, ValidationErrorResponse } from "../services/api/models";

export function isValidationErrorResponse(response: any) : response is ValidationErrorResponse {
  return response.errors !== undefined;
}

export function buildRequestHeaders(extraHeaders: Record<string, string> = {}) : Record<string, string> {
  const defaultHeaders: Record<string, string> = {
    'Content-Type': 'application/json',
  };
  const accessToken = localStorage.getItem(ACCESS_TOKEN_STORAGE_KEY);
  if (accessToken) {
    defaultHeaders['Authorization'] = `Bearer ${accessToken}`;
  }

  return { ...defaultHeaders, ...extraHeaders };
}

async function sendRequestWithBody<TResponse>(
  url: string,
  method: 'POST' | 'PUT' | 'PATCH' | 'DELETE' | 'GET',
  reqConfig: { body?: any, extraHeaders?: Record<string, string> } = {}
  ) : Promise<TResponse> {
  const response = await fetch(url, {
    method: method,
    body: reqConfig.body ? JSON.stringify(reqConfig.body) : undefined,
    headers: buildRequestHeaders(reqConfig.extraHeaders || {}),
  });

  if (response.status >= 200 && response.status < 300) {
    if (!response.body) {
      return Promise.resolve<any>(null);
    }

    const bodyContent = await response.json();
    if (bodyContent.error) {
      throw new Error(bodyContent.error);
    }

    return bodyContent;
  }

  const responseBody = await response.json();
  if (response.status === 400 && isValidationErrorResponse(responseBody)) {
    throw new ValidationError('Api validation error', responseBody.errors);
  }

  throw new Error(responseBody.error);
}

export async function sendGet<TResponse>(url: string) : Promise<TResponse> {
  return sendRequestWithBody(url, 'GET');
}

export async function sendPost<TResponse>(
  url: string,
  reqBody: any,
  extraHeaders: Record<string, string> = {}) : Promise<TResponse> {
  return sendRequestWithBody(url, 'POST', {
    body: reqBody,
    extraHeaders
  });
}

export async function sendPut<TResponse>(
  url: string,
  reqBody: any,
  extraHeaders: Record<string, string> = {}
  ) : Promise<TResponse> {
  return sendRequestWithBody(url, 'PUT', {
    body: reqBody,
    extraHeaders
  });
}
