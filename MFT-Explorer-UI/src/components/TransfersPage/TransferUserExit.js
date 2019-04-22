import React, { Component } from "react";
import { Grid, Typography} from '@material-ui/core';
import { Dialog, DialogTitle, DialogActions, DialogContent} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import _ from 'lodash'
import TextField from '@material-ui/core/TextField'
import Input from '@material-ui/core/Input'
import FormLabel from '@material-ui/core/FormLabel'
import { IconButton,MenuItem } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import ArrowDropDown from '@material-ui/icons/ArrowDropDown';

import DropDown from '../../CustomControls/DropDown'
import TablePaginationActions from "@material-ui/core/TablePagination/TablePaginationActions";

const styles = {
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
};

export default class TransferUserExit extends Component {
    handelChange = input => e => {
        this.setState({[input]: e.target.value})
    }
	state = {
        error: false,
		show: false,
		type: '',
        headers: [],
        exit_selected: {},
        keys: [],
	}
	Close = () => {
		this.setState({
			show: false
		});
	};

    submit = () => {
        let valid = true
        let paramStr = this.state.exit_selected.Name
        //this.state.keys.map((key,ind) {
        for (let i=0;i<this.state.keys.length; i++) {
            let key = this.state.keys[i]
            if (!key.default_value || key.default_value === '') {
                valid  = false 
            } else {
                paramStr = paramStr + '|' + this.props.processUserExitValue(key.default_value)
            }
        }
        if (!valid)
        {
            this.setState({
                ...this.state,
                error: true
            })
        } else {
            this.props.onSubmit({type: this.state.type, paramStr: paramStr})
            this.Close()
        }
    }
	Show = (type,exit) => {
		this.setState({
            error: false,
            show: true,
            type: type,
            headers: [],
            exit_selected: {},
            keys: [],
        });
    };
    handlePropChange =(prop,value) => {
        this.setState({
            ...this.state,
            [prop]: value
        })
    }
    handleChange = ind => e => {
        this.setExitPropValue(ind,e.target.value)
    }
    exitSelected = exit => {
        this.setState({
            ...this.state,
            exit_selected: exit,
            keys: exit.Parameter
        })
    }
    setExitPropValue = (ind,val) => {
        let keys = this.state.keys
        keys[ind].default_value = val
        this.setState({
            ...this.state,
            keys: keys
        })
    }
	render() {
		const { classes } = this.props
		return (
			<Dialog fullWidth open={this.state.show} onClose={this.Close}>
				<DialogTitle>User Exit: {this.state.type}</DialogTitle>
				<DialogContent>
                    <DropDown items={this.props.exits}
                        value={this.state.exit_selected}
                        caption='User Exit' 
                        TextField="Name" 
                        onChange={(val) => this.exitSelected(val)} /> 

                    {this.state.keys.length > 0 &&
                    <div style={{padding:20}}>
                        {this.state.keys.map((row,index) => {
                            return <Grid container spacing={0} key={index}>
                            <Grid item xs={10}>
                                <TextField
                                    fullWidth
                                    label={row.name}
                                    placeholder={row.name}
                                    variant="standard"
                                    margin="none"
                                    InputLabelProps={{ shrink: true }}
                                    value={row.default_value}
                                    onChange={this.handleChange(index)} /> 
                            </Grid>
                            <Grid item xs={1}>
                            { row.value &&
                                <TextField
                                    select
                                    fullWidth
                                    margin="normal"
                                    variant= "standard"
                                    //label= {this.props.caption}
                                    value="--"
                                    onChange={this.handleChange(index)}
                                    >
                                    {row.value.map((item,index) => {
                                        return <MenuItem key={index} value={item}>
                                            {item}
                                        </MenuItem>
                                    })}
                                </TextField>
                            }
                            </Grid>
                            </Grid>
                        } )}
                    </div>
                    }
                </DialogContent>
				<DialogActions>
                    { this.state.error &&
                    <Typography color="error" variant="caption">Please enter all params</Typography>}
					<Button onClick={this.Close} color="primary">
						Close
					</Button>
                    <Button variant="contained"
                        onClick={this.submit} color="primary"
                        disabled={!this.state.exit_selected.Name } >
						Submit
					</Button>
				</DialogActions>
			</Dialog>
		);
	}
}