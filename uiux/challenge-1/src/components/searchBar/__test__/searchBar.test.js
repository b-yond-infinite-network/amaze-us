import React from 'react';
import ReactDOM from 'react-dom';
import { configure, shallow, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import SearchBar from './../SearchBar'

configure({ adapter: new Adapter() });

let wrapper;
beforeEach(() => {
    const setKeyWord = jest.fn();
    const launchSearch = jest.fn();
    wrapper = shallow(<SearchBar
        setKeyWord={setKeyWord}
        launchSearch={launchSearch} />);
});

describe("SearchBar", () => {
    it("Should render without crashing", () => {
        const div = document.createElement("div");
        ReactDOM.render(wrapper, div);
        ReactDOM.unmountComponentAtNode(div);
    });

    it("Render matches snapshot", () => {
        expect(wrapper).toMatchSnapshot();
    });

    it("Should render 1 <Input>", () => {
        expect(wrapper.find("Input")).toHaveLength(1);
    });

    it("Should render the search button", () => {
        expect(wrapper.find(".search-button")).toHaveLength(1);
    });

});