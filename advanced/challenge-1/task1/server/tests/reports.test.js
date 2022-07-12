const request = require("supertest");
const app = require("../app.js");
const Bus = require("../models").Bus;
const Driver = require("../models").Driver;
const Schedule = require("../models").Schedule;
const data = require('./data.json');
const { uuid } = require("uuidv4");
const { faker } = require("@faker-js/faker");
const Helper = require("../utils/helper");
const helper = new Helper();
var Chance = require("chance");
var chance = new Chance();
describe("Buses Endpoints", () => {
  const path = "/api/v1/schedules/";
  const schedulesData = [];
  const modelInput = {
    from: "2022-07-20 07:52:49",
    to: "2022-07-20 11:52:49",
  };

  let empToken = "";
  const invalidToken = "invalidToken";
  let testDriver = {};
  let testBus = {};

  beforeAll(async () => {
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

    const empInput = {
      username: "employee@test.com",
      password: "employee@123",
    };
    const empLogin = await request(app)
      .post("/api/v1/auth/signin")
      .send(empInput);

    empToken = empLogin.body.token;

    for (const element of data.schedules) {
      Schedule.create({
        from: element.from,
        to: element.to,
        driver_id: testDriver.id,
        bus_id: testBus.id,
      }).then((data) => {
        schedulesData.push(data.dataValues);
      });
    }
  });

  it("/Get api/v1/schedules/top_drivers_schedules should return top X drivers schedules", async () => {
    const res = await request(app)
      .get(
        `${path}top_drivers_schedules?top=2&from=2020-05-26 08:00:43&to=2020-07-26 08:00:43`
      )
      .set({ Authorization: empToken, Accept: "application/json" })
      .send();

    expect(res.statusCode).toEqual(200);
    expect(res.body.length).toEqual(9);
    const outputSample =
      '{"from":"2020-05-26T04:00:43.000Z","to":"2020-06-02T04:00:43.000Z","data":[{"scheduls_count":2,"driver":{"id":11,"first_name":"Mozelle","last_name":"Veum","social_security_number":"225-46-2851","email":"6fb4ac43-e852-426f-b226-04f25bbf6f15test_driver@test.com","createdAt":"2022-07-11T19:13:07.000Z","updatedAt":"2022-07-11T19:13:07.000Z","deletedAt":null}}]}';
    const outputObject = JSON.parse(outputSample);
    for (const property in outputObject) {
      expect(res.body[0]).toHaveProperty(property);
    }
    expect(res.body[0].from).toEqual(outputObject.from);
    expect(res.body[0].to).toEqual(outputObject.to);
    expect(res.body[0].data[0].scheduls_count).toEqual(
      outputObject.data[0].scheduls_count
    );
  });

  it("/Get api/v1/schedules/driver_schedules should return drivers schedules", async () => {
    const res = await request(app)
      .get(
        `${path}driver_schedules?from=2020-05-26 08:00:43&to=2020-07-26 08:00:43`
      )
      .set({ Authorization: empToken, Accept: "application/json" })
      .send();

    expect(res.statusCode).toEqual(200);
    expect(res.body.length).toEqual(1);
    expect(res.body[0]).toHaveProperty("id");
    expect(res.body[0]).toHaveProperty("first_name");
    expect(res.body[0]).toHaveProperty("last_name");
    expect(res.body[0]).toHaveProperty("social_security_number");
    expect(res.body[0]).toHaveProperty("email");
    expect(res.body[0]).toHaveProperty("schedules");
    expect(res.body[0].schedules.length).toEqual(5);
  });


  it("/Get api/v1/schedules/bus_schedules should return buses schedules", async () => {
    const res = await request(app)
      .get(
        `${path}bus_schedules?from=2020-05-26 08:00:43&to=2020-07-26 08:00:43`
      )
      .set({ Authorization: empToken, Accept: "application/json" })
      .send();

    expect(res.statusCode).toEqual(200);
    expect(res.body.length).toEqual(1);
    expect(res.body[0]).toHaveProperty("id");
    expect(res.body[0]).toHaveProperty("capacity");
    expect(res.body[0]).toHaveProperty("model");
    expect(res.body[0]).toHaveProperty("make");
    expect(res.body[0]).toHaveProperty("associated_driver_id");
    expect(res.body[0]).toHaveProperty("schedules");
    expect(res.body[0].schedules.length).toEqual(5);
  });
});
