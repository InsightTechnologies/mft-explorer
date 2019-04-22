import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import { IconButton, Table, TableBody, TableCell, TableHead, TableRow} from '@material-ui/core';
import Tooltip from '@material-ui/core/Tooltip';
import ListIcon from '@material-ui/icons/List';
import AddToQueueIcon from '@material-ui/icons/AddToQueue';
import MessageIcon from '@material-ui/icons/Message';

const CustomTableCell = withStyles(theme => ({
    body: {
        fontSize: 12,
    },
}))(TableCell);

const styles = theme => ({
});


class QueueList extends Component {
	render() {
		const { classes, queues } = this.props;
		return (
			<Table>
				<TableHead>
					<TableRow>
						<CustomTableCell align="left">Queue Name</CustomTableCell>
						<CustomTableCell align="left">Current Depth</CustomTableCell>
						<CustomTableCell align="left">Max Depth</CustomTableCell>
						<CustomTableCell align="left">Input Count</CustomTableCell>
						<CustomTableCell align="left">Output Count</CustomTableCell>
						<CustomTableCell align="right">Action</CustomTableCell>
					</TableRow>
				</TableHead>
				<TableBody>
					{queues.map((row,index) => (
						<TableRow key={row.queueName}>
						<CustomTableCell scope="row">{row.queueName}</CustomTableCell>
						<CustomTableCell scope="row">{row.queueDepth} </CustomTableCell>
						<CustomTableCell scope="row">{row.queueMaxDepth}</CustomTableCell>
						<CustomTableCell align="left">{row.inputCount}</CustomTableCell>
						<CustomTableCell align="left">{row.outputCount}</CustomTableCell>
						<CustomTableCell align="right">
							<Tooltip title="Properties" placement="top">
								<IconButton color="primary"  onClick={() => this.props.fetchProperties(row) } >
									<ListIcon /> 
								</IconButton>
							</Tooltip>
							<Tooltip title="Put message" placement="top">
								<IconButton color="primary"  onClick={() => this.props.putMessage(row) } >
									<AddToQueueIcon /> 
								</IconButton>
							</Tooltip>
							<Tooltip title="Browse Messages" placement="top">
								<IconButton color="primary" onClick={() => this.props.browseMessages(row) } >
									<MessageIcon/>
								</IconButton>
							</Tooltip>
						</CustomTableCell>
						</TableRow>
					))}
				</TableBody>
			</Table>
		);
	}
}

module.exports = withStyles(styles)(QueueList);