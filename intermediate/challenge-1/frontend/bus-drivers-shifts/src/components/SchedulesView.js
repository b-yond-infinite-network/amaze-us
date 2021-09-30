import React, { useState, useEffect } from "react";
import moment from "moment";

import driverService from "../services/DriverService";
import busService from "../services/BusService";
import scheduleService from "../services/ScheduleService";

export const SchedulesView = ({ filterBy }) => {
  const today = moment();
  const [date, setDate] = useState(today.format("YYYY-MM-DD"));
  const [schedules, setSchedules] = useState([]);
  const [busID, setBusID] = useState();
  const [driverSSN, setDriverSSN] = useState("");
  const [drivers, setDrivers] = useState([]);
  const [buses, setBuses] = useState([]);

  const loadAllDrivers = () => {
    driverService.getAllDrivers().then(response => {
      setDrivers(
        response.data.map(driver => {
          return {
            ssn: driver.ssn,
            name: `${driver.firstName} ${driver.lastName} (ssn: ${driver.ssn})`
          };
        })
      );
    });
  };
  const loadAllBuses = () => {
    busService.getAllBuses().then(response => {
      setBuses(
        response.data.map(bus => {
          return {
            id: bus.id,
            name: `${bus.id} - ${bus.model} : ${bus.make}`
          };
        })
      );
    });
  };

  useEffect(() => {
    if (filterBy === "driver") {
      loadAllDrivers();
    } else {
      loadAllBuses();
    }
  }, [filterBy]);

  const rows = schedules.map(s => {
    var day = moment(s.day, "YYYY-MM-DD");
    day.add(1, "days");
    return (
      <tr key={s.id}>
        <td>{s.id}</td>
        <td>{day.format("YYYY-MM-DD")}</td>
        <td>{s.timeFrom}</td>
        <td>{s.timeTo}</td>
        <td>{s.busID}</td>
        <td>{s.driverSSN}</td>
      </tr>
    );
  });
  return (
    <div>
      <h5 className="h5">
        <u>
          Filter Schedules{" "}
          {filterBy === "driver"
            ? "by Driver"
            : filterBy === "bus"
            ? "by Bus"
            : ""}
          :
        </u>
      </h5>

      <div className="form-group">
        <label htmlFor="day">
          Date: <span> </span>
          <span className="text-primary">
            (Select <strong>Date</strong> to search schedules in the week after
            selected date)
          </span>
        </label>
        <div className="col-3">
          <input
            id="day"
            type="date"
            className="form-control"
            placeholder="Date"
            value={date}
            onChange={e => setDate(e.target.value)}
          />
        </div>
      </div>

      {filterBy === "bus" && (
        <div className="form-group">
          <label htmlFor="busID">Bus:</label>
          <div className="col-3">
            <select
              id="busID"
              className="form-control"
              value={busID}
              onChange={e => {
                setBusID(e.target.value);
              }}
            >
              <option value="NOT_SELECTED">-- Select Bus --</option>
              {buses.map(b => {
                return <option value={b.id} key={b.id}>{b.name}</option>;
              })}
            </select>
          </div>
        </div>
      )}

      {filterBy === "driver" && (
        <div className="form-group">
          <label htmlFor="driverSSN">Driver:</label>
          <div className="col-3">
            <select
              id="driverSSN"
              className="form-control"
              value={driverSSN}
              onChange={e => {
                setDriverSSN(e.target.value);
              }}
            >
              <option value="NOT_SELECTED">-- Select Driver --</option>
              {drivers.map(d => {
                return <option value={d.ssn}  key={d.ssn}>{d.name}</option>;
              })}
            </select>
          </div>
        </div>
      )}

      <button
        className="btn btn-sm btn-primary mt-2"
        onClick={() => {
          if (filterBy === "driver") {
            let dssn = driverSSN;
            if(!driverSSN || driverSSN === "NOT_SELECTED"){
              dssn = ""
            }
            scheduleService.getWeeklyScheduleByDriverSSN(dssn, date).then(response => {
              setSchedules(response.data);
            });

            loadAllDrivers();
          } else {
            let bid = busID;
            if(!busID || busID === "NOT_SELECTED"){
              bid = -1;
            }
            scheduleService.getWeeklyScheduleByBusID(bid, date).then(response => {
              setSchedules(response.data);
            });

            loadAllBuses();
          }
          // scheduleService.getWeeklySchedules(date).then(response => {
          //   setSchedules(response.data);
          // });
        }}
      >
        Filter Schedules
      </button>

      {rows.length > 0 && (
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Day</th>
              <th>From</th>
              <th>To</th>
              <th>Bus ID</th>
              <th>Driver SSN</th>
            </tr>
          </thead>
          <tbody>{rows}</tbody>
        </table>
      )}
    </div>
  );
};
