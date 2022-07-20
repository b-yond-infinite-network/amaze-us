from typing import Optional

from pydantic import BaseModel


class Token(BaseModel):
    username: Optional[str] = None
