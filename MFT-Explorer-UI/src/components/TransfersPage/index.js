import React, { Component } from "react";
import Utils from "../../Utils";
import { Endpoints } from "../../Constants";
import RestClient from "../../RestClient";
import { AppBar, Tabs, Tab, TabContainer, Grid } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";

import TransferSteps from './TransferSteps'
import TransferBuild from './TransferBuild'
import TransferPreview from './TransferPreview'
import DropDown from "../../CustomControls/DropDown";

const styles = {
	root: {
		flexGrow: 1,
	},
	button: {
		margin: 10,
		width: 100
	},
}
export default class TransfersPage extends Component {
	
	state = {
		operation: 'Monitor / Transfer',
		value: '',
		buildObj: {},
		managers: [],
	}

	handleChange = (event, value) => {
		if (value > 2){
			this.setState({ selectManager: true, monitorSelected: '', value})
			return;
		} else {
			this.setState({ selectManager: false, value })
		}
	};
	BuildUpdate = (build) => {
		this.setState({
			...this.state,
			buildObj: build
		})
	}
	stepsObj = () => {
		return this.refs.steps
	}
	getDetails = () => {
		return this.refs.build.getDetails()
	}
	handleMonitorChange =value => {
        this.setState({
            ...this.state,
            monitorSelected: value
        })
    }
	fetchManagers = () => {
		this.refs.utils.Loading(true)
		RestClient.Get(Endpoints.QueueManagers,{},
		(data) => {
			if (data.Success) {
				this.setState({
                    ...this.state,
					managers: JSON.parse(data.Result).body
				})
			} else {
				this.refs.utils.Error('Failed to fetch queue managers')
			}
			//this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
    }
	componentDidMount () {
		this.fetchManagers()
	}
	
	render() {
		return (
			<React.Fragment>
				<Utils ref="utils" />
				<Grid container spacing={24} >
					<Grid item xs={2}>
						<Typography variant="caption">Create / Edit</Typography>
						<Typography variant="h6">Monitor / Transfer</Typography>
					</Grid>
					<Grid item xs={10}>
						<AppBar position="static" color="default">
							<Tabs
								value={this.state.value}
								onChange={this.handleChange}
								variant="fullWidth"
								>
								<Tab label="Onetime Transfer" />
								<Tab label="Scheduled Transfer" />
								<Tab label="Create Monitor" />
								<Tab label="Edit Monitor" />
								<Tab label="Copy Monitor" />
							</Tabs>
						</AppBar>
					</Grid>
				</Grid>
				{ this.state.selectManager == true &&
					<DropDown items={this.state.managers}
						value={this.state.monitorSelected}
						caption='Queue Manager' 
						TextField="qmName" 
						onChange={this.handleMonitorChange} /> 
				}
				{ this.state.value !== '' &&
					(this.state.selectManager == true && this.state.monitorSelected || this.state.selectManager == false) &&
				<Grid container spacing={24} >
					<Grid item md={12}>
						<TransferSteps ref="steps" />
					</Grid>
					<Grid item md={8}>
						<TransferBuild ref="build" 
							managers={this.state.managers}
							type={this.state.value}
							steps={this.stepsObj}
							onChange={this.BuildUpdate} />
					</Grid>
					<Grid item md={4}>
						<TransferPreview ref="preview" getDetails={this.getDetails} />
					</Grid>
				</Grid>
				}
			</React.Fragment>
		)
	}

}	