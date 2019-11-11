import React from 'react';
import SortableDataTable from '../../components/sortableDataTable';

const Tracks = (props) => {
    return (
        <div className="tracks-wrapper">
            <h2>Tracks</h2>
            <SortableDataTable
                className={props.className}
                defaultSort={{ field: 'track_name', dir: 'asc' }}
                data={props.tracks}
                columns={props.columns}
                onRowClick={props.onRowClick} />
        </div>
    );
}

export default Tracks;
