import React, { useEffect } from "react";
import { RouteComponentProps } from "@reach/router";
import { fetchArtist } from "../shared/api/artist";

const Home: React.FC<RouteComponentProps> = () => {
  useEffect(() => {
    fetchArtist("interpol").then(r => console.log("HOME OAGE: ", r));
  });

  return (
    <main>
      <h1>Hello world</h1>
    </main>
  );
};

export default Home;
