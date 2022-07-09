import DriverList from './component';
import { connect } from 'react-redux';
import { driverOperations } from 'store/modules/Driver';

const mapStateToProps = ({ Driver }) => ({ Driver });
const mapDispatchToProps = { ...driverOperations };

export default connect(mapStateToProps, mapDispatchToProps)(DriverList);
