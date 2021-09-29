import { sendPost } from "../../utils/api";
import { LoginRequest, LoginResponse } from "./models";

export function login(request: LoginRequest) : Promise<LoginResponse> {
  return sendPost<LoginResponse>('/auth/login', request);
}
