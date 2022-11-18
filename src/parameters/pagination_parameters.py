async def pagination_parameters(page: int = 1, size: int = 100):
    return {"page": page, "page_size": size}
