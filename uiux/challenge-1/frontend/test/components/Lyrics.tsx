import React from "react";
import { expect } from "chai";
import { shallow, ShallowWrapper } from "enzyme";

import Lyrics from "../../src/components/Lyrics";

export default () => {
  describe("Testing <Lyrics/> component", () => {
    let wrapper: ShallowWrapper;
    it("should render correctly", () => {
      wrapper = shallow(
        <Lyrics
          lyricsContent={
            "loLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel imperdiet massa, at auctor ex. Etiam feugiat condimentum massa, sed luctus felis. Integer nec quam turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vestibulum id nisl ac sagittis. Suspendisse potenti. Suspendisse nisi ante, elementum sed semper nec, dictum ut purus"
          }
          handleClose={() => {}}
        ></Lyrics>
      );
      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("LyricsContent")).to.have.length(1);
      expect(wrapper.find("BackToSearch")).to.have.length(1);

      expect(wrapper.find("LyricsContent").text()).to.equal(
        "loLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel imperdiet massa, at auctor ex. Etiam feugiat condimentum massa, sed luctus felis. Integer nec quam turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vestibulum id nisl ac sagittis. Suspendisse potenti. Suspendisse nisi ante, elementum sed semper nec, dictum ut purus"
      );
    });
  });
};
