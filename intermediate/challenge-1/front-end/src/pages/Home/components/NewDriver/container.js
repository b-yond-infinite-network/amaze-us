import NewDriver from './component';
import { connect } from 'react-redux';
import { driverOperations } from 'store/modules/Driver';

const mapStateToProps = null;
const mapDispatchToProps = { ...driverOperations };

export default connect(mapStateToProps, mapDispatchToProps)(NewDriver);
