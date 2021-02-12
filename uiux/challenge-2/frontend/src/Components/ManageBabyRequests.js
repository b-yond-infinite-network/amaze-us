import React, {useEffect, useReducer, useState} from 'react';
import {LOAD_ITEM, REMOVE_ITEM} from "../Actions/types";
import {listReducer} from "../Reducers/listReducer";
import ColonyService from "../Services/ColonyService";

const ManageBabyRequests = () => {
  const [requests, dispatchListData] = useReducer(listReducer, {list: []});
  const [error, setError] = useState(null);

  useEffect(() => {
    let unmounted = false;
    ColonyService.getRequest().then(
      (result) => {
        if (!unmounted) {
          dispatchListData({type: LOAD_ITEM, data: result.data.requests});
          setError(false)
        }
      },
      (_) => {
        setError(true)
      }
    );
    return () => {
      unmounted = true
    };
  }, []);

  function handleRemove(id) {
    dispatchListData({type: REMOVE_ITEM, id});
  }

  return error ? <i>Unexpected error while retrieving requests</i> :
    <Requests list={requests.list} onRemove={handleRemove}/>;
};

const Requests = ({list, onRemove}) => {
  return list.length === 0 ? <i>There are no new requests</i>
    : (
      <ul className='baby-requests'>
        {list.map((item) => (
          <Item key={item.id} item={item} onRemove={onRemove}/>
        ))}
      </ul>
    )
};

const Item = ({item, onRemove}) => (
  <li>
    <ButtonBabyRequest item={item} onRemove={onRemove} decision='approved'/>
    <ButtonBabyRequest item={item} onRemove={onRemove} decision='denied'/>
    <span>Request status: <b>{item.status.toUpperCase()}</b></span>
    <span className='baby-name'>name: <i>{item.name}</i></span>
  </li>
);

const ButtonBabyRequest = ({item, onRemove, decision}) => (
  <button type='button' aria-label='requestButton' className={decision} onClick={() => {
    ColonyService.putRequestDecision(item.id, decision).then(
      (response) => {
        if (response.status === 200) {
          onRemove(item.id)
        }
      })
  }}>
    {decision === 'approved' ? 'approve' : 'deny'}
  </button>
);

export default ManageBabyRequests;
