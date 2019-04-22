
import React, { Component } from 'react'


import Button from '@material-ui/core/Button'
import { withStyles } from '@material-ui/core/styles'

import Checkbox from '@material-ui/core/Checkbox'
import FormControl from '@material-ui/core/FormControl'
import FormControlLabel  from '@material-ui/core/FormControlLabel'
import Input from '@material-ui/core/Input'
import FormLabel from '@material-ui/core/FormLabel'
import DropDown from "../../CustomControls/DropDown";

const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        margin: theme.spacing.unit*2,
        marginRight: theme.spacing.unit*3        
    },
    button: {
        margin: theme.spacing.unit*2,
        width: 150
    },
    input: {
        display: 'none',
    },
});

class ManagerItem extends Component {
    selectHost = (host) => {
		this.setState({
			...this.state,
			hostId: host
        })
    }
    handelChange = input => e => {
        this.setState({[input]: e.target.value})
    }
    handleCheckChange = (key) => e => {
        this.setState({[key]: e.target.checked ? 'Y' : 'N'})
    }
    state = {

    }
    componentWillMount () {
        this.setState({...this.props.manager})
    }
    render() {
        const { classes } = this.props
        return (
            <div>
                <form className={classes.container}  noValidate autoComplete="off">
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">QM Name</FormLabel>
                        <Input
                            id="qm-name"
                            label="QM NAme"
                            onChange = {this.handelChange('qmName')}
                            value = {this.state.qmName}
                        />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">QM Channel</FormLabel>
                    <Input
                        id="qm-channel"
                        label="QM Channel"
                        style={{width: 400}}
                        onChange = {this.handelChange('qmChannel')}
                        value = {this.state.qmChannel}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">QM Port</FormLabel>
                    <Input
                        id="qm-port"
                        label="QM Port"
                        onChange = {this.handelChange('qmPort')}
                        value = {this.state.qmPort}
                    />
                    </FormControl>
                    
                    <FormControl className={classes.formControl}>
                        <FormLabel component="legend">Coordination QM</FormLabel>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    id = 'qm-coord'
                                    checked={this.state.isCoord === 'Y'}
                                    onChange={this.handleCheckChange('isCoord')}
                                    value={this.state.isCoord}
                                    color="primary"
                                />
                            }
                            />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                        <FormLabel component="legend">Command QM</FormLabel>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    id = 'qm-cmnd'
                                    checked={this.state.isCmnd === 'Y'}
                                    onChange={this.handleCheckChange('isCmnd')}
                                    value={this.state.isCmnd}
                                    color="primary"
                                />
                            }
                            />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                        <FormLabel component="legend">Agent QM</FormLabel>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    id = 'qm-agnt'
                                    checked={this.state.isAgent === 'Y'}
                                    onChange={this.handleCheckChange('isAgent')}
                                    value={this.state.isAgent}
                                    color="primary"
                                />
                            }
                            />
                    </FormControl>
                    
                    <FormControl className={classes.formControl}>
                        <FormLabel component="legend">Status</FormLabel>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    id = 'host-active'
                                    checked={this.state.isActive === 'Y'}
                                    onChange={this.handleCheckChange('isActive')}
                                    value={this.state.isActive}
                                    color="primary"
                                />
                            }
                            />     
                    </FormControl>
                    
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">User ID</FormLabel>
                    <Input 
                        id="user-id"
                        label="User ID"
                        onChange = {this.handelChange('qmUserid')}
                        value = {this.state.qmUserid}
                    />
                    </FormControl>
                    
                    <DropDown items={this.props.hosts}
                        style={{width: 200}}
                        value={this.state.hostId}
                        caption='QM Host' 
                        TextField="hostName" 
                        ValueField="hostId"
                        onChange={this.selectHost} /> 
                </form>
                <div align='center'>
                    <Button  variant="contained" className={classes.button} 
                        onClick={() => this.props.onCancel()}>Cancel</Button>
                    <Button variant="contained" color="primary" className={classes.button}
                        onClick={() => this.props.updateManager(this.state)}> Save </Button>
                </div>
            </div>
        )
    }
}
module.exports = withStyles(styles)(ManagerItem)
