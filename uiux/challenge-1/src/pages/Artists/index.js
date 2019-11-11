import { connect } from 'react-redux';
import ArtistsContainer from './artistsContainer';
import { getArtistsList } from './../../services/artistsService/artistsActions';


// function mapStateToProps(state) {
//     return {
//         artistsList: state.artistsReducer.artistsList,
//         isLoading: state.artistsReducer.isLoading
//     }
// }
// export default connect((mapStateToProps), { getArtistsList })(ArtistsContainer);

export default connect((state) => ({
    artistList: state.artistsReducer.artistList,
    isLoading: state.artistsReducer.isLoading
}), {
    getArtistsList,
})(ArtistsContainer);

// export default connect((state) => {
//     console.log(state);
//     return ({
//     artistsList: state.artistsReducer.artistList,
//     isLoading: state.artistsReducer.isLoading,})
// }, {
//     getArtistsList,
// })(ArtistsContainer);