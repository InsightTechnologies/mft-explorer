import React, { Component } from 'react'
import HostList from './HostList'
import HostItem from './HostItem'
import Utils from '../../Utils'
import {Endpoints} from '../../Constants'
import RestClient from "../../RestClient"


import { IconButton, Table, TableCell, TableHead, TableRow} from '@material-ui/core';
import Typography from '@material-ui/core/Typography'
import AddIcon from '@material-ui/icons/Add';
import RefreshIcon from '@material-ui/icons/Refresh';
// import SaveAllIcon from '@material-ui/icons/Save';

export default class HostDetailsPage extends Component {
	
	state = {
		hosts: [],
		mode: 'view', // view |  edit 
		host: {}
	}
	
	
	fetchHosts = () => {
		this.refs.utils.Loading(true)
		RestClient.Get(Endpoints.Hosts,{},
			(data) => {
				if (data.Success) {
					var result = JSON.parse(data.Result).body
					this.setState({
						hosts: result,
						mode: 'view'
					})
				} else {
					this.refs.utils.Error('Failed to fetch Host details')
				}
				this.refs.utils.Loading(false)
			},
			(error) => {
				this.refs.utils.Error(error)
				this.refs.utils.Loading(false)
			})
		}
		updateHost = host => {
			this.refs.utils.Loading(true);

			if (host.hostId==0) {
				RestClient.Post(Endpoints.Hosts, host,
				(data) => {
					if (data.Success) {
						this.refs.utils.Success("Host added successfully.")
						setTimeout(() => {
							this.fetchHosts()
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
				RestClient.Put(Endpoints.Hosts, host,
				(data) => {
					if (data.Success) {
						this.refs.utils.Success("Host updated successfully")
						setTimeout(() => {
							this.fetchHosts()
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
		
		addHost = () => {
			this.setState({mode: 'edit', host: {hostId: 0} })
		}
		editHost = host => {
			this.setState({mode: 'edit', host})
		}
		deleteHost = host => {
			//let _hosts  = this.state.hosts;
			this.refs.utils.Confirm(`Are you sure to delete host ${host.hostName}`,
			() => { 
				this.refs.utils.Loading(true)
				RestClient.Delete(Endpoints.Hosts+"/"+host.hostId, {},
				(data) => {
					if (data.Success) {
						// _hosts.map((h,i) =>{
						// 	if (h.hostId === host.hostId) 
						// 	_hosts.splice(i,1) 
						// })
						// this.setState({ hosts: _hosts })
						this.refs.utils.Success("Host deleted successfully")
						setTimeout(() => {
							this.fetchHosts()
						}, 100);
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
		}
		render() {
			return (
				<React.Fragment>
				<Utils ref="utils" />
				<Table>
				<TableHead>
				<TableRow>
				<TableCell>
				<Typography variant='h5'>Host Details</Typography>
				</TableCell>
				<TableCell align="right">
				<IconButton color="primary" onClick={this.addHost} >
				<AddIcon /> 
				</IconButton>
				<IconButton color="primary" onClick={this.fetchHosts}>
				<RefreshIcon />
				</IconButton>
				{/* <IconButton >
					<SaveAllIcon color='primary' />
				</IconButton> */}
				</TableCell>
				</TableRow>
				</TableHead>
				</Table>
				{this.state.mode === 'view' && 
				<HostList hosts={this.state.hosts} editHost={this.editHost}  deleteHost={this.deleteHost} /> }  
				{this.state.mode === 'edit' && 
				<HostItem host={this.state.host} onCancel={() => this.setState({mode: 'view'})}
				updateHost={this.updateHost} /> }  
				</React.Fragment>
				)
				
			}
			
		}
		