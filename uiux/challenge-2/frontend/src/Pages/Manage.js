import React from "react";
import ManageBabyRequests from "../Components/ManageBabyRequests";

function Manage() {
  return (
    <div className="content">
      <h1>Review the Baby requests</h1>
      <p>
        This is the Habitat and Survival Management Page, be wary of the future of the colony.
        We cannot overgrow the population and we cannot allow random names for our future generations.
      </p>
      <p>New requests:</p>
      <ManageBabyRequests/>
    </div>
  );
}

export default Manage;
