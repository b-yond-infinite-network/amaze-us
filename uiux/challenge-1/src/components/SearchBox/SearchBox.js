import React, {PureComponent} from 'react';
import { connect } from 'react-redux';
import { Grid, Search } from 'semantic-ui-react';
import { fetchArtists } from '../../reducers/songs'
import _ from 'lodash';
import AwesomeDebouncePromise from 'awesome-debounce-promise';


class SearchBox extends PureComponent{
  constructor (props){
    super(props);
    this.state = {};
  }

  componentWillMount() {this.resetComponent()}

  componentDidMount(){
    console.log("mountSearch", this.props, this.state);
  }

  resetComponent = () => this.setState({ isLoading: false, results: [], value: '' })
  
  handleSearchChange = (e, { value }) => {
    this.setState({ isLoading: true, value })

    const allArtists = this.props.fetchArtists(value);
    allArtists.then(() =>  {
      const { artists } = this.props;
      const { value } = this.state;
      
      if (value.length < 1) return this.resetComponent()

      const re = new RegExp(_.escapeRegExp(value), 'i')
      const isMatch = result => re.test(result.title)
      
      const artistsTitle = artists.map(artist => {
        return {title: artist.artist.artist_name, key:artist.artist.artist_id};
      })
      this.setState({
        isLoading: false,
        results: _.filter(artistsTitle, isMatch),
      })
    })
    
  }
  
  render() {
    const { isLoading, results } = this.state;
    const { handleArtistSelection, value } = this.props;


      return  <Grid padded verticalAlign="middle" columns={1} centered>
                <Grid.Row>
                  <Grid.Column width={8} textAlign="center" verticalAlign="middle" >
                    <Search 
                        input={{ fluid: true, width:'16px' }} 
                        placeholder={"Enter the Artist"} 
                        size="massive"
                        results={results}
                        value={value}
                        loading={isLoading}
                        onResultSelect={handleArtistSelection}
                        // nice bonus <3 
                        onSearchChange={AwesomeDebouncePromise(this.handleSearchChange, 500)}                        
                    />
                  </Grid.Column>
                </Grid.Row>
                </Grid>
              
  };
};


const mapDispatchToProps = {
    fetchArtists
}

const mapStateToProps = (state) => {
    //get the data from redux store, like getMyState()
    const returningState = state.songs.artists !== undefined ? state.songs.artists : [];
    return { artists: returningState };
};

export default connect(
    mapStateToProps, 
    mapDispatchToProps
)(SearchBox);

