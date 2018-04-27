// Libraries
import React, { Component } from 'react';
import orderBy from 'lodash/orderBy';
// Components
import TrackList from 'components/presentational/track-list';

const SortIcon = ({
  current,
  direction
}) => (
  current
    ? direction
      ? <i className="fas fa-sort-up"></i>
      : <i className="fas fa-sort-down"></i>
    : <i className="fas fa-sort" style={{opacity: .25}}></i>
);

class SortableTrackList extends Component {

  constructor (props) {
    super(props);
    this.onClick = this.onClick.bind(this);

    this.sortConfig = [
      {
        name: 'Popularity',
        property: 'rating',
        direction: false,
      },
      // Note: Although I calculated the lyrics word count, it comes from the lyrics API,
      // so surfacing that value here would mean making separate API calls for each
      // track. Overkill! I'm replacing it with track name word count just for fun :)
      {
        name: 'Word count',
        property: 'wordCount',
        direction: true
      },
      {
        name: 'Song title',
        property: 'name',
        direction: true
      },
      {
        name: 'Duration',
        property: 'duration',
        direction: false
      }
    ];

    const sort = this.sort(this.sortConfig[0]);

    this.state = {
      sortBy: this.sortConfig[0],
      sort
    };
  }

  /**
   * @method sortByItem
   * @description Handle link click
   */
  sortByItem = item => {
    const newSortState = Object.assign({}, item, {
      direction: !this.state.sortBy.direction
    });

    const newSort = this.sort(newSortState);

    this.setState(() => ({
      sortBy: newSortState,
      sort: newSort
    }));
  }

  /**
   * @method onClick
   * @description Handle link click
   */
  onClick = (event, item) => {
    event.preventDefault();
    this.sortByItem(item);
  };

  /**
   * @method getDirectionString
   * @description Translate boolean direction state
   */
  getDirectionString = direction => direction
    ? 'asc'
    : 'desc';

  /**
   * @method sort
   * @description Sort tracks by selected criteria and order 
   */
  sort = sortState => orderBy(
    this.props.tracks,
    [sortState.property],
    [this.getDirectionString(sortState.direction)]
  );

  render() {
    return (
      <div>
        <p>
          {this.sortConfig
            .map(item => {
              return (
                <React.Fragment
                  key={item.property}
                >
                  <a
                    className="ui-pill"
                    href="#0"
                    onClick={event => this.onClick(event, item)}
                    style={{
                      backgroundColor: item.name === this.state.sortBy.name ? 'rgba(255, 255, 255, .25)' : null
                    }}
                  >{item.name}&nbsp;
                    <SortIcon
                      current={item.name === this.state.sortBy.name}
                      direction={this.state.sortBy.direction}
                    />
                  </a>
                </React.Fragment>
              );
            })
            .reduce((previous, item) => {
              return previous === null ? [item] : [previous, item];
            }, null)
          }
        </p>
        <TrackList
          actions={this.props.actions}
          tracks={this.state.sort}
        />
      </div>
    );
  }
}

export default SortableTrackList;
