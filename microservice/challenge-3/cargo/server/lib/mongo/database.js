const { MongoClient } = require('mongodb')
const config = {
  url: 'mongodb://' + process.env.HOST + ':27017/',
  db: process.env.DB_NAME
}
module.exports = ({ _MongoClient = MongoClient } = {}) => {
  const state = {
    client: null,
    db: null
  }

  return {
    connect: async ({ _config = config } = {}) => {
      const { url, db } = _config
      state.client = await _MongoClient.connect(url, {
        useNewUrlParser: true,
        useUnifiedTopology: true
      })
      state.db = state.client.db(db)
      return state.db
    },
    close: async () => {
      state.client.close()
      state.client = null
      state.db = null
    }
  }
}
