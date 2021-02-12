import React, {useEffect, useState} from 'react';
import ColonyService from "../Services/ColonyService";


export default function PopulationCount() {
  const [error, setError] = useState(null);
  const [items, setItems] = useState('Not available');

  useEffect(() => {
    let unmounted = false;
    ColonyService.getPopulation().then(
      (result) => {
        if (!unmounted) setItems(result.data);
      },
      (error) => {
        if (!unmounted) setError(error);
      }
    );

    return () => {
      unmounted = true
    };
  }, []);

  return (<div className='PopulationCount'><p>The population counter:</p>
    <h3>{error ? 'N/A' : items.amount}</h3>
  </div>);

}
