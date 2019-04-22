import React, { Component, Fragment } from "react";
import DateTimePicker from "../../CustomControls/DateTimePicker";
import FormControl from "@material-ui/core/FormControl";
import Input from "@material-ui/core/Input";
import FormLabel from "@material-ui/core/FormLabel";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import RestClient from "../../RestClient";
import { Endpoints } from "../../Constants";

import VisibilityIcon from "@material-ui/icons/Visibility";
import PropTypes from "prop-types";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import IconButton from "@material-ui/core/IconButton";
import FirstPageIcon from "@material-ui/icons/FirstPage";
import KeyboardArrowLeft from "@material-ui/icons/KeyboardArrowLeft";
import KeyboardArrowRight from "@material-ui/icons/KeyboardArrowRight";
import LastPageIcon from "@material-ui/icons/LastPage";
import { TableHead, Typography, Grid, TextField } from "@material-ui/core";
import _ from "lodash";
import Utils from '../../Utils'


const actionsStyles = theme => ({
  root: {
    flexShrink: 0,
    color: theme.palette.text.secondary,
    marginLeft: theme.spacing.unit * 2.5
  }
});

class TablePaginationActions extends React.Component {
  handleFirstPageButtonClick = event => {
    this.props.onChangePage(event, 0);
  };

  handleBackButtonClick = event => {
    this.props.onChangePage(event, this.props.page - 1);
  };

  handleNextButtonClick = event => {
    this.props.onChangePage(event, this.props.page + 1);
  };

  handleLastPageButtonClick = event => {
    this.props.onChangePage(
      event,
      Math.max(0, Math.ceil(this.props.count / this.props.rowsPerPage) - 1)
    );
  };

  render() {
    const { classes, count, page, rowsPerPage, theme } = this.props;

    return (
      <div className={classes.root}>
        <IconButton
          onClick={this.handleFirstPageButtonClick}
          disabled={page === 0}
          aria-label="First Page"
        >
          {theme.direction === "rtl" ? <LastPageIcon /> : <FirstPageIcon />}
        </IconButton>
        <IconButton
          onClick={this.handleBackButtonClick}
          disabled={page === 0}
          aria-label="Previous Page"
        >
          {theme.direction === "rtl" ? (
            <KeyboardArrowRight />
          ) : (
            <KeyboardArrowLeft />
          )}
        </IconButton>
        <IconButton
          onClick={this.handleNextButtonClick}
          disabled={page >= Math.ceil(count / rowsPerPage) - 1}
          aria-label="Next Page"
        >
          {theme.direction === "rtl" ? (
            <KeyboardArrowLeft />
          ) : (
            <KeyboardArrowRight />
          )}
        </IconButton>
        <IconButton
          onClick={this.handleLastPageButtonClick}
          disabled={page >= Math.ceil(count / rowsPerPage) - 1}
          aria-label="Last Page"
        >
          {theme.direction === "rtl" ? <FirstPageIcon /> : <LastPageIcon />}
        </IconButton>
      </div>
    );
  }
}

TablePaginationActions.propTypes = {
  classes: PropTypes.object.isRequired,
  count: PropTypes.number.isRequired,
  onChangePage: PropTypes.func.isRequired,
  page: PropTypes.number.isRequired,
  rowsPerPage: PropTypes.number.isRequired,
  theme: PropTypes.object.isRequired
};

const TablePaginationActionsWrapped = withStyles(actionsStyles, {
  withTheme: true
})(TablePaginationActions);

let counter = 0;
function createData(name, calories, fat) {
  counter += 1;
  return { id: counter, name, calories, fat };
}

const styles = theme => ({
  root: {
    width: "100%",
    marginTop: theme.spacing.unit * 3,
    paddingBottom: "20px"
  },
  table: {

  },
  tableWrapper: {
    width: '100%',
    overflowX: "auto"
  },
  container: {
    display: "flex",
    flexWrap: "wrap"
  }
});
class TransactionLogPage extends Component {
  constructor(props) {
    super(props);
    this.dateTimeHandler = this.dateTimeHandler.bind(this);
  }
  startTime = new Date().toISOString().substring(0, 11) + "00:00";
  endTime = new Date().toISOString().substring(0, 11) + "23:59";

  state = {
    startDateTime: this.startTime,
    endDateTime: this.endTime,
    jobName: "",
    logs: [],
    tableSearchText: "",
    filteredLogs: [],
    rowsPerPage: 5,
    page: 0
  };
  handleChangePage = (event, page) => {
    this.setState({ ...this.state, page: page });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ page: 0, rowsPerPage: event.target.value });
  };

formatDate = (date) => {
  var dt = new Date(date)
  var month=dt.getMonth()
  var year=dt.getFullYear()
  var day=dt.getDate()
  var hours = dt.getHours()
  var minutes = dt.getMinutes()
  var seconds = dt.getSeconds()
  //var zone = dt.getTimezoneOffset() * 60000
  var dateString = `${year}-${month<10?'0'+month:month}-${day<10?'0'+day:day}-${hours<10?'0'+hours:hours}.${minutes<10?'0'+minutes:minutes}.${seconds<10?'0'+seconds:seconds}`
    return dateString
}

  dateTimeHandler = (capturedDateTime, type) => {
    this.formatDate(capturedDateTime)
    if (type == "startDate") {
      this.setState({
        ...this.state,
        startDateTime: capturedDateTime
      });
    } else {
      this.setState({
        ...this.state,
        endDateTime: capturedDateTime
      });
    }
  };

  jobNameHandler = event => {
    this.setState({ ...this.state, jobName: event.target.value });
  };
  calculateTimeDiff = (item) => {
    return Math.abs(new Date(item.endTime)-new Date(item.startTime))
  }

  fetchTransactionLogsHandler = () => {
    this.refs.utils.Loading(true)
    //"http://172.17.0.177:8090/MQMFT-RestService/transfers/2018-12-10-11.01.17/2018-12-12-11.01.17/SAMPLE_TEST1",
    //"http://172.17.0.177:8090/MFT-DBAPI/mft/dbServices/getTransferLogs/2018-12-10-11.01.17/2018-12-12-11.01.17/SAMPLE_TEST1",
    //2018-12-10-11.01.17/2018-12-12-11.01.17/SAMPLE_TEST1`
    var sdt = this.formatDate(this.state.startDateTime)
    var edt = this.formatDate(this.state.endDateTime)
    var job = this.state.jobName == '' ? '%25' : this.state.jobName
    var url = `${Endpoints.transferLogs}/${sdt}/${edt}/${job}`
    RestClient.Get(url, {},
      data => {
        if (data) {
          this.setState({
            ...this.state,
            logs: JSON.parse(data.Result).body,
            filteredLogs: JSON.parse(data.Result).body
          });
        } else {
          this.refs.utils.Error("Failed to read transfer logs");
        }
        this.refs.utils.Loading(false)
      },
      error => {
        this.refs.utils.Error(JSON.stringify(error));
        this.refs.utils.Loading(false)
      }
    );
  };

  searchChangeHandler = event => {
    let filteredLogs;
    if (event.target.value === "") {
      filteredLogs = this.state.agents;
    } else {
      const transactionLogs = this.state.logs.slice();
      const searchWord = event.target.value;

      filteredLogs = transactionLogs.filter(log => {
        if (
          log.jobName.toLowerCase().indexOf(searchWord.toLowerCase()) >= 0 ||
          log.source_agent.toLowerCase().indexOf(searchWord.toLowerCase()) >=
            0 ||
          log.target_agent.toLowerCase().indexOf(searchWord.toLowerCase()) >=
            0 ||
          log.source_file.toLowerCase().indexOf(searchWord.toLowerCase()) >=
            0 ||
          log.destination_file
            .toLowerCase()
            .indexOf(searchWord.toLowerCase()) >= 0
        )
          return obj;
      });
    }
  };
  shouldComponentUpdate(nextProps, nextState) {
    if (
      this.state.startDateTime !== nextState.startDateTime ||
      this.state.endDateTime !== nextState.endDateTime ||
      this.state.jobName !== nextState.jobName ||
      this.state.logs !== nextState.logs ||
      this.state.filteredLogs !== nextState.filteredLogs ||
      this.state.searchText !== nextState.searchText ||
      this.state.rowsPerPage !== nextState.rowsPerPage ||
      this.state.page !== nextState.page
    ) {
      return true;
    } else {
      return false;
    }
  }

  componentDidUpdate() {
  }

  render() {
    const styles = theme => ({
      button: {
        margin: theme.spacing.unit
      },
      leftIcon: {
        marginRight: theme.spacing.unit
      },
      rightIcon: {
        marginLeft: theme.spacing.unit
      },
      iconSmall: {
        fontSize: 20
      },
      tablecell: {
        padding: 5
      }
    });
    const { classes } = this.props;
    const { filteredLogs, rowsPerPage, page } = this.state;
    const emptyRows =
      rowsPerPage -
      Math.min(rowsPerPage, filteredLogs.length - page * rowsPerPage);

    let tableHeader = [
      "Job Name",
      "Source Agent",
      "Target Agent",
      "Start Date and Time",
      "End Date and Time",
      // "Source File",
      // "Destination File",
      "Time Elapsed",
      "Status"
    ];
    tableHeader = tableHeader.map((header, index) => {
      return <TableCell key={index}>{header}</TableCell>;
    });
    const TablePaginationActionsWrapped = withStyles(actionsStyles, {
      withTheme: true
    })(TablePaginationActions);

    return (
      <React.Fragment>
        <Utils ref="utils" />
        <Grid container spacing={24} alignItems="flex-end" alignContent="stretch" >
					<Grid item md={6}>
              <Typography variant="h5">Transaction Log</Typography>
					</Grid>
				</Grid>
        <Grid container spacing={24} alignItems="flex-end" alignContent="stretch" >
					<Grid item md={3}>
            <DateTimePicker
                label="Start Date and Time"
                capturedValue={event => this.dateTimeHandler(event, "startDate")}
                defaultDateTime={this.state.startDateTime} />              
					</Grid>
					<Grid item md={3}>
            <DateTimePicker
              label="End Date and Time"
              capturedValue={event => this.dateTimeHandler(event, "endDate")}
              defaultDateTime={this.state.endDateTime} />
					</Grid>
          {/* <Grid item md={6}></Grid> */}
          <Grid item md={5}>
                <TextField
                    fullWidth
                    id="job-name"
                    label="Job Name"
                    InputLabelProps={{ shrink: true }}
                    onChange={this.jobNameHandler}
                    value={this.state.jobName} />
					</Grid>
          <Grid item md={1}>
            <Button
                variant="contained"
                className={classes.button}
                color="primary"
                disabled={ this.state.startDateTime == "" || this.state.endDateTime == "" }
                onClick={this.fetchTransactionLogsHandler}
              >
                Search
              </Button>
          </Grid>
				</Grid>
        
        
        
        
        {this.state.logs.length > 0 && 
            <div className={classes.tableWrapper}>
              <Table className={classes.table} padding="default">
                <TableHead>
                  <TableRow>
                    {/* <TableCell>
                      <Input
                        type="text"
                        value={this.state.tableSearchText}
                        placeholder="Search"
                        onChange={event => this.searchChangeHandler(event)}
                      />
                    </TableCell> */}
                    <TablePagination
                      rowsPerPageOptions={[5, 10, 25, 50]}
                      count={filteredLogs.length}
                      rowsPerPage={rowsPerPage}
                      page={page}
                      SelectProps={{ native: true }}
                      onChangePage={this.handleChangePage}
                      onChangeRowsPerPage={this.handleChangeRowsPerPage}
                      ActionsComponent={TablePaginationActionsWrapped}
                    />
                  </TableRow>
                  <TableRow>{tableHeader}</TableRow>
                </TableHead>

                <TableBody className={styles.tableBody}>
                  {filteredLogs
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row, index) => (
                      <TableRow key={index}>
                        <TableCell className={styles.tablecell}>
                          {row.jobName}
                        </TableCell>
                        <TableCell>{row.sourceSystem}</TableCell>
                        <TableCell>{row.targetSystem}</TableCell>
                        <TableCell>{row.startTime}</TableCell>
                        <TableCell>{row.endTime}</TableCell>
                        {/* <TableCell>{row.sourceFilename}</TableCell>
                        <TableCell>{row.destinationFilename}</TableCell> */}
                        {/* <TableCell>{row.time_elapsed}</TableCell> */}
                        <TableCell>{this.calculateTimeDiff(row)}</TableCell>
                        <TableCell>{row.status}</TableCell>
                      </TableRow>
                    ))}
                  {/* {emptyRows > 0 && (
                    <TableRow style={{ height: 48 * emptyRows }}>
                      <TableCell colSpan={9} />
                    </TableRow>
                  )} */}
                </TableBody>
              </Table>
            </div>
          // </Paper>
        }
      </React.Fragment>
    );
  }
}
{
  /* <Button variant="contained" color="primary" className={classes.button}>
    Get Transfer Logs}
    <Icon className={classes.rightIcon}>send</Icon>
</Button> */
}
export default withStyles(styles)(TransactionLogPage);
