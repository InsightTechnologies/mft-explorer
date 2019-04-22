import React, { Component } from "react";
import { Dialog, DialogTitle, DialogActions, DialogContent} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import _ from 'lodash'

import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import { Grid, Typography } from "@material-ui/core";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import TablePagination from "@material-ui/core/TablePagination";
import IconButton from "@material-ui/core/IconButton";
import FirstPageIcon from "@material-ui/icons/FirstPage";
import KeyboardArrowLeft from "@material-ui/icons/KeyboardArrowLeft";
import KeyboardArrowRight from "@material-ui/icons/KeyboardArrowRight";
import LastPageIcon from "@material-ui/icons/LastPage";
import { Table, TableHead, TableBody, TableRow, TableCell } from "@material-ui/core";

const styles ={
	grid : {
		marginLeft: 20
	},
	messageBg: {
		margin: 3,
		backgroundColor: '#ddd'
	}
};

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
		)
	}

	render() {
		const { count, page, rowsPerPage, direction } = this.props;

		return (
			<div style={{flexShrink: 0, marginLeft: 20}}>
				<IconButton
					onClick={this.handleFirstPageButtonClick}
					disabled={page === 0}
					aria-label="First Page"
				>
					{direction === "rtl" ? <LastPageIcon /> : <FirstPageIcon />}
				</IconButton>
				<IconButton
					onClick={this.handleBackButtonClick}
					disabled={page === 0}
					aria-label="Previous Page"
				>
					{direction === "rtl" ? (
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
					{direction === "rtl" ? (
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
					{direction === "rtl" ? <FirstPageIcon /> : <LastPageIcon />}
				</IconButton>
			</div>
		)
	}
}

TablePaginationActions.propTypes = {
	count: PropTypes.number.isRequired,
	onChangePage: PropTypes.func.isRequired,
	page: PropTypes.number.isRequired,
	rowsPerPage: PropTypes.number.isRequired,
}

export default class QueueBrowseMessages extends Component {
	state = {
		show: false,
		queue: {},
		messages: [],
		expanded: 0,
		count: 10,
		rowsPerPage: 10,
		page: 0
	}
	Close = () => {
		this.setState({
			...this.state,
			show: false
		});
	};
	Show = (queue, messages,total,start,end) => {
		if (!start) start = 1
		if (!end) end = 10
		
		let rowsPerPage = end - start + 1
		let page = Math.floor(start / rowsPerPage)
		this.setState({
			...this.state,
			queue: queue,
			messages: messages,
			show: true,
			expanded: 0,
			count: total,
			rowsPerPage: rowsPerPage,
			page: page
		});
	};
	handleChange = panel => (event, expanded) => {
		this.setState({
			...this.state,
			expanded: expanded ? panel : false,
		});
	};
	ParseMsg = msg => {
		let m = {message: '', properties: []}
		let msgArr = msg.split('\n')
			if (msgArr.length > 0) {
				m.message = msgArr[msgArr.length -1]
			}		
			for (var i=1; i < msgArr.length-2;i++)
			{
				m.properties.push(msgArr[i])
			}
		return m
	}

	// shouldComponentUpdate = (nextProps, nextState) => {
	// 	if (
	// 		this.state.rowsPerPage !== nextState.rowsPerPage ||
	// 		this.state.page !== nextState.page	
	// 	) {
	// 		return true;
	// 	} else {
	// 		return false;
	// 	}
	// }
	browseMessages = () => {
		const {page, rowsPerPage} = this.state
		this.props.browseMessages(this.state.queue, page*rowsPerPage +1, page*rowsPerPage + rowsPerPage)
	}
	handleChangePage = (event, page) => {
		this.setState({ ...this.state, page })
		setTimeout(() => {
			this.browseMessages()
		}, 200);
	}
	handleChangeRowsPerPage = event => {
		this.setState({...this.state, page: 0, rowsPerPage: event.target.value })
		setTimeout(() => {
			this.browseMessages()
		}, 200);
	}
	render() {
		const { classes} = this.props
		return (
			<Dialog fullWidth maxWidth="md" open={this.state.show} onClose={this.Close}>
				<DialogTitle>{this.state.queue.queueName} : Messages </DialogTitle>
				<DialogContent>
					<Table>
						<TableHead>
							<TableRow>
								<TableCell>
									<TablePagination
										rowsPerPageOptions={[5, 10, 25, 50]}
										count={this.state.count}
										rowsPerPage={this.state.rowsPerPage}
										page={this.state.page}
										SelectProps={{ native: true }}
										onChangePage={this.handleChangePage}
										onChangeRowsPerPage={this.handleChangeRowsPerPage}
										ActionsComponent={TablePaginationActions}
									/>
								</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							<TableRow>
								{this.state.messages.map((msg,ind) => {
									const details = this.ParseMsg(msg)
									return <ExpansionPanel square key={ind}
											expanded={this.state.expanded === ind+1}
											onChange={this.handleChange(ind+1)}>
										<ExpansionPanelSummary style={styles.messageBg} expandIcon={<ExpandMoreIcon />}>
										<Typography >{ind+1} : {details.message.substring(0,100)}</Typography>
										</ExpansionPanelSummary>
										<ExpansionPanelDetails>
											<div>
												<Typography color="primary" variant="caption">Properties</Typography>
												<Grid container direction="row" style={styles.grid} >
												{details.properties.map((prop,pind) => {
													return <Grid container item xs={6} key={pind}>
														<Grid item xs={4} >
															<Typography variant="caption">{_.startCase(prop.split(":")[0].trim())}</Typography>
														</Grid>
														<Grid item xs={8} >
															<Typography variant="body1">{prop.split(":")[1]}</Typography>
														</Grid>
													</Grid>
												} )}
												</Grid>
												{/* <Typography color="primary" variant="caption">Headers</Typography>
												<Grid container direction="row" style={styles.grid} >
													<Grid item md={12}  >
														<Typography variant="body1">{details.header}</Typography>
													</Grid>
												</Grid> */}
												<Typography color="primary" variant="caption">Message</Typography>
												<Grid container direction="row" style={styles.grid} >
													<Grid item md={12} >
														<Typography variant="subtitle1">{details.message}</Typography>
													</Grid>
												</Grid>
											</div>
										</ExpansionPanelDetails>
									</ExpansionPanel>
								} )}
							</TableRow>
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