import React from "react";
import { expect } from "chai";
import { shallow, ShallowWrapper } from "enzyme";
import { Select } from "antd";
import Search from "../../src/components/Search";
import { renderHook, act } from "@testing-library/react-hooks";
import { useSearchType } from "../../src/components/Search";

const { Option } = Select;

export default () => {
  describe("Testing <Search /> component", () => {
    let wrapper: ShallowWrapper;
    it("should render correctly", () => {
      wrapper = shallow(
        <Search searchTriggered={() => {}} setSortType={() => {}}></Search>
      );
      expect(wrapper.find("SearchAreaWrapper")).to.have.length(1);

      // Testing search option
      expect(wrapper.find("Select")).to.have.length(1);
      expect(wrapper.find("Option")).to.have.length(2);
      expect(
        wrapper.containsMatchingElement(<Option value="artist">Artist</Option>)
      ).to.be.true;
      expect(
        wrapper.containsMatchingElement(<Option value="track">Track</Option>)
      ).to.be.true;
      const selectProps = wrapper.find("Select").props();
      expect(selectProps.defaultValue).to.be.equal("artist");

      // Testing search bar
      expect(wrapper.find("Search")).to.have.length(1);

      // Testing Dropdown
      expect(wrapper.find("Dropdown")).to.have.length(1);
      expect(wrapper.find("button")).to.have.length(1);
    });

    it("useSearchType hook must change the state correctly", () => {
      const { result } = renderHook(() => useSearchType("artist"));
      act(() => {
        result.current[1]("track");
      });
      expect(result.current[0]).to.equal("track");
    });
  });
};
