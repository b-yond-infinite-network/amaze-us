const request = require("supertest");
const app = require("../app.js");
const Bus = require("../models").Bus;
const Driver = require("../models").Driver;
const Schedule = require("../models").Schedule;
const { uuid } = require("uuidv4");
const { faker } = require("@faker-js/faker");
const Helper = require("../utils/helper");
const helper = new Helper();
var Chance = require("chance");
var chance = new Chance();
describe("Buses Endpoints", () => {
  const path = "/api/v1/schedules/";

  const modelInput = {
    from: "2022-07-20 07:52:49",
    to: "2022-07-20 11:52:49",
  };
  let managerToken = "";
  let empToken = "";
  const invalidToken = "invalidToken";
  let testDriver = {};
  let testBus = {};
  let testSchedule = {};
  beforeAll(async () => {
    const managerInput = {
      username: "manager@test.com",
      password: "manager@123",
    };

    //create test Driver
    await Driver.create({
      first_name: faker.name.firstName(),
      last_name: faker.name.lastName(),
      email: uuid() + "test_driver@test.com",
      social_security_number: chance.ssn(),
    }).then((data) => {
      testDriver = data.dataValues;
      modelInput.driver_id = testDriver.id;
    });

    //create test Bus
    await Bus.create({
      capacity: helper.rand(20, 30), //[20,30]
      model: faker.vehicle.manufacturer(),
      make: "2017",
      associated_driver_id: testDriver.id,
    }).then((data) => {
      testBus = data.dataValues;
      modelInput.bus_id = testBus.id;
    });

  //create test Schedule
  await Schedule.create({
    driver_id: testDriver.id,
    bus_id: testBus.id,
    from: "2022-07-21 07:52:49",
    to: "2022-07-21 11:52:49",
  }).then((data) => {
    testSchedule = data.dataValues;
  });

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
  });

  it("/Post api/v1/schedules/add should add new schedule", async () => {
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

  it("/Post api/v1/schedules/add should throw exception Forbidden", async () => {
    const res = await request(app)
      .post(`${path}add`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });

  it("/Put api/v1/schedules/:id should update the schedule", async () => {
    modelInput.from = "2022-07-20 06:52:49";
  
    const res = await request(app)
      .put(`${path}${testBus.id}`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(200);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("updated");
  });

  it("/Put api/v1/schedules/:id should throw exception Forbidden ", async () => {
    const res = await request(app)
      .put(`${path}${testBus.id}`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send(modelInput);

    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });

  it("/Delete api/v1/schedules/:id should delete the schedule", async () => {
    const res = await request(app)
      .delete(`${path}${testBus.id}`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send();

    expect(res.statusCode).toEqual(200);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("deleted");
  });

  it("/Delete api/v1/schedules/:id should return schedule not found", async () => {
    const res = await request(app)
      .delete(`${path}-1`)
      .set({ Authorization: managerToken, Accept: "application/json" })
      .send();
    expect(res.statusCode).toEqual(404);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Schedule not found");
  });
  it("/Delete api/v1/schedules/:id should throw exception Forbidden", async () => {
    const res = await request(app)
      .delete(`${path}-1`)
      .set({ Authorization: empToken, Accept: "application/json" })
      .send();
    expect(res.statusCode).toEqual(403);
    expect(res.body).toHaveProperty("message");
    expect(res.body.message).toEqual("Forbidden");
  });
});
