import * as React from 'react';
import { createShallow } from '@material-ui/core/test-utils';

import Search from '../search';
import SearchBar from '../search-bar';
import Artists from '../artists';
import Empty from '../empty';

describe('<Search />', () => {
    let shallow: any;

    beforeAll(() => {
        shallow = createShallow();
    })

    it('renders the <SearchBar /> component', () => {
        const wrapper = shallow(<Search />);
        expect(wrapper.find(SearchBar)).toHaveLength(1);
    });

    it('not render the <Artists /> component by default', () => {
        const wrapper = shallow(<Search />);
        console.log(wrapper);
        expect(wrapper.find(Artists)).toHaveLength(0);
    });

    it('render the <Empty /> component if searched was performed but no artists', () => {
        const wrapper = shallow(<Search />);
        wrapper.setState({ searched: true });
        expect(wrapper.find(Empty)).toHaveLength(1);
    });

    it('render the <Artists /> component if searched was performed but no artists', () => {
        const wrapper = shallow(<Search />);
        wrapper.setState({ searched: true, artists: [{}, {}] });
        expect(wrapper.find(Artists)).toHaveLength(1);
    });
});
