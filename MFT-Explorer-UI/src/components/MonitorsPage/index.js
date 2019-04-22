import React from "react";
import Utils from '../../Utils'
import RestClient from "../../RestClient";
import { Endpoints } from "../../Constants";
import VisibilityIcon from "@material-ui/icons/Visibility";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
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
import { Grid, Typography , TableHead } from "@material-ui/core";
import Input from "@material-ui/core/Input";
import _ from "lodash";
import MonitorProperties from "./MonitorProperties";


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
    minWidth: 500
  },
  tableWrapper: {
    overflowX: "auto"
  }
});

class CustomPaginationActionsTable extends React.Component {
  state = {
    monitors: [],
    rows: [],
    page: 0,
    rowsPerPage: 5,
    searchText: "",
    selectedmonitor: ""
  };

  handleChangePage = (event, page) => {
    this.setState({ page });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ page: 0, rowsPerPage: event.target.value });
  };

  searchChangeHandler = event => {
    let filteredRows;
    if (event.target.value === "") {
      filteredRows = this.state.monitors;
    } else {
      const monitorsFromState = this.state.monitors.slice();
      const searchWord = event.target.value;

      filteredRows = monitorsFromState.filter(obj => {
        let match=false;

        for(var i=0;i<obj.properties.entry.length;i++){
          const element = obj.properties.entry[i];
          if(element.key=="monitorName"||element.key=="qmgr"||element.key=="agent"||element.key=="status"||element.key=="monitor_xml"||element.key=="Action"){
            if(element.content&&element.content.toLowerCase().indexOf(searchWord.toLowerCase()) >= 0){
              match = true;
              break;
            }
          }
        }
        return match ? obj:"";
      });
    }
    this.setState({
      ...this.state,
      rows: filteredRows,
      searchText: event.target.value
    });
  };

  showMonitorPropertiesHandler = monitor => {
    if (monitor) {
      this.setState({ ...this.state, selectedmonitor: monitor });
    }
    
  };
  fetchCordManagerAndmonitors = () => {
    this.refs.utils.Loading(true)
		RestClient.Get(Endpoints.QueueManagers,{},
		(data) => {
			if (data.Success) {
        let result = JSON.parse(data.Result).body.filter(obj => {
          return obj.isCoord === 'Y'
        })
        this.fetchmonitors(result[0])
			} else {
				this.refs.utils.Error('Failed to fetch queue managers')
			}
			
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
	}
  fetchmonitors = (qm) => {
    if (!qm) {
      this.refs.utils.Error('Failed to get Coordination queue manager to access monitors')
      this.refs.utils.Loading(false)
      return
    }
    //const url =  `http://172.17.0.177:5000/monitors`
    RestClient.Get(Endpoints.Monitors, {},
      data => {
        if (data.Success) {
          let result=[];
          // for(let i=0;i<data.Result.length;i++){
          //   result.push(JSON.parse(data.Result[i]));
          // }
          result = JSON.parse(data.Result).body
          this.setState({
            ...this.state,
            rows: result,
            monitors: result
          });
        } else {
          this.refs.utils.Error("Failed to fetch monitor details")
        }
        this.refs.utils.Loading(false)
      },
      error => {
        this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
      }
    )
  }
  componentDidMount() {
    this.fetchCordManagerAndmonitors()
  }

  shouldComponentUpdate(nextProps, nextState) {
    if (
      this.state.rows !== nextState.rows ||
      this.state.searchText !== nextState.searchText ||
      this.state.rowsPerPage !== nextState.rowsPerPage ||
      this.state.page !== nextState.page ||
      this.state.selectedmonitor !== nextState.selectedmonitor ||
      this.state.rows !== nextState.rows
    ) {
      return true;
    } else {
      return false;
    }
  }

  closePropertiesModalHandler = () => {
    this.setState({ ...this.state, selectedmonitor: "" });
  };
  render() {
    const { classes } = this.props;
    const { rows, rowsPerPage, page } = this.state;
    const emptyRows =
      rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);

    let tableHeader = [
      "Monitor Name",
      "Queue Manager Name",
      "Agent",
      "Status",
      "XML",
      "Action"
    ];
    tableHeader = tableHeader.map((header, index) => {
      return <TableCell key={index}>{header}</TableCell>;
    });

    const styles = {
      tableBody: {
        height: 100,
        overflowY: "auto"
      }
    };

const getCellValue = (key,row)=>{
  let result = row[key] //_.find(row.properties.entry, function(o) { return o.key ===key }).content;
 return result?result:"--";
};
    return (
      <React.Fragment>
        <Utils ref='utils' />
        <Grid container spacing={24} alignItems="flex-end" alignContent="stretch" >
					<Grid item md={6}>
              <Typography variant="h5">Monitors</Typography>
					</Grid>
				</Grid>
        {/* <Paper className={classes.root}> */}
          <div className={classes.tableWrapper}>
            <Table className={classes.table}>
              <TableHead>
                {/* <TableRow>
                  <TableCell>
                    <Input
                      type="text"
                      value={this.state.searchText}
                      placeholder="Search"
                      onChange={event => this.searchChangeHandler(event)}
                    />
                  </TableCell>
                  <TablePagination
                      rowsPerPageOptions={[5, 10, 25, 50]}
                      colSpan={6}
                      count={rows.length}
                      rowsPerPage={rowsPerPage}
                      page={page}
                      SelectProps={{ native: true }}
                      onChangePage={this.handleChangePage}
                      onChangeRowsPerPage={this.handleChangeRowsPerPage}
                      ActionsComponent={TablePaginationActionsWrapped}
                  />
                </TableRow> */}
                <TableRow>{tableHeader}</TableRow>
              </TableHead>

              <TableBody style={styles.tableBody}>
                {rows
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row, index) => (
                    <TableRow key={index}>
                    
                      <TableCell> {getCellValue("monitorName",row)}</TableCell>
                      <TableCell> {getCellValue("qmgr",row)}</TableCell>
                      <TableCell> {getCellValue("agent",row)}</TableCell>
                      <TableCell> {getCellValue("status",row)}</TableCell>

                    {/*  <TableCell> {getCellValue("monitor_xml",row)}</TableCell>
                      <TableCell> {getCellValue("action",row)}</TableCell>*/}
                      

                      {/* <TableCell> {row.monitorName}</TableCell> */}
                      {/* <TableCell>{row.monitor_type}</TableCell>
                      <TableCell>{row.description}</TableCell>
                      <TableCell>{row.qmgr}</TableCell>
                      <TableCell>{row.status}</TableCell> */}
                      <TableCell>
                        <IconButton
                          color="primary"
                          onClick={() => this.showMonitorPropertiesHandler(row)}
                        >
                          <VisibilityIcon />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))}
                {emptyRows > 0 && (
                  <TableRow style={{ height: 48 * emptyRows }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
        {/* </Paper> */}
        {this.state.selectedmonitor && (
          <MonitorProperties ref="monitorProperties"
          Monitor={this.state.selectedmonitor}
            CloseModal={this.closePropertiesModalHandler}
          />
        )}
      </React.Fragment>
    );
  }
}

CustomPaginationActionsTable.propTypes = {
  classes: PropTypes.object.isRequired
};
export default withStyles(styles)(CustomPaginationActionsTable);
