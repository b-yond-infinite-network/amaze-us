import React from "react";
import spinner from "./../../assets/spinner.gif";

export default () => {
  return (
    // <div class="wraper-loader">
    //   <RingLoader size={60} color={"#605664"} loading={isLoading} />
    // </div>
    <div>
      <img
        src={spinner}
        alt="Loading..."
        style={{ width: '200px', margin: ' 40px auto', display: 'block' }}
      />
    </div>
  );
};
