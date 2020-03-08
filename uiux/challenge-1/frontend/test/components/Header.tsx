import React from "react";
import { expect } from "chai";
import { shallow } from "enzyme";

import Header from "../../src/components/header";

export default () => {
  describe("Testing <Header /> component", () => {
    let wrapper;
    it("should render correctly", () => {
      wrapper = shallow(<Header enableHomeButtonLink={false}></Header>);
      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("Title")).to.have.length(1);
      expect(wrapper.find("Link")).to.have.length(0);

      wrapper = shallow(<Header enableHomeButtonLink={true}></Header>);
      expect(wrapper.find("Link")).to.have.length(1);
    });
  });
};
