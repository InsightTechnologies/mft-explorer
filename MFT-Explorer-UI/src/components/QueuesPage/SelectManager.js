import React, { Component } from "react";
import { Grid, Button } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";
import { TextField } from "@material-ui/core";
import classNames from "classnames";
import InputAdornment from "@material-ui/core/InputAdornment";
import MenuItem from "@material-ui/core/MenuItem";

const styles = theme => ({
	container: {
		display: "flex",
		flexWrap: "wrap"
	},
	formControl: {
		margin: theme.spacing.unit * 2,
	},
	input: {
		display: "none"
	},
	margin: {
		margin: theme.spacing.unit
	},
	button: {
		margin: theme.spacing.unit*2,
		width: 200
	},
	textField: {
		flexBasis: 200
	}
});

class SelectManager extends Component {

	handleChange = () => e => {
		this.props.managerSelected(e.target.value)
	};


	render() {
		const { classes } = this.props
		return (
			<Grid container spacing={24} alignItems="flex-end" alignContent="stretch" >
				<Grid item md={6}>
					<TextField
						select
						fullWidth
						className={classNames(classes.margin, classes.textField)}
						variant="standard"
						label="Queue Manager"
						value={this.props.qm}
						onChange={this.handleChange()}
						InputProps={{
							startAdornment: (
								<InputAdornment position="end"> </InputAdornment>
							)
						}}
					>	
						{/* <MenuItem key={0} value={""}>---  Select queue manager  ---</MenuItem> */}
						{this.props.managers.map(qm => (
							<MenuItem key={qm.qmid} value={JSON.stringify(qm)}>
								{qm.qmName}
							</MenuItem>
						))}
					</TextField>
				</Grid>
				<Grid item md={6} align='center' >
					<Button variant="contained" className={classes.button} 
						color="primary" disabled={ this.props.qm == "" }
						onClick={() => this.props.fetchQueues('local')}>Get local queues</Button>
					<Button variant="contained" className={classes.button}
						color="primary" disabled={ this.props.qm == "" }
						onClick={() => this.props.fetchQueues('system')}> Get system queues </Button>
				</Grid>
			</Grid>
		)
	}
}

module.exports = withStyles(styles)(SelectManager);