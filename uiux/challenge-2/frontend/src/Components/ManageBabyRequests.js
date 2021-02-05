import React, {useEffect, useReducer, useState} from 'react';
import axios from 'axios';


const listReducer = (state, action) => {
  switch (action.type) {
    case 'REMOVE_ITEM':
      return {
        ...state,
        list: state.list.filter((item) => item.id !== action.id),
      };
    case 'LOAD_ITEM':
      return {
        ...state,
        list: action.data
      };
    default:
      throw new Error();
  }
};

const ManageBabyRequests = () => {
  const [requests, dispatchListData] = useReducer(listReducer, {list: []});
  const [error, setError] = useState(null);

  useEffect(() => {
    let unmounted = false;
    axios.get('/v1/baby/request')
      .then(
        (result) => {
          if(!unmounted) {
            dispatchListData({type: 'LOAD_ITEM', data: result.data.requests});
            setError(false)
          }
        },
        (error) => {
          setError(true)
        }
      );
    return () => { unmounted = true };
  }, []);

  function handleRemove(id) {
    dispatchListData({type: 'REMOVE_ITEM', id});
  }

  return error ? <i>Unexpected error while retrieving requests</i> :
    <Requests list={requests.list} onRemove={handleRemove}/>;
};

const Requests = ({list, onRemove}) => {
  if (list.length === 0) {
    return <i>There are no new requests</i>
  } else {
    return (
      <ul className='baby-requests'>
      {list.map((item) => (
        <Item key={item.id} item={item} onRemove={onRemove}/>
      ))}
    </ul>)
  }
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
    axios.put('/v1/baby/request/' + encodeURI(item.id), {status: decision})
      .then(
        (response) => {
          if (response.status === 200) {
            onRemove(item.id)
          } else {
            //console.log(response.status)
          }
        },
        (error) => {
          //console.log(error)
        }
      )
  }}>
    {decision === 'approved' ? 'approve' : 'deny'}
  </button>
);

export default ManageBabyRequests;
