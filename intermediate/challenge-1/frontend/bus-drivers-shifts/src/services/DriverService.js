import axios from "axios";
import btoa from "btoa-lite";

const REST_API_URL = "http://localhost:8080/bus-drivers-shifts/api/driver/";

class DriverService {
  // get driver by SSN
  getDriverBySSN = async ssn => {
    return await axios
      .get(REST_API_URL + ssn)
      .then(response => console.log(response.data))
      .catch(err => console.log(err));
  };

  // get all drivers
  getAllDrivers = async () => {
    var username = "admin";
    var password = "admin";
    // var basicAuth = 'Basic ' + btoa(username + ':' + password);
    axios
      .get(
        REST_API_URL + "all",
        {},
        {
          auth: {
            username: username,
            password: password
          }
        }
      )
      .then(response => {
        console.log("++++");
        console.log(response);
        console.log("----");
        console.log(response.data);
        console.log("====");
        console.log(JSON.stringify(response.data));
        return JSON.stringify(response.data);
      })
      .catch(err => console.log(err));
  };

  // create/update driver
  saveOrUpdateDriver = async driver => {
    await axios
      .post(REST_API_URL, {
        driver: driver
      })
      .then(response => console.log(response.data))
      .catch(err => console.log(err));
  };

  // delete driver by ID
  deleteDriverBySSN = async ssn => {
    return await axios
      .delete(REST_API_URL + ssn)
      .then(response => console.log(response.data))
      .catch(err => console.log(err));
  };
}

export default new DriverService();
