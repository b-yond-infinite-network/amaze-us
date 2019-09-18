const { MongoClient } = require("mongodb");
const config = {
  url: "mongodb://" + process.env.HOST + ":27017/",
  db: process.env.DB_NAME
};
const state = {
  client: null,
  db: null
};
/**
 *
 * Handles connection to the databse
 * @param {*} [{ _config = config }={}]
 * @returns A connection to db
 */
const connect = async function({ _config = config } = {}) {
  const { url, db } = _config;
  if (state.db) return state.db;

  state.client = await MongoClient.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true
  });
  state.db = state.client.db(db);
  return state.db;
};
module.exports = {
  connect,
  /**
   * Closes the connection to the db
   */
  close: () => {
    state.client.close();
    state.client = null;
    state.db = null;
  }
};
