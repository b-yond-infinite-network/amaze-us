import React, { useState, useEffect } from "react";
import { Modal, Box } from "@material-ui/core/";
import driverService from "../services/DriverService";

export const DriverCRUD = () => {
  const [ssn, setSsn] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
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
    driverService.getAllDrivers().then(response => {
      setDrivers(response.data);
    });
  }, [drivers]);

  const rows = drivers.map(d => {
    return (
      <tr key={d.ssn}>
        <td>{d.ssn}</td>
        <td>{d.firstName}</td>
        <td>{d.lastName}</td>
        <td>
          <button
            className="btn btn-sm btn-warning m-1"
            onClick={() => {
              setSsn(d.ssn);
              setFirstName(d.firstName);
              setLastName(d.lastName);
              setMessage("");
              setEdit(true);
              handleOpen();
            }}
          >
            Edit
          </button>
          <button
            className="btn btn-sm btn-danger"
            onClick={() => {
              driverService.deleteDriverBySSN(d.ssn).then(response => {
                alert("Driver deleted successfully");
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
        <u>Drivers:</u>
      </h5>
      <button
        className="btn btn-success btn-sm"
        onClick={() => {
          setSsn("");
          setFirstName("");
          setLastName("");
          setMessage("");
          setEdit(false);
          handleOpen();
        }}
      >
        Add new Driver
      </button>
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-labelledby="child-modal-title"
        aria-describedby="child-modal-description"
      >
        <Box sx={{ ...style, width: 400 }}>
          {!edit && <h4 className="h4">Add new Driver</h4>}
          {edit && <h4 className="h4">Edit Driver</h4>}
          <span className="text-danger mt-2">{message}</span>

          <div className="form-group">
            <label htmlFor="ssn">SSN:</label>
            <input
              id="ssn"
              disabled={edit}
              type="text"
              maxLength={50}
              className="form-control"
              placeholder="SSN"
              value={ssn}
              onChange={e => {
                setSsn(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="firstName">First Name:</label>
            <input
              id="firstName"
              type="text"
              maxLength={25}
              className="form-control"
              placeholder="First Name"
              value={firstName}
              onChange={e => {
                setFirstName(e.target.value);
              }}
            />
          </div>

          <div className="form-group">
            <label htmlFor="lastName">Last Name:</label>
            <input
              id="lastName"
              type="text"
              maxLength={25}
              className="form-control"
              placeholder="Last Name"
              value={lastName}
              onChange={e => {
                setLastName(e.target.value);
              }}
            />
          </div>
          <button
            className="btn btn-success btn-sm m-2"
            onClick={() => {
              if (!ssn || !firstName || !lastName) {
                setMessage("All field are required!");
              } else {
                const driver = {
                  ssn: ssn,
                  firstName: firstName,
                  lastName: lastName
                };

                driverService.saveOrUpdateDriver(driver).then(resp => {
                  let alertMessage = !edit
                    ? "Driver added successfully!"
                    : "Driver updated successfully";
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
        <thead className="thead-dark">
          <tr>
            <th>SSN</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Operations</th>
          </tr>
        </thead>
        <tbody>{rows}</tbody>
      </table>
    </div>
  );
};
