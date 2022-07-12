const request = require("supertest");
const app = require("../app.js");
const User = require("../models").User;
const Role = require("../models").Role;
const { faker } = require("@faker-js/faker");
var Chance = require("chance");
var chance = new Chance();
describe("Users Endpoints", () => {
  const path = "/api/v1/users/";

  const modelInput = {
    username: "test_user_1@test.com",
    password: "test@123_!",
    fullname: "Test User",

  };
  let managerToken = "";
  let empToken = "";
  const invalidToken = "invalidToken";
  let testUser = {};
  beforeAll(async () => {

    //role 
    const role=await Role.findOne({
      where: {
        role_name: "employee",
      },
    })
    modelInput.role_id=role.id ;


    const managerInput = {
      username: "manager@test.com",
      password: "manager@123",
    };
    const managerLogin = await request(app)
      .post("/api/v1/auth/signin")
      .send(managerInput);
    managerToken = managerLogin.body.token;

    const empInput = {
      username: "employee@test.com",
      password: "employee@123",
    };
    const empLogin = await request(app)
      .post("/api/v1/auth/signin")
      .send(empInput);
    empToken = empLogin.body.token;

    //create test User
 
    await User.create({
      username: "test_user@test.com",
      password: "test@123",
      fullname: "Test User",
      role_id: role.id,
    }).then((data) => {
      testUser = data.dataValues;
    });
  });

  it("/Post api/v1/users/add should add new user", async () => {
    const res = await request(app)
      .post(`${path}add`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(201);
    expect(res.body).toHaveProperty("id");
   
  });

  it("/Post api/v1/users/add should throw exception Forbidden", async () => {
    const res = await request(app)
      .post(`${path}add`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });

  it("/Put api/v1/users/:id should update the user", async () => {
    modelInput.fullname = "new fullname";
    const res = await request(app)
      .put(`${path}${testUser.id}`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(200);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("updated");
  });

  it("/Put api/v1/divers/:id should throw exception Forbidden ", async () => {
    const res = await request(app)
      .put(`${path}${testUser.id}`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });

  it("/Delete api/v1/dirvers/:id should delete the user", async () => {
    const res = await request(app)
      .delete(`${path}${testUser.id}`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send();

    expect(res.statusCode).toEqual(200);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("deleted");
  });

  it("/Delete api/v1/dirvers/:id should return user not found", async () => {
    const res = await request(app)
      .delete(`${path}-1`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send();
    expect(res.statusCode).toEqual(404);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("User not found");
  });
  it("/Delete api/v1/dirvers/:id should throw exception Forbidden", async () => {
    const res = await request(app)
      .delete(`${path}-1`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send();
    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });
});
