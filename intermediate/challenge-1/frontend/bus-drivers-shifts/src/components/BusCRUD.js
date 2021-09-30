import React, { useState, useEffect } from "react";
import { Modal, Box } from "@material-ui/core/";
import driverService from "../services/DriverService";
import busService from "../services/BusService";

export const BusCRUD = () => {
  const [busID, setBusID] = useState();
  const [capacity, setCapacity] = useState();
  const [model, setModel] = useState("");
  const [make, setMake] = useState("");
  const [ssn, setSsn] = useState("");
  const [buses, setBuses] = useState([]);
  const [drivers, setDrivers] = useState([]);
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
    busService.getAllBuses().then(response => {
      setBuses(response.data);
    });
  }, [buses]);

  const rows = buses.map(b => {
    return (
      <tr key={b.id}>
        <td>{b.id}</td>
        <td>{b.capacity}</td>
        <td>{b.model}</td>
        <td>{b.make}</td>
        <td>{b.driverSSN}</td>
        <td>
          <button
            className="btn btn-sm btn-warning m-1"
            onClick={() => {
              setBusID(b.id);
              setCapacity(b.capacity);
              setModel(b.model);
              setMake(b.make);
              setSsn(b.driverSSN);
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

              handleOpen();
            }}
          >
            Edit
          </button>
          <button
            className="btn btn-sm btn-danger"
            onClick={() => {
              busService.deleteBusByID(b.id).then(response => {
                alert("Bus deleted successfully");
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
        <u>Buses:</u>
      </h5>
      <button
        className="btn btn-success btn-sm"
        onClick={() => {
          setBusID("");
          setCapacity("");
          setMake("");
          setModel("");
          setSsn("");
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
          handleOpen();
        }}
      >
        Add new Bus
      </button>
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-labelledby="child-modal-title"
        aria-describedby="child-modal-description"
      >
        <Box sx={{ ...style, width: 400 }}>
          {!edit && <h4 className="h4">Add new Bus</h4>}
          {edit && <h4 className="h4">Edit Bus</h4>}
          <span className="text-danger mt-2">{message}</span>

          {edit && (
            <div className="form-group">
              <label htmlFor="busID">Bus ID:</label>
              <input
                id="busID"
                disabled={edit}
                type="number"
                // maxLength={50}
                className="form-control"
                placeholder="Bus ID"
                value={busID}
                onChange={e => {
                  setBusID(e.target.value);
                }}
              />
            </div>
          )}

          <div className="form-group">
            <label htmlFor="capacity">Capacity:</label>
            <input
              id="capacity"
              type="number"
              // maxLength={25}
              className="form-control"
              placeholder="Capacity"
              value={capacity}
              onChange={e => {
                setCapacity(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="model">Model:</label>
            <input
              id="model"
              type="text"
              maxLength={25}
              className="form-control"
              placeholder="Model"
              value={model}
              onChange={e => {
                setModel(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="make">Make:</label>
            <input
              id="make"
              type="text"
              maxLength={25}
              className="form-control"
              placeholder="Make"
              value={make}
              onChange={e => {
                setMake(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="driverSSN">Associated Driver SSN:</label>
            <select
              id="driverSSN"
              class="form-control"
              value={ssn}
              onChange={e => {
                setSsn(e.target.value);
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
                !capacity ||
                !model ||
                !make ||
                !ssn ||
                ssn === "NOT_SELECTED"
              ) {
                setMessage("All field are required!");
              } else {
                const bus = {
                  id: busID,
                  capacity: capacity,
                  model: model,
                  make: make,
                  driverSSN: ssn
                };
                busService.saveOrUpdateBus(bus).then(resp => {
                  let alertMessage = !edit
                    ? "Bus added successfully!"
                    : "Bus updated successfully";
                  alert(alertMessage);
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
            <th>Bus ID</th>
            <th>Capacity</th>
            <th>Model</th>
            <th>Make</th>
            <th>Associated Driver</th>
            <th>Operations</th>
          </tr>
        </thead>
        <tbody>{rows}</tbody>
      </table>
    </div>
  );
};
