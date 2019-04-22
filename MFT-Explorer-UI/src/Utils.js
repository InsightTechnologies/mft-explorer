import React, { Component } from "react";
import {ToastMessageTimeout} from '..//AppConfig'

import CircularProgress from "@material-ui/core/CircularProgress";
import {
	Dialog,
	DialogTitle,
	DialogActions,
	DialogContent
} from "@material-ui/core";
import Button from "@material-ui/core/Button";

import { withStyles } from "@material-ui/core/styles";

import classNames from "classnames";
import Snackbar from "@material-ui/core/Snackbar";
import CheckCircleIcon from "@material-ui/icons/CheckCircle";
import ErrorIcon from "@material-ui/icons/Error";
import InfoIcon from "@material-ui/icons/Info";
import CloseIcon from "@material-ui/icons/Close";
import SnackbarContent from "@material-ui/core/SnackbarContent";
import WarningIcon from "@material-ui/icons/Warning";
import green from "@material-ui/core/colors/green";
import amber from "@material-ui/core/colors/amber";
import IconButton from "@material-ui/core/IconButton";
import { object, string } from "prop-types";

const variantIcon = {
	success: CheckCircleIcon,
	warning: WarningIcon,
	error: ErrorIcon,
	info: InfoIcon
};

function MySnackbarContent(props) {
	const { classes, className, message, onClose, variant, ...other } = props;
	const Icon = variantIcon[variant];

	return (
		<SnackbarContent
			className={classNames(classes[variant], className)}
			aria-describedby="client-snackbar"
			message={
				<span id="client-snackbar" className={classes.message}>
					<Icon className={classNames(classes.icon, classes.iconVariant)} />
					{message}
				</span>
			}
			action={[
				<IconButton
					key="close"
					aria-label="Close"
					color="inherit"
					className={classes.close}
					onClick={onClose}
				>
					<CloseIcon className={classes.icon} />
				</IconButton>
			]}
			{...other}
		/>
	);
}

const styles1 = theme => ({
	success: {
		backgroundColor: green[600]
	},
	error: {
		backgroundColor: theme.palette.error.dark
	},
	info: {
		backgroundColor: theme.palette.primary.dark
	},
	warning: {
		backgroundColor: amber[700]
	},
	icon: {
		fontSize: 20
	},
	iconVariant: {
		opacity: 0.9,
		marginRight: theme.spacing.unit
	},
	message: {
		display: "flex",
		alignItems: "center"
	}
});

const MySnackbarContentWrapper = withStyles(styles1)(MySnackbarContent);

export default class Utils extends Component {
	state = {
		loading: false,
		confirm: {
			show: false,
			text: ""
		},
		alert: {
			show: false,
			variant: "success",
			message: ""
		}
	};
	closeAlert = () => {
		this.setState({
			alert: { show: false, variant: this.state.alert.variant }
		});
	};
	Close = () => {
		this.setState({
			confirm: { show: false, text: "" }
		});
	};

	Loading = inVal => {
		this.setState({
			loading: inVal
		});
	};
	Success = obj => {
		this.Alert('success',obj);
	}
	Error = obj => {
		this.Alert('error',obj);
	}
	Alert = (variant, message) => {
		if (typeof message !== string) {
			if (message.message)
				message = JSON.stringify(message.message)
			else 
			message = JSON.stringify(message)
		}			
		this.setState({
			alert: { show: true, variant, message }
		});
	};
	Confirm = (text, onYes, onNo) => {
		this.setState({
			confirm: { show: true, text: text, handleYes: onYes, handleNo: onNo }
		});
	};
	Yes = () => {
		if (this.state.confirm.handleYes) {
			this.state.confirm.handleYes();
		}
		this.Close();
	};
	No = () => {
		if (this.state.confirm.handleNo) {
			this.state.confirm.handleNo();
		}
		this.Close();
	};

	render() {
		return (
			<React.Fragment>
				<Dialog open={this.state.loading}>
					<DialogContent>
						<CircularProgress size={40} thickness={4} />
					</DialogContent>
				</Dialog>
				<Dialog open={this.state.confirm.show} onClose={this.No}>
					<DialogTitle>{this.state.confirm.text}</DialogTitle>
					<DialogActions>
						<Button onClick={this.No} color="secondary" autoFocus>
							No
						</Button>
						<Button variant="contained" onClick={this.Yes} color="primary">
							Yes
						</Button>
					</DialogActions>
				</Dialog>
				<Snackbar
					anchorOrigin={{
						vertical: "top",
						horizontal: "center"
					}}
					open={this.state.alert.show}
					autoHideDuration={ToastMessageTimeout}
					onClose={this.closeAlert}
				>
					<MySnackbarContentWrapper
						onClose={this.closeAlert}
						variant={this.state.alert.variant}
						message={this.state.alert.message}
					/>
				</Snackbar>
			</React.Fragment>
		);
	}
}
