import axios from "axios";

const REST_API_URL = "http://localhost:8080/bus-drivers-shifts/api/schedule/";

class ScheduleService {
  // get Schedule by ID
  getScheduleByBusID = busID => {
    return axios.get(REST_API_URL + `busID?busID=${busID}`);
  };

  // get Schedule by DriverSSN
  getScheduleByDriverSSN = driverSSN => {
    return axios.get(REST_API_URL + `driverSSN?driverSSN=${driverSSN}`);
  };

  // get Weekly Schedule by busID and Date
  getWeeklyScheduleByBusID = (busID, day) => {
    return axios.get(REST_API_URL + `busID/Weekly?busID=${busID}&day=${day}`);
  };

  // get Weekly Schedule by DriverSSN and Date
  getWeeklyScheduleByDriverSSN = (driverSSN, day) => {
    return axios.get(
      REST_API_URL + `driverSSN/Weekly?driverSSN=${driverSSN}&day=${day}`
    );
  };

  getWeeklySchedules = date => {
    return axios.get(REST_API_URL + `date/Weekly?date=${date}`);
  };

  // get Schedule by Id
  getScheduleByID = id => {
    return axios.get(REST_API_URL + id);
  };

  // get all Schedules
  getAllSchedules = () => {
    return axios.get(REST_API_URL + "all");
  };

  // create/update Schedule
  saveOrUpdateSchedule = schedule => {
    return axios.post(REST_API_URL, {
      id: schedule.id,
      busID: schedule.busID,
      driverSSN: schedule.driverSSN,
      day: schedule.day,
      timeFrom: schedule.timeFrom,
      timeTo: schedule.timeTo
    });
  };

  // delete Schedule by Id
  deleteScheduleByID = id => {
    return axios.delete(REST_API_URL + id);
  };
}

export default new ScheduleService();
