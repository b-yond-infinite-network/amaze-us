import React from 'react';
import SortableDataTable from '../../components/sortableDataTable';


const Artists = (props) => {
    return (
        <div className="artists-wrapper">
            <h2>Artists</h2>
            <SortableDataTable
                className={props.className}
                defaultSort={{ field: 'artist_name', dir: 'asc' }}
                data={props.artists}
                columns={props.columns}
                onRowClick={props.onRowClick} />
        </div>
    );
}

export default Artists;
