import NewBus from './component';
import { connect } from 'react-redux';
import { busOperations } from 'store/modules/Bus';

const mapStateToProps = null;
const mapDispatchToProps = { ...busOperations };

export default connect(mapStateToProps, mapDispatchToProps)(NewBus);
