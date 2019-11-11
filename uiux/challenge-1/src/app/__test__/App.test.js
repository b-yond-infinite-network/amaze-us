import React from 'react';
import ReactDOM from 'react-dom';
import { configure, mount } from 'enzyme'
import Adapter from 'enzyme-adapter-react-16';
import App from './../App';

configure({ adapter: new Adapter() });

describe("<App />", () => {
  it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<App />, div);
    ReactDOM.unmountComponentAtNode(div);
  });

  it("Render matches snapshot", () => {
    const app = mount(<App />);
    console.log(app.debug());
    
    expect(app).toMatchSnapshot();
  });
});