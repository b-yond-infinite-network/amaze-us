import { connect } from 'react-redux'
import Spinner from './Spinner';
import { withRouter } from 'react-router-dom'

import { loadingOn, loadingOff } from "./redux/Actions"

const mapStateToProps = (state) => ({
    loading: state.loader.loading
})

const mapDispatchToProps = (dispatch, ownProps) => ({
    loadingOn: () => {
        dispatch(loadingOn())
    },
    loadingOff: () => {
        dispatch(loadingOff())
    }
})

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Spinner))
