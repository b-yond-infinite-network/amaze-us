import React from "react";
import { expect } from "chai";
import App from "../../src/App";
import Enzyme, { shallow } from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter() });

export default () => {
  describe("Testing <App> component ", () => {
    let wrapper;
    it("Should have correct number of components", () => {
      wrapper = shallow(<App />);
      expect(wrapper.find("HashRouter")).to.have.length(1);
      expect(wrapper.find("Switch")).to.have.length(1);
      expect(wrapper.find("Route")).to.have.length(1);
    });
  });
};
