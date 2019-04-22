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

const agentProperties = props => {
  const {Agent} = props;
  const getCellValue = (key,row)=>{
    let result = _.find(row, function(o) { return o.key ===key }).content;
   return result?result:"--";
  };
  return (
    // <Dialog fullWidth open={this.state.show} onClose={this.Close}>
    <Dialog fullWidth open={Agent ? true : false}>
      <DialogTitle>{getCellValue("agentName",Agent)} : Properties</DialogTitle>
      <DialogContent>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Property</TableCell>
              <TableCell>Value</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {Agent.map((row, index) => {
              return (
                <TableRow key={index}>
                  <TableCell scope="row" style={{width:'50%'}}>{_.startCase(row.key)}</TableCell>
                  <TableCell>{_.startCase(row.content)}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </DialogContent>
      <DialogActions>
        <Button variant="contained" color="primary" onClick={props.CloseModal}>
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default agentProperties;
