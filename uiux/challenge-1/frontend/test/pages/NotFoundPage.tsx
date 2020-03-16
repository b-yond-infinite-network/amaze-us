import React from "react";
import { expect } from "chai";
import { shallow, ShallowWrapper } from "enzyme";

import NotFoundPage from "../../src/pages/NotFoundPage";

export default () => {
  describe("Testing <NotFoundPage/> page", () => {
    it("Should render correctly when it loads initially", () => {
      const wrapper: ShallowWrapper = shallow(<NotFoundPage></NotFoundPage>);
      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("Header")).to.have.length(1);
      expect(wrapper.find("Header").props()["enableHomeButtonLink"]).to.be.true;
      expect(wrapper.find("Message")).to.have.length(1);
    });
  });
};
