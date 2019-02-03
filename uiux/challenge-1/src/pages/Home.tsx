import React, { useEffect, useState } from "react";
import { RouteComponentProps } from "@reach/router";
import { fetchArtists } from "../shared/api/artist";
import Input from "../shared/components/Input";
import useDebounce from "../shared/hooks/useDebounce";

const Home: React.FC<RouteComponentProps> = () => {
  const [value, setValue] = useState<string>("");
  const debouncedValue = useDebounce(value, 300);

  useEffect(() => {
    if (debouncedValue !== "") {
      fetchArtists(debouncedValue).then(r =>
        console.log("artists result: ", r)
      );
    }
  }, [debouncedValue]);

  const onChange = (e: React.FormEvent<HTMLInputElement>) => {
    setValue(e.currentTarget.value);
  };

  return (
    <main>
      <h1>Hello world</h1>
      <Input value={value} onChange={onChange} placeholder="Search artist" />
    </main>
  );
};

export default Home;
