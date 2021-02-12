import React from "react";
import PopulationCount from "../Components/PopulationCount";
import PostBabyRequests from "../Components/PostBabyRequests";


function Pioneers() {
  return (
    <div className="content">
      <h1>Pioneers' Page</h1>
      <p> All the information you need about the health and survival of the colony.</p>
      <PopulationCount/>
      <p>
        Have you finally decided to educate your own baby to become a proper pioneer in our Colony?
        Then make the request now! Just fill up the name of the baby you would like and send the request.
        Shall your request be approved, the baby will be delivered shortly and you will be able to appreciate the joy of
        parenthood!
      </p>
      <PostBabyRequests/>
    </div>
  );
}

export default Pioneers;
