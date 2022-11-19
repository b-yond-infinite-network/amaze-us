from distutils.util import strtobool

from fastapi import Request, HTTPException
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials

import json
import jwt
import os
import time


class JWTBearer(HTTPBearer):
    def __init__(self, allowed_roles, auto_error: bool = True):
        super(JWTBearer, self).__init__(auto_error=auto_error)
        self.allowed_roles = allowed_roles

    async def __call__(self, request: Request):
        secure_api = strtobool(os.getenv('SECURE_API', 'False'))
        if not secure_api:
            return True

        credentials: HTTPAuthorizationCredentials = await super(JWTBearer, self).__call__(request)
        if credentials:
            if not credentials.scheme == "Bearer":
                raise HTTPException(
                    status_code=403, detail="Invalid authentication scheme.")
            
            decoded_token = self.decodeJWT(
                credentials.credentials.split('Bearer ')[-1])
            if not decoded_token:
                raise HTTPException(
                    status_code=403, detail="Invalid token or expired token.")

            if not any(role in decoded_token["roles"] for role in self.allowed_roles):
                raise HTTPException(
                    status_code=403, detail="Not enough permissions.")

            return credentials.credentials
        else:
            raise HTTPException(
                status_code=403, detail="Invalid authorization code.")

    def decodeJWT(self, token: str) -> dict | None:
        json_file = open('jwt.json')
        jwt_data = json.load(json_file)
        secret_key = jwt_data.get('secret')
        algorithm = jwt_data.get('algorithm')
        json_file.close()

        try:
            decoded_token = jwt.decode(
                token, secret_key, algorithms=[algorithm])
            return decoded_token if decoded_token["exp"] >= time.time() else None
        except:
            return None
