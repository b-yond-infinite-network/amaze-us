import React from "react";
import { expect } from "chai";
import { shallow, ShallowWrapper } from "enzyme";

import SearchResultBanner from "../../src/components/SearchResultBanner";

export default () => {
  describe("Testing <SearchResultBanner /> component", () => {
    let wrapper: ShallowWrapper;
    it("should render correctly", () => {
      wrapper = shallow(
        <SearchResultBanner
          banner={"Test Results from Justin Bieber"}
        ></SearchResultBanner>
      );
      expect(wrapper.find("Wrapper").text()).to.equal(
        "Test Results from Justin Bieber"
      );
    });
  });
};
