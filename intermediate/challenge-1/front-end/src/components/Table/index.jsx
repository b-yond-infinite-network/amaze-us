import React from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing(3),
    overflowX: 'auto'
  },
  table: {
    minWidth: 650
  }
}));

const CustomTable = ({ data, headTitles, caption }) => {
  const classes = useStyles();

  return (
    <Paper className={classes.root}>
      {caption && (
        <Table className={classes.table} aria-label={'caption table'}>
          <caption>{caption}</caption>
        </Table>
      )}
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            {headTitles.map((title, index) => (
              <TableCell key={title.name + index} align={'left'}>
                {title.name}
              </TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={index}>
              {Object.entries(item).map(([key, value]) => {
                return (
                  key !== 'id' && (
                    <TableCell key={key} align={'left'}>
                      {value}
                    </TableCell>
                  )
                );
              })}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
};

export default CustomTable;
