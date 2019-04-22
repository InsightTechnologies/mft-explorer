import React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@material-ui/core";
import {
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent
} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import _ from "lodash";

class MonitorProperties extends React.Component {

  // state = {
  //   mProps: []
  // }

  // componentDidMount () {
  //   const {Monitor} = this.props;

  //   let mProps = []
  //   for (var key in Monitor)
  //   {
  //     mProps.push( {key: key, value: Monitor[key]})
  //   }
  //   this.setState({ ...this.state, mProps })
  // }

  getCellValue = (key,row)=>{
    let result =row[key] // _.find(row, function(o) { return o.key ===key }).content;
    return result?result:"--";
  };

  render (){    
    const {Monitor} = this.props
  return (
    <Dialog fullWidth open={Monitor ? true : false} onClose={this.props.CloseModal}>
      <DialogTitle>{this.getCellValue("monitorName",Monitor)} : XML</DialogTitle>
      <DialogContent>
        <pre>{Monitor.mxml}</pre>
        {/* <Table>
          <TableHead>
            <TableRow>
              <TableCell>Property</TableCell>
              <TableCell>Value</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            { mProps.map((row, index) => {
              return (
                <TableRow key={index}>
                  <TableCell scope="row" style={{width:'50%'}}>{_.startCase(row.key)}</TableCell>
                  <TableCell>{_.startCase(row.value)}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table> */}
      </DialogContent>
      <DialogActions>
        <Button variant="contained" color="primary" onClick={this.props.CloseModal}>
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );

}
}

export default MonitorProperties;
