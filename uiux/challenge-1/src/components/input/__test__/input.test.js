import React from 'react';
import ReactDOM from 'react-dom';
import { configure, shallow, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Input from './../index';

configure({ adapter: new Adapter() });

describe("Input test", () => {
    it("Should render without crashing", () => {
        const div = document.createElement("div");
        ReactDOM.render(<Input />, div);
        ReactDOM.unmountComponentAtNode(div);
    });

    it("Should render a value", () => {
        const value = "Input value";
        const wrapper = shallow(<Input value={value} />);

        expect(wrapper.prop('value')).toEqual(value);
    });

    it("Should render default type ", () => {
        const defaultType = "text";
        const wrapper = shallow(<Input />);

        expect(wrapper.prop('type')).toEqual(defaultType);
    });

    it("Should render a type ", () => {
        const type = "search";
        const wrapper = shallow(<Input type={type} />);

        expect(wrapper.prop('type')).toEqual(type);
    });

    it("Should call onChange when input changes", () => {
        const value = "New value";
        const onChange = jest.fn();
        const wrapper = shallow(<Input onChange={onChange} />);
        const input = wrapper.find('input');

        input.simulate('change', { target: { value } })

        expect(onChange).toHaveBeenCalled();
    });
});