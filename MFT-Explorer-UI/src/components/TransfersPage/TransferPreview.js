import React, { Component } from 'react'
import Paper from '@material-ui/core/Paper'

export default class TransferPreview extends Component {

    state ={
        detaisl: {
        }
    }
    componentDidMount() {
        this.setState({
            details: this.props.getDetails()
        })
    }

    render() {
        return (
            <Paper>
                <pre>{JSON.stringify(this.state.details,null,4)}</pre>

            </Paper>
        )
    }
}
