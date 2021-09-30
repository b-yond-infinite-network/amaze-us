import axios from "axios";

const REST_API_URL = "http://localhost:8080/bus-drivers-shifts/api/bus/";

class BusService {
  // get Bus by ID
  getBusByID = id => {
    return axios.get(REST_API_URL + id);
  };

  // get all Buses
  getAllBuses = () => {
    return axios.get(REST_API_URL + "all");
  };

  // create/update Bus
  saveOrUpdateBus = bus => {
    return axios.post(REST_API_URL, {
      id: bus.id,
      capacity: bus.capacity,
      model: bus.model,
      make: bus.make,
      driverSSN: bus.driverSSN
    });
  };

  // delete Bus by ID
  deleteBusByID = id => {
    return axios.delete(REST_API_URL + id);
  };
}

export default new BusService();
