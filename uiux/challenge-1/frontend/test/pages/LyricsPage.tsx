import React from "react";
import { expect } from "chai";
import { shallow, ShallowWrapper } from "enzyme";

import LyricsPage from "../../src/pages/LyricsPage";

export default () => {
  describe("Testing <LyricsPage/> page", () => {
    it("should render correctly when it loads initially", async () => {
      const wrapper: ShallowWrapper = shallow(
        <LyricsPage
          trackID="1075967"
          trackName="Born To Love You"
          location={{ search: "?trackID=1075967?trackName=Born To Love You" }}
        ></LyricsPage>
      );

      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("Header")).to.have.length(1);
      expect(wrapper.find("SearchResultBanner")).to.have.length(1);
      expect(wrapper.find("Lyrics")).to.have.length(1);
      expect(wrapper.find("Lyrics").props()).to.have.property("lyricsContent");
    });
  });
};
