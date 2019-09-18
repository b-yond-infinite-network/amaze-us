const Joi = require("@hapi/joi");
const { ObjectID } = require("mongodb");

const cargoSchema = require("./schema.js");
const mongo = require("../../lib/mongo/database.js");

module.exports = ({
  _Joi = Joi,
  _mongo = mongo.connect({}),
  _cargoSchema = cargoSchema
} = {}) => {
  const collections = _mongo
    .then(connection => [connection.collection("todos")])
    .catch(error => error);
  return {
    /**
     * @returns list of todo items
     */
    async getAll() {
      const [todosCollection] = await collections;
      const todos = await todosCollection.find().toArray();
      return { todos };
    },
    /**
     * Adds an item to the todo list
     * @param {*} [{ text }={}] string todo item
     * @returns list of updated todo items
     */
    async add({ text } = {}) {
      const [todosCollection] = await collections;
      const loaded = false;
      const { error, value: validatedInput } = _Joi.validate(
        { text, loaded },
        _cargoSchema.cargo
      );
      if (!error) {
        await todosCollection.insertOne(validatedInput);
        return await this.getAll();
      }
      return error.details;
    },
    /**
     *  Removes todo item from database
     * @param {*} [{ cargoId }={}] string id of the item to be removed
     * @returns list of updated todo items after remove
     */
    async remove({ cargoId } = {}) {
      const [todosCollection] = await collections;
      await todosCollection.deleteOne({
        _id: ObjectID(cargoId)
      });
      return await this.getAll();
    }
  };
};
