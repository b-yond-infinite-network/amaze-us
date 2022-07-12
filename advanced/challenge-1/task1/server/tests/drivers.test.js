const request = require("supertest");
const app = require("../app.js");
const Driver = require("../models").Driver;
const { faker } = require("@faker-js/faker");
var Chance = require("chance");
var chance = new Chance();
describe("Drivers Endpoints", () => {
  const path = "/api/v1/drivers/";

  const modelInput = {
    first_name: faker.name.firstName(),
    last_name: faker.name.lastName(),
    email: "test-email@test.com",
    social_security_number: chance.ssn(),
  };
  let managerToken = "";
  let empToken = "";
  const invalidToken = "invalidToken";
  let testDriver = {};
  beforeAll(async () => {
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

    //create test Driver
    await Driver.create({
      first_name: faker.name.firstName(),
      last_name: faker.name.lastName(),
      email: "myemail@test.com",
      social_security_number: chance.ssn(),
    }).then((data) => {
      testDriver = data.dataValues;
    });
  });

  it("/Post api/v1/drivers/add should add new driver", async () => {
    const res = await request(app)
      .post(`${path}add`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(201);

    for (const property in modelInput) {
      expect(res.body).toHaveProperty(property);
      expect(res.body[property]).toEqual(modelInput[property]);
    }
  });

  it("/Post api/v1/drivers/add should throw exception Forbidden", async () => {
    const res = await request(app)
      .post(`${path}add`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });

  it("/Put api/v1/drivers/:id should update the driver", async () => {
    modelInput.email = "newEmail@test.com";
    modelInput.social_security_number = "123-123-12321";

    const res = await request(app)
      .put(`${path}${testDriver.id}`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(200);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("updated");
  });

  it("/Put api/v1/divers/:id should throw exception Forbidden ", async () => {
    const res = await request(app)
      .put(`${path}${testDriver.id}`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });

  it("/Delete api/v1/dirvers/:id should delete the driver", async () => {
    const res = await request(app)
      .delete(`${path}${testDriver.id}`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send();

    expect(res.statusCode).toEqual(200);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("deleted");
  });

  it("/Delete api/v1/dirvers/:id should return driver not found", async () => {
    const res = await request(app)
      .delete(`${path}-1`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send();
    expect(res.statusCode).toEqual(404);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Driver not found");
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
