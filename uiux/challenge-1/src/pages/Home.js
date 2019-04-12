import React, { PureComponent } from 'react'
import SearchBox from '../components/SearchBox';
import SongList from '../components/SongList';
import Lyrics from '../components/Lyrics';
import { withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import { requestSongList, requestLyric, requestSong } from '../reducers/songs'


class Home extends PureComponent {
  constructor (props){
    super(props);
    this.state = {}
  }

  handleArtistSelection = (e, {result}) => {
    {this.artistSelection(result.title)}
    {this.setState({ value: result.title })}
    {this.props.history.push({pathname: '/artist/' + result.title })}
  }
  artistSelection = artist => {
    this.SearchBoxChild.props.requestSongList(artist)
  }

  handleTrackSelection = trackID => {
    {this.lyricSelection(trackID)}
    {this.setState({ trackID: trackID })}
  }
  lyricSelection = trackID => {
    this.LyricsChild.props.handleOpenLyric(trackID)
  }

  handleOpenLyric = trackID => {
    this.LyricsChild.props.requestLyric(trackID);
    this.LyricsChild.props.requestSong(trackID);
    this.LyricsChild.setState({ open: true });
    
  }  
  render () {
    return (
      <div>
        <SearchBox 
          artistSelected = {this.state.value} 
          handleArtistSelection = {this.handleArtistSelection}/>
        <SongList 
          onRef={ref => (this.SearchBoxChild = ref)}
          handleTrackSelection={this.handleTrackSelection} 
          {...this.props}
        />
        <Lyrics 
          onRef={ref => (this.LyricsChild = ref)} 
          handleOpenLyric = {this.handleOpenLyric}
          />
      </div>
    )
  }
}

export default withRouter(
connect(
  ({ ui: { loading } }) => ({loading}), 
  {requestSongList, requestLyric, requestSong}
)
(Home))