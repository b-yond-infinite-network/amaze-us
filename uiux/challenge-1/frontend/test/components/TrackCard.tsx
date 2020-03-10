import React from "react";
import { shallow, ShallowWrapper } from "enzyme";
import { expect, assert } from "chai";

import TrackCardComponent from "../../src/components/TrackCard";

export default () => {
  describe("Testing <TrackCardComponent /> component", () => {
    let wrapper: ShallowWrapper;
    it("should render correctly", () => {
      wrapper = shallow(
        <TrackCardComponent
          artistID={1234}
          artistName="xyz"
          rating={94}
          name="Superb Song"
          explicit={false}
          hasLyrics={true}
          numFavorite={90}
        ></TrackCardComponent>
      );

      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("Title")).to.have.length(1);
      expect(wrapper.find("GetLyrics")).to.have.length(1);
      expect(wrapper.find("MetaData")).to.have.length(2);

      expect(wrapper.find("Title").text()).to.equal("Superb Song");
      let metaDataTexts: string[] = [];
      wrapper.find("MetaData").forEach(each => {
        metaDataTexts.push(each.text());
      });
      assert.deepEqual(metaDataTexts, ["Artist: xyz", "Rating: 94"]);

      // Make explicit true and check for number of MetaData count and
      // Make hasLyrics false and check for number of GetLyrics count.
      wrapper = shallow(
        <TrackCardComponent
          artistID={1234}
          artistName="xyz"
          rating={94}
          name="Superb Song"
          explicit={true}
          hasLyrics={false}
          numFavorite={90}
        ></TrackCardComponent>
      );
      expect(wrapper.find("GetLyrics")).to.have.length(0);
      expect(wrapper.find("MetaData")).to.have.length(3);
      metaDataTexts = [];
      wrapper.find("MetaData").forEach(each => {
        metaDataTexts.push(each.text());
      });
      assert.deepEqual(metaDataTexts, [
        "Artist: xyz",
        "Rating: 94",
        "Explicit content"
      ]);
    });
  });
};
