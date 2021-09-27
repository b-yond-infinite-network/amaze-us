import React from "react";
import { Redirect } from "react-router-dom";

export const MainPage = () => {
  console.log(localStorage.getItem("isAuthenticated"))
  const isAuthenticated = false;
  const userRole = "ROLE_MANAGER";

  if (!isAuthenticated) return <Redirect to="/login" />;

  if (userRole === "ROLE_MANAGER") return <ManagerView />;
  else if (userRole === "ROLE_EMPLOLYEE") return <EmployeeView />;

  return (
    <div>
      <ManagerView />
      <EmployeeView />
    </div>
  );
};

export const EmployeeView = () => {
  return <div>Welcome to Employee View</div>;
};

export const ManagerView = () => {
  return (
    <div>
      <h1 className="h1">Welcome to Manager View</h1>
      <DriverCRUD />
    </div>
  );
};

export const DriverCRUD = () => {
  return <div>Welcome to Manager View</div>;
};
