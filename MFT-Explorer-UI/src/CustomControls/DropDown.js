import React, { Component } from "react";
import { TextField } from "@material-ui/core";
import classNames from "classnames";
import InputAdornment from "@material-ui/core/InputAdornment";
import MenuItem from "@material-ui/core/MenuItem";

const styles = {
}

export default class DropDown extends Component {
    state = {
        value: ''
    }
    componentDidMount () {
        let val = ''// this.props.value ? JSON.stringify(this.props.value) : '' 
        if (this.props.ValueField) {
            this.props.items.map((item,index) => {
                if(item[this.props.ValueField] == this.props.value) {
                    val = JSON.stringify(item)
                }
            })
        } else {
            if (this.props.TextField) {
                val = this.props.value ? JSON.stringify(this.props.value) : '' 
            } else {
                val = this.props.value ? this.props.value : '' 
            }
        }
        this.setState({
            value: val
        })
    }
    handleChange = () => e => {
        this.setState({
            value: e.target.value
        })
        if(this.props.onChange) {
            let obj = ''
            if (this.props.TextField) {
                obj = JSON.parse(e.target.value)
            } else {
                obj = e.target.value
            }
            if (this.props.ValueField) {
                this.props.onChange(obj[this.props.ValueField])
            } else {
                this.props.onChange(obj)
            }
        }
    }
    render() {
        const myVariant = this.props.variant ? this.props.variant : 'standard'
        return (
            <div style={{paddingBottom:5,...this.props.style}}  >
                <TextField
                    select
                    fullWidth
                    margin="normal"
                    variant= {myVariant}
                    label= {this.props.caption}
                    value={this.state.value}
                    onChange={this.handleChange()}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="end"> </InputAdornment>
                            )
                        }}
                    >
                    {this.props.items.map((item,index) => {
                        if (this.props.TextField) {
                            return <MenuItem key={index} value={JSON.stringify(item)}>
                                {item[this.props.TextField]}
                            </MenuItem>
                        } else {
                            return <MenuItem key={index} value={item}>
                                {item}
                            </MenuItem>
                        }
                    })}
                </TextField>
            </div>
        )
    }
}
