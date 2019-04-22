import React, { Component } from 'react'


import Button from '@material-ui/core/Button'
import { withStyles } from '@material-ui/core/styles'

import Checkbox from '@material-ui/core/Checkbox'
import FormControl from '@material-ui/core/FormControl'
import FormControlLabel  from '@material-ui/core/FormControlLabel'
import Input from '@material-ui/core/Input'
import FormLabel from '@material-ui/core/FormLabel'

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

class HostItem extends Component {

    handelChange = input => e => {
        this.setState({[input]: e.target.value})
    }
    handleactiveChange = () => e => {
        this.setState({['active']: e.target.checked ? 'Y' : 'N'})
    }
    state = {

    }
    componentWillMount () {
        this.setState({...this.props.host})
    }
    render() {
        const { classes } = this.props
        return (
            <div>
                <form className={classes.container}  noValidate autoComplete="off">
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Host Name</FormLabel>
                        <Input
                            id="host-name"
                            label="Host Name"
                            onChange = {this.handelChange('hostName')}
                            value = {this.state.hostName}
                            // aria-describedby="component-helper-text"
                        />
                        {/* <FormHelperText id="component-helper-text">Some important helper text</FormHelperText> */}
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Host IP</FormLabel>
                    <Input
                        id="host-ip"
                        label="Host IP"
                        onChange = {this.handelChange('hostIp')}
                        value = {this.state.hostIp}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Default  Channel</FormLabel>
                    <Input
                        id="default-channel"
                        label="Default Channel"
                        onChange = {this.handelChange('defaultChannel')}
                        value = {this.state.defaultChannel}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Coordination QMgr</FormLabel>
                    <Input
                        id="coord-qmgr"
                        label="Coordination QMgr"
                        onChange = {this.handelChange('coordQmgr')}
                        value = {this.state.coordQmgr}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Coordination QMgr Port</FormLabel>
                    <Input
                        id="coord-qmgr-port"
                        label="Coordination QMgr Port"
                        onChange = {this.handelChange('coordPort')}
                        value = {this.state.coordPort}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Agent QMgr</FormLabel>
                    <Input
                        id="agent-qmgr"
                        label="Agent QMgr"
                        onChange = {this.handelChange('agentQmgr')}
                        value = {this.state.agentQmgr}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">Agent QMgr Port</FormLabel>
                    <Input
                        id="agent-qmgr-port"
                        label="Agent QMgr Port"
                        onChange = {this.handelChange('agentPort')}
                        value = {this.state.agentPort}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                    <FormLabel component="legend">User ID</FormLabel>
                    <Input 
                        id="user-id"
                        label="User ID"
                        onChange = {this.handelChange('userId')}
                        value = {this.state.userId}
                    />
                    </FormControl>
                    <FormControl className={classes.formControl}>
                        <FormLabel component="legend">Status</FormLabel>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    id = 'host-active'
                                    checked={this.state.active === 'Y'}
                                    onChange={this.handleactiveChange()}
                                    value={this.state.active}
                                    color="primary"
                                />
                            }
                            />     
                    </FormControl>
                </form>
                <div align='center'>
                    <Button  variant="contained" className={classes.button} 
                        onClick={() => this.props.onCancel()}>Cancel</Button>
                    <Button variant="contained" color="primary" className={classes.button}
                        onClick={() => this.props.updateHost(this.state)}> Save </Button>
                </div>
            </div>
        )
    }
}
module.exports = withStyles(styles)(HostItem)
