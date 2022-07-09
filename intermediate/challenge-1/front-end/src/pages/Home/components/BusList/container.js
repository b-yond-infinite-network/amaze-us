import BusList from './component';
import { connect } from 'react-redux';
import { busOperations } from 'store/modules/Bus';

const mapStateToProps = ({ Bus }) => ({ Bus });
const mapDispatchToProps = { ...busOperations };

export default connect(mapStateToProps, mapDispatchToProps)(BusList);
