import React, { useState } from 'react';
import PropsTypes from 'prop-types';
import { orderBy } from '@progress/kendo-data-query';
import { Grid, GridColumn as Column } from '@progress/kendo-react-grid';
import './sortableDataTable.css'

const DataTable = (props) => {
    const [sort, setSort] = useState([props.defaultSort]);

    return (
        <Grid
            className={props.className}
            data={orderBy(props.data, sort)}
            sortable
            sort={sort}
            onSortChange={(e) => { setSort(e.sort) }}
            onRowClick={props.onRowClick}>
            {props.columns && props.columns.map((value, index) => {
                return <Column key={index} field={value.field} title={value.title} />;
            })}
        </Grid>
    );
}

DataTable.propsTypes = {
    data: PropsTypes.array.isRequired,
    onSortChange: PropsTypes.func.isRequired,
    className: PropsTypes.string,
    columns: PropsTypes.string,
    defaultSort: PropsTypes.object,
    onRowClick: PropsTypes.func,
}

DataTable.defaultProps = {
    className: PropsTypes.string,
    onRowClick: () => { },
}

export default DataTable;