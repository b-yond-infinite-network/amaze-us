import NewSchedule from './component';
import { connect } from 'react-redux';
import { scheduleOperations } from 'store/modules/Schedule';

const mapStateToProps = ({ Bus, Driver }) => ({ Bus, Driver });
const mapDispatchToProps = { ...scheduleOperations };

export default connect(mapStateToProps, mapDispatchToProps)(NewSchedule);
