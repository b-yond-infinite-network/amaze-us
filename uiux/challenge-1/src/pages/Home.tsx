import React, { useEffect, useState } from "react";
import { RouteComponentProps } from "@reach/router";
import { fetchArtists } from "../shared/api/artist";
import Input from "../shared/components/Input";
import useDebounce from "../shared/hooks/useDebounce";
import Artist from "../shared/types/artist";
import List from "../features/List";

const Home: React.FC<RouteComponentProps> = () => {
  const [value, setValue] = useState<string>("");
  const debouncedValue = useDebounce(value, 300);
  const [results, setResults] = useState<Artist[]>([]);

  useEffect(() => {
    if (debouncedValue !== "") {
      console.log("debouncedValue: ", debouncedValue);
      fetchArtists(debouncedValue).then(response => {
        console.log("response: ", response);
        setResults(response);
      });
    }
  }, [debouncedValue]);

  const onChange = (e: React.FormEvent<HTMLInputElement>) => {
    setValue(e.currentTarget.value);
  };

  return (
    <main>
      <h1>Hello world</h1>
      <Input value={value} onChange={onChange} placeholder="Search artist" />
      <List items={results} initialized={debouncedValue !== ""} />
    </main>
  );
};

export default Home;
