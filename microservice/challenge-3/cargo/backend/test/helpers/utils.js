module.exports = {
  createResponseObject: (stub) => {
    return {
      json: stub,
      status: stub,
      send: stub,
      end: stub,
    };
  },
};