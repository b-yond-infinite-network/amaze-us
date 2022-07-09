import Home from './component';
import { connect } from 'react-redux';
import { driverOperations } from 'store/modules/Driver';
import { busOperations } from 'store/modules/Bus';

const mapStateToProps = null;
const mapDispatchToProps = { ...driverOperations, ...busOperations };

export default connect(mapStateToProps, mapDispatchToProps)(Home);
