import React, { Component } from "react";
import { Table, TableBody, TableCell, TableHead, TableRow} from '@material-ui/core';
import { Dialog, DialogTitle, DialogActions, DialogContent} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import _ from 'lodash'
import FormControl from '@material-ui/core/FormControl'
import Input from '@material-ui/core/Input'
import FormLabel from '@material-ui/core/FormLabel'
import { IconButton } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import { hostname } from "os";

const styles = {
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
};

export default class QueuePutMessage extends Component {
    handelChange = input => e => {
        this.setState({[input]: e.target.value})
    }
	state = {
		show: false,
		queue: {},
        headers: [],
        body:'',
		hkey: '',
		hval: ''
	}
	Close = () => {
		this.setState({
			show: false
		});
	};
    addHeader = () => {
        var headers = this.state.headers
        headers.push({key: this.state.hkey, value: this.state.hval})
        this.setState({
            ...this.state,
            headers: headers,
            hkey: '',
            hval: ''
        })
    }
    deleteHeader = ind => {
        var headers = this.state.headers
        headers.splice(ind,1)
        this.setState({
            ...this.state,
            headers: headers
        })
    }
    submit = () => {
        let headers = {}
        this.state.headers.map((head,ind) => {
            headers[head.key] = head.value
		})
        this.props.onSubmit(
			this.state.headers.length>0 ?
			{queueName: this.state.queue.queueName,headers:headers,body:this.state.body}
			: {queueName: this.state.queue.queueName,body:this.state.body}
        )
    }
	Show = (queue) => {
		this.setState({
            queue: queue,
            headers: [],
            body:'',
            hkey: '',
            hval: '',
			show: true
		});
	};
	render() {
		const { classes } = this.props
		return (
			<Dialog fullWidth open={this.state.show} onClose={this.Close}>
				<DialogTitle>{this.state.queue.queueName} : Put Message</DialogTitle>
				<DialogContent>
					<form className={styles.container}  noValidate autoComplete="off">
						<FormControl fullWidth>
							<FormLabel component="legend">Body</FormLabel>
                            <textarea rows='10'
                                id="msg-body"
                                label="Message body"
                                onChange = {this.handelChange('body')}
                                value = {this.state.body} />
						</FormControl>
					</form>
					<Table style={{display: 'none'}}>
						<TableHead>
							<TableRow>
								<TableCell>Header</TableCell>
								<TableCell>Value</TableCell>
                                <TableCell></TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{this.state.headers.map((row,index) => {
								return <TableRow key={index}>
									<TableCell scope="row">{row.key}</TableCell>
									<TableCell>{row.value}</TableCell>
                                    <TableCell>
                                    <IconButton color="secondary" onClick={() => this.deleteHeader(index)} >
                                        <DeleteIcon/>
                                    </IconButton>
                                    </TableCell>
								</TableRow>
							} )}
							<TableRow>
								<TableCell>
									<Input
										id="key-name"
										label="Header Key"
										onChange = {this.handelChange('hkey')}
                                        value = {this.state.hkey}
									/>
								</TableCell>
								<TableCell>
									<Input
										id="value-name"
										label="Header value"
										onChange = {this.handelChange('hval')}
										value = {this.state.hval}
									/>
								</TableCell>
                                <TableCell>
                                    <IconButton color="primary"
                                        disabled={!this.state.hkey}
                                        onClick={this.addHeader} >
                                        <AddIcon/>
                                    </IconButton>
                                </TableCell>
							</TableRow>
						</TableBody>
					</Table>
				</DialogContent>
				<DialogActions>
					<Button onClick={this.Close} color="primary">
						Close
					</Button>
                    <Button variant="contained"
                        onClick={this.submit} color="primary"
                        disabled={!this.state.body} >
						Submit
					</Button>
				</DialogActions>
			</Dialog>
		);
	}
}