import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import requestAnimationFrame from "./tempPolyfills";

Enzyme.configure({ adapter: new Adapter() });