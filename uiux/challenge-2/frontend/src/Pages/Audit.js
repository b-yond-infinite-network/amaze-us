import React from "react";
import AuditBabyRequests from "../Components/AuditBabyRequests";

function Audit() {
  return (
    <div className="content">
      <h1>Audit the processed Baby requests</h1>
      <p>
        Look through the processed approved requests. You may use the search bar for better results:
      </p>
      <AuditBabyRequests/>
    </div>
  );
}

export default Audit;
