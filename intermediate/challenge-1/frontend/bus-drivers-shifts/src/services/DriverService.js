import axios from "axios";

const REST_API_URL = "http://localhost:8080/bus-drivers-shifts/api/driver/";

class DriverService {
  // get driver by SSN
  getDriverBySSN = ssn => {
    return axios.get(REST_API_URL + ssn);
  };

  // get all drivers
  getAllDrivers = () => {
    return axios.get(REST_API_URL + "all");
  };

  // create/update driver
  saveOrUpdateDriver = (driver) => {
    return axios.post(REST_API_URL, {
      ssn: driver.ssn,
      firstName: driver.firstName,
      lastName: driver.lastName
    });
  };

  // delete driver by ID
  deleteDriverBySSN = ssn => {
    return axios.delete(REST_API_URL + ssn);
  };
}

export default new DriverService();
