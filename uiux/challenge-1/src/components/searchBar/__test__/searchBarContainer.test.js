import React from 'react';
import ReactDOM from 'react-dom';
import { configure, shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import SearchBarContainer from './../SearchBarContainer'

configure({ adapter: new Adapter() });

let wrapper;
beforeEach(() => {
    const setKeyWord = jest.fn();
    const launchSearch = jest.fn();
    wrapper = shallow(<SearchBarContainer
        setKeyWord={setKeyWord}
        launchSearch={launchSearch} />);
});

describe("SearchBarContainer", () => {
    it("Should render without crashing", () => {
        const div = document.createElement("div");
        ReactDOM.render(wrapper, div);
        ReactDOM.unmountComponentAtNode(div);
    });
});
