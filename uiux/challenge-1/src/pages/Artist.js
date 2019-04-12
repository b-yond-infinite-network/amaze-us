import React, { PureComponent } from 'react'
import Home from './Home';
import { requestSongList } from '../reducers/songs'


class Artist extends PureComponent {
  constructor (props){
    super(props);
    this.state = {}
  }
  
  

  render () {
    return (
    <Home></Home>)
  }
}
export default Artist