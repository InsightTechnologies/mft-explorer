import React, { Component } from "react";
import Utils from "../../Utils";
import { Endpoints } from "../../Constants";
import RestClient from "../../RestClient";

import SelectManager from './SelectManager'
import QueueList from './QueueList'
import QueueProperties from './QueueProperties'
import QueuePutMessage from './QueuePutMessage'
import QueueBrowseMessages from './QueueBrowseMessages'

//import { IconButton,Table,TableCell,TableHead, TableRow } from "@material-ui/core";
import {Typography, Grid} from "@material-ui/core";
import AddIcon from "@material-ui/icons/Add";
import RefreshIcon from "@material-ui/icons/Refresh";

export default class QueuesPage extends Component {

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
			this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
	}

	fetchQueues = type => {
		if (this.state.qm === '') return
		this.refs.utils.Loading(true)
		const qm = JSON.parse(this.state.qm)
		const url = `${Endpoints.Queues}/${type}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}`
		RestClient.Get(url,{},
		(data) => {
			if (data.Success) {
				this.setState({
					...this.state,
					type: type,
					queues: data.Result
				})
			} else {
				this.refs.utils.Error('Failed to fetch queue details')
			}
			this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
	}

	fetchProperties = (queue) => {
		if (this.state.qm === '') return
		this.refs.utils.Loading(true)
		const qm = JSON.parse(this.state.qm)
		const url = `${Endpoints.Queues}/properties/${queue.queueName.trim()}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}`
		RestClient.Get(url,{},
		(data) => {
			if (data.Success) {
				this.refs.queueProperties.Show(queue,data.Result)
			} else {
				this.refs.utils.Error('Failed to fetch queue properties')
			}
			this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
	}

	showMessageInput = (queue) => {
		this.refs.queuePutMessage.Show(queue,{})
	}

	putMessage = message => {
		if (this.state.qm === '') return
		this.refs.utils.Loading(true)
		const qm = JSON.parse(this.state.qm)
		let url = message.headers ?
				`${Endpoints.Queues}/puth/${message.queueName.trim()}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}`
				:`${Endpoints.Queues}/put/${message.queueName.trim()}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}` 
		RestClient.Put(url,
			message.body,
			(data) => {
				if (data.Success) {
					this.refs.utils.Success("Message posted successfully")
					this.refs.queuePutMessage.Close()
					setTimeout(() => {
						this.fetchQueues(this.state.type)
					}, 200);
				} else {
					this.refs.utils.Error('Failed to fetch queue properties')
				}
				this.refs.utils.Loading(false)
			},
			(error) => {
				this.refs.utils.Error(error)
				this.refs.utils.Loading(false)
			},
			message.headers
		)
	}

	browseMessages = (queue,start,end) => {
		if (this.state.qm === '') return
		if (!start) start = 1
		if (!end) end = 10
		this.refs.utils.Loading(true)
		const qm = JSON.parse(this.state.qm)
		const url = `${Endpoints.Queues}/browseMessages/${queue.queueName.trim()}/${start}/${end}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}`
		RestClient.Get(url,{},
		(data) => {
			if (data.Success) {
				this.refs.queueBrowseMessages.Show(queue,data.Result, queue.queueDepth, start, end)
			} else {
				this.refs.utils.Error('Failed to fetch messages from queue')
			}
			this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
	}

	managerSelected = manager => {
		this.setState({qm: manager})
	}

	state = {
		managers: [],
		queues: [],
		mode: 'view',
		qm: ""
	}	

	componentDidMount () {
		this.fetchManagers()
	}

	render() {
		return (
			<React.Fragment>
				<Utils ref="utils" />
				<Grid container spacing={24} alignItems="flex-end" alignContent="stretch" >
					<Grid item md={6}>
						<Typography variant="h5">Queues</Typography>
					</Grid>
				</Grid>
				<SelectManager qm={this.state.qm} managers={this.state.managers} 
					managerSelected={this.managerSelected} fetchQueues={this.fetchQueues}  />
				{ this.state.queues.length > 0 &&
				<QueueList queues={this.state.queues} fetchProperties={this.fetchProperties} 
				putMessage={this.showMessageInput} browseMessages={this.browseMessages}
				/> }
				<QueueProperties ref="queueProperties" />
				<QueuePutMessage ref="queuePutMessage" onSubmit={this.putMessage} />
				<QueueBrowseMessages ref="queueBrowseMessages" browseMessages={this.browseMessages} />
			</React.Fragment>
		);
	}
}
