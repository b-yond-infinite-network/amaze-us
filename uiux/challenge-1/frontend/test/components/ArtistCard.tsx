import React from "react";
import { shallow, ShallowWrapper } from "enzyme";
import { expect, assert } from "chai";

import ArtistCardComponent from "../../src/components/ArtistCard";

export default () => {
  describe("Testing <ArtistCardComponent /> component", () => {
    let wrapper: ShallowWrapper;
    it("should render correctly", () => {
      wrapper = shallow(
        <ArtistCardComponent
          artistID={1234}
          artistName="xyz"
          artistCountry="US"
          artistRating={94}
          artistTwitterURL="someURL"
          totalAvailable={2000}
        ></ArtistCardComponent>
      );
      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("Title")).to.have.length(1);
      expect(wrapper.find("MetaData")).to.have.length(3);
      expect(wrapper.find("GetTracks")).to.have.length(1);

      expect(wrapper.find("Title").text()).to.equal("xyz");
      let metaDataTexts: string[] = [];
      wrapper.find("MetaData").forEach(each => {
        metaDataTexts.push(each.text());
      });
      assert.deepEqual(metaDataTexts, ["Rating: 94", "Country: US", "Twitter"]);

      wrapper = shallow(
        <ArtistCardComponent
          artistID={1234}
          artistName="xyz"
          artistCountry=""
          artistRating={94}
          artistTwitterURL=""
          totalAvailable={2000}
        ></ArtistCardComponent>
      );

      expect(wrapper.find("Wrapper")).to.have.length(1);
      expect(wrapper.find("Title")).to.have.length(1);
      expect(wrapper.find("MetaData")).to.have.length(1);
      expect(wrapper.find("GetTracks")).to.have.length(1);

      expect(wrapper.find("Title").text()).to.equal("xyz");
      metaDataTexts = [];
      wrapper.find("MetaData").forEach(each => {
        metaDataTexts.push(each.text());
      });
      assert.deepEqual(metaDataTexts, ["Rating: 94"]);
    });
  });
};
