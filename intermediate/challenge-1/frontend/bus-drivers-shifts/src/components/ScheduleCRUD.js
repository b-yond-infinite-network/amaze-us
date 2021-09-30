import React, { useState, useEffect } from "react";
import { Modal, Box } from "@material-ui/core/";
import moment from "moment";

import driverService from "../services/DriverService";
import busService from "../services/BusService";
import scheduleService from "../services/ScheduleService";

export const ScheduleCRUD = () => {
  const [schedules, setSchedules] = useState([]);
  const [id, setID] = useState();
  const [day, setDay] = useState();
  const [timeFrom, setTimeFrom] = useState();
  const [timeTo, setTimeTo] = useState();
  const [busID, setBusID] = useState();
  const [driverSSN, setDriverSSN] = useState("");
  const [drivers, setDrivers] = useState([]);
  const [buses, setBuses] = useState([]);

  const [open, setOpen] = React.useState(false);
  const [message, setMessage] = useState("");
  const [edit, setEdit] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  useEffect(() => {
    scheduleService.getAllSchedules().then(response => {
      setSchedules(response.data);
    });
  }, [schedules]);

  const rows = schedules.map(s => {
    var day = moment(s.day, "YYYY-MM-DD");
    day.add("days", 1);
    var timeFrom = moment(s.timeFrom, "HH:mm:ss");
    var timeTo = moment(s.timeTo, "HH:mm:ss");

    return (
      <tr key={s.id}>
        <td>{s.id}</td>
        <td>{day.format("YYYY-MM-DD")}</td>
        <td>{timeFrom.format("h:mm A")}</td>
        <td>{timeTo.format("h:mm A")}</td>
        <td>{s.busID}</td>
        <td>{s.driverSSN}</td>
        <td>
          <button
            className="btn btn-sm btn-warning m-1"
            onClick={() => {
              setID(s.id);
              var day = moment(s.day, "YYYY-MM-DD");
              day.add(1, "days");
              setDay(day.format("YYYY-MM-DD"));
              setTimeFrom(s.timeFrom);
              setTimeTo(s.timeTo);
              setBusID(s.busID);
              setDriverSSN(s.driverSSN);
              setMessage("");
              setEdit(true);

              driverService.getAllDrivers().then(response => {
                setDrivers(
                  response.data.map(driver => {
                    return {
                      ssn: driver.ssn,
                      name: `${driver.firstName} ${driver.lastName}`
                    };
                  })
                );
              });

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
              handleOpen();
            }}
          >
            Edit
          </button>
          <button
            className="btn btn-sm btn-danger"
            onClick={() => {
              scheduleService.deleteScheduleByID(s.id).then(response => {
                alert("Schedule deleted successfully");
              });
            }}
          >
            Delete
          </button>
        </td>
      </tr>
    );
  });

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "background.paper",
    border: "2px solid #000",
    boxShadow: 24,
    pt: 2,
    px: 4,
    pb: 3
  };

  return (
    <div>
      <h5 className="h5">
        <u>Schedules:</u>
      </h5>

      <button
        className="btn btn-success btn-sm"
        onClick={() => {
          setID();
          setDay(moment().format("YYYY-MM-DD"));
          setTimeFrom(moment().format("H:mm:ss"));
          setTimeTo(moment().format("H:mm:ss"));
          setBusID("");
          setDriverSSN("");
          setMessage("");
          setEdit(false);

          driverService.getAllDrivers().then(response => {
            setDrivers(
              response.data.map(driver => {
                return {
                  ssn: driver.ssn,
                  name: `${driver.firstName} ${driver.lastName}`
                };
              })
            );
          });

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

          handleOpen();
        }}
      >
        Add new Schedule
      </button>
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-labelledby="child-modal-title"
        aria-describedby="child-modal-description"
      >
        <Box sx={{ ...style, width: 400 }}>
          {!edit && <h4 className="h4">Add new Schedule</h4>}
          {edit && <h4 className="h4">Edit Schedule</h4>}
          <span className="text-danger mt-2">{message}</span>

          {edit && (
            <div className="form-group">
              <label htmlFor="ID">Schedule ID:</label>
              <input
                id="ID"
                disabled={edit}
                type="number"
                // maxLength={50}
                className="form-control"
                placeholder="Schedule ID"
                value={id}
                onChange={e => {
                  setID(e.target.value);
                }}
              />
            </div>
          )}

          <div className="form-group">
            <label htmlFor="day">Day:</label>
            <input
              id="day"
              type="date"
              // maxLength={25}
              className="form-control"
              placeholder="Day"
              value={day}
              onChange={e => {
                setDay(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="timeFrom">Time from:</label>
            <input
              id="timeFrom"
              type="time"
              maxLength={25}
              className="form-control"
              placeholder="Time From"
              value={timeFrom}
              onChange={e => {
                setTimeFrom(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="timeTo">Time to:</label>
            <input
              id="timeTo"
              type="time"
              maxLength={25}
              className="form-control"
              placeholder="Time From"
              value={timeTo}
              onChange={e => {
                setTimeTo(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="busID">Bus:</label>
            <select
              id="busID"
              class="form-control"
              value={busID}
              onChange={e => {
                setBusID(e.target.value);
              }}
            >
              <option value="NOT_SELECTED">-- Select Bus --</option>
              {buses.map(b => {
                return <option value={b.id}>{b.name}</option>;
              })}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="driverSSN">Driver:</label>
            <select
              id="driverSSN"
              class="form-control"
              value={driverSSN}
              onChange={e => {
                setDriverSSN(e.target.value);
              }}
            >
              <option value="NOT_SELECTED">-- Select Driver --</option>
              {drivers.map(d => {
                return <option value={d.ssn}>{d.name}</option>;
              })}
            </select>
          </div>

          <button
            className="btn btn-success btn-sm m-2"
            onClick={() => {
              if (
                !day ||
                !timeFrom ||
                !timeTo ||
                !driverSSN ||
                !busID ||
                driverSSN === "NOT_SELECTED" ||
                busID === "NOT_SELECTED"
              ) {
                setMessage("All field are required!");
              } else {
                const schedule = {
                  id: id,
                  busID: busID,
                  driverSSN: driverSSN,
                  day: day,
                  timeFrom: timeFrom,
                  timeTo: timeTo
                };
                scheduleService
                  .saveOrUpdateSchedule(schedule)
                  .then(resp => {
                    let alertMessage = !edit
                      ? "Schedule added successfully!"
                      : "Schedule updated successfully";
                    alert(alertMessage);
                  })
                  .catch(err => {
                    alert(err);
                  });
                handleClose();
              }
            }}
          >
            Submit
          </button>
          <button className="btn btn-danger btn-sm" onClick={handleClose}>
            Close
          </button>
        </Box>
      </Modal>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Day</th>
            <th>From</th>
            <th>To</th>
            <th>Bus ID</th>
            <th>Driver SSN</th>
            <th>Operations</th>
          </tr>
        </thead>
        <tbody>{rows}</tbody>
      </table>
    </div>
  );
};
