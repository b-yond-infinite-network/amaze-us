import * as React from 'react';

export default class Search extends React.Component<{}> {
    constructor() {
        super({});
        this.state = { search: '' };
    }

    search() {
        alert(this.state['search']);
    }

    updateSearch(e) {
        this.setState({
            search: e.target.value
        });
    }

    render() {
        return (
            <div>
                <input type="text" onChange={this.updateSearch.bind(this)} placeholder="Find your artist" />
                <button onClick={this.search.bind(this)}>Find it</button>
            </div>
        );
    }
}