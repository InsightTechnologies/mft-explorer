import React, { Component } from "react";
import { Table, TableBody, TableCell, TableHead, TableRow} from '@material-ui/core';
import { Dialog, DialogTitle, DialogActions, DialogContent} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import _ from 'lodash'
const styles = theme => ({
});

export default class QueueProperties extends Component {
	state = {
		show: false,
		queue: {},
		properties: []
	}
	Close = () => {
		this.setState({
			show: false
		});
	};
	getPropsList = props => {
		var list = []
		Object.keys(props).map((key,val) => {
			list.push({key: key,val: val})
		})
		return list
	}
	Show = (queue, properties) => {
		var props = this.getPropsList(properties)
		this.setState({
			queue: queue,
			properties: props,
			show: true
		});
	};
	render() {
		const { classes} = this.props
		return (
			<Dialog fullWidth open={this.state.show} onClose={this.Close}>
				<DialogTitle>{this.state.queue.queueName} : Properties</DialogTitle>
				<DialogContent>
					<Table>
						<TableHead>
							<TableRow>
								<TableCell>Property</TableCell>
								<TableCell>Value</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
						{this.state.properties.map((row,index) => {
							return <TableRow key={row.key}>
								<TableCell scope="row">{_.startCase(row.key)}</TableCell>
								<TableCell>{row.val}</TableCell>
							</TableRow>
						} )}
						</TableBody>
					</Table>
				</DialogContent>
				<DialogActions>
					<Button variant="contained" onClick={this.Close} color="primary">
						Close
					</Button>
				</DialogActions>
			</Dialog>
		);
	}
}