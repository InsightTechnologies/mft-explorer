import React, { Component } from "react";
import Utils from "../../Utils";
import { Endpoints } from "../../Constants";
import RestClient from "../../RestClient";

import ManagerList from './ManagerList'
import ManagerItem from './ManagerItem'
// import QueuePutMessage from './QueuePutMessage'
// import QueueBrowseMessages from './QueueBrowseMessages'

import { IconButton,Table,TableCell,TableHead, TableRow } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import AddIcon from "@material-ui/icons/Add";
import RefreshIcon from "@material-ui/icons/Refresh";
import DropDown from "../../CustomControls/DropDown";

export default class QueueManagersPage extends Component {

	state = {
		hosts: [],
		managers: [],
		allManagers: [],
		mode: 'view', // view |  edit 
		manager: {}
	}
	handlePropChange = (key,value) => {
		this.setState({
			...this.state,
			[key]: value
		})
	}
	selectHost = (host) => {
		this.setState({
			...this.state,
			host: host
		})
		if (this.state.mode === 'view')
			setTimeout(() => {
				this.filterManagers()
			}, 100);
	}
	fetchHosts = () => {
		//this.refs.utils.Loading(true)
		RestClient.Get(Endpoints.Hosts,{},
		(data) => {
			if (data.Success) {
				this.setState({
					...this.state,
					hosts: JSON.parse(data.Result).body,
				})
			} else {
				this.refs.utils.Error('Failed to fetch Host details')
			}
			//this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			//this.refs.utils.Loading(false)
		})
	}
	fetchManagers = () => {
		this.refs.utils.Loading(true)
		RestClient.Get(Endpoints.QueueManagers,{},
		(data) => {
			if (data.Success) {
				this.setState({
					...this.state,
					allManagers: JSON.parse(data.Result).body,
					managers: JSON.parse(data.Result).body,
					mode: 'view'
				})
				if (this.state.host) {
					setTimeout(() => {
						this.filterManagers()
					}, 100);
				}
			} else {
				this.refs.utils.Error('Failed to fetch queue managers')
			}
			this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
	}
	filterManagers = () => {
		let managers = []
		managers = this.state.allManagers.filter(m => m.hostId == this.state.host )
		this.setState({
			...this.state,
			managers: managers
		})
	}

    updateManager = Manager => {
		this.refs.utils.Loading(true);
		if (Manager.qmid===0) {
			RestClient.Post(Endpoints.QueueManagers, Manager,
			(data) => {
				if (data.Success) {
					this.refs.utils.Success("Queue manager added successfully")
					setTimeout(() => {
						this.fetchManagers()
					}, 100);
				} else {
					this.refs.utils.Error(data.Message)
				}
				this.refs.utils.Loading(false)
			},
			(error) => {
				this.refs.utils.Error(error)
				this.refs.utils.Loading(false)
			})
		} else {
			RestClient.Put(Endpoints.QueueManagers, Manager,
			(data) => {
				if (data.Success) {
					this.refs.utils.Success("Queue manager updated successfully")
					setTimeout(() => {
						this.fetchManagers()
					}, 100);
				} else {
					this.refs.utils.Error(data.Message)
				}
				this.refs.utils.Loading(false)
			},
			(error) => {
				this.refs.utils.Error(error)
				this.refs.utils.Loading(false)
			})
		}
	}
	
	addManager = () => {
		// if (this.state.host) {
		this.setState({mode: 'edit', manager: {qmid: 0} })
		// } else {
		// 	this.refs.utils.Error("Please select a host")
		// }
		
	}
	editManager = Manager => {
		this.setState({mode: 'edit', manager: Manager})
	}
	deleteManager = Manager => {
		//let _Managers  = this.state.managers;
		this.refs.utils.Confirm(`Are you sure to delete Manager ${Manager.qmName}`,
		() => { 
			this.refs.utils.Loading(true)
			RestClient.Delete(Endpoints.QueueManagers+"/" + Manager.qmid, {},
			(data) => {
				if (data.Success) {
					setTimeout(() => {
						this.fetchManagers()
					}, 100);
						// _Managers.map((h,i) =>{
						// 	if (h.qmid === Manager.qmid) 
						// 	_Managers.splice(i,1) 
						// })
						// this.setState({ Managers: _Managers })
					this.refs.utils.Success("Queue manager deleted successfully")
				} else {
					this.refs.utils.Error(data.Message)
				}
				this.refs.utils.Loading(false)
			},
			(error) => {
				this.refs.utils.Error(error)
				this.refs.utils.Loading(false)
			}
			)
		})
	}

    componentDidMount () {
		this.fetchHosts()
		this.fetchManagers()
    }

	render() {    
		return (
		<React.Fragment>
			<Utils ref="utils" />
			<Table>
				<TableHead>
					<TableRow>
						<TableCell>
							<Typography variant="h5">Queue Managers</Typography>
						</TableCell>
						<TableCell>
							<DropDown items={this.state.hosts}
								value={this.state.host}
								caption='Select Host' 
								TextField="hostName" 
								ValueField="hostId"
								onChange={this.selectHost} /> 
						</TableCell>
						<TableCell align="right" >
							<IconButton color="primary" onClick={this.addManager} >
								<AddIcon /> 
							</IconButton>
							<IconButton color="primary" onClick={this.fetchManagers}>
								<RefreshIcon />
							</IconButton>
						</TableCell>
					</TableRow>
				</TableHead>
			</Table>
			{this.state.mode === 'view' && 
			<ManagerList managers={this.state.managers} editManager={this.editManager}  deleteManager={this.deleteManager} /> }  
			
			{this.state.mode === 'edit' && 
			<ManagerItem manager={this.state.manager} onCancel={() => this.setState({mode: 'view'})}
			hosts={this.state.hosts} updateManager={this.updateManager} /> }  
		</React.Fragment>
		)
	}
}	