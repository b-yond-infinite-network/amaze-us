import React, { useEffect } from "react";
import { Redirect } from "react-router-dom";

import { SchedulesView } from "./SchedulesView";
import { DriverCRUD } from "./DriverCRUD";
import { BusCRUD } from "./BusCRUD";
import { ScheduleCRUD } from "./ScheduleCRUD";

import userService from "../services/UserService";

export const MainPage = () => {
  useEffect(() => {
    document.title = "Bus Drivers Shifts - Main Page";
  }, []);

  let user = userService.getCurrentUser();
  const isAuthenticated = user ? true : false;

  const isManager =
    user && user.roles && user.roles.includes("ROLE_MANAGER") ? true : false;
  const isEmployee =
    user && user.roles && user.roles.includes("ROLE_EMPLOYEE") ? true : false;

  if (!isAuthenticated) return <Redirect to="/login" />;

  return (
    <div className="container">
      {isManager && <ManagerView />}
      {isEmployee && <EmployeeView />}
    </div>
  );
};

export const EmployeeView = () => {
  return (
    <div>
      <h1 className="h1">Welcome to Employee View</h1>
      <SchedulesView filterBy="driver" />
      <hr />
      <SchedulesView filterBy="bus" />
      <hr />
    </div>
  );
};

export const ManagerView = () => {
  return (
    <div>
      <h1 className="h1">Welcome to Manager View</h1>
      <DriverCRUD />
      <BusCRUD />
      <ScheduleCRUD />
      <hr />
    </div>
  );
};
