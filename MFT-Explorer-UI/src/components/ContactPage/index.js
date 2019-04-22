import React, { Component } from 'react'
import {Typography, Grid} from '@material-ui/core'

export default class ContactPage extends Component {
  render() {

    return (
			<Grid container spacing={24}  >
					<Grid item xs={12} >
						<Typography variant='h4'>Contact</Typography>
					</Grid>
					<Grid item xs={12} style={{paddingLeft:15}}>
						<Typography variant='h5'>Phone</Typography>
						<Typography variant='body1' style={{paddingLeft:15}}>
								Main: +1-248-233-1100
						</Typography>
					</Grid>
					<Grid item xs={12} style={{paddingLeft:15}}>
						<Typography variant='h5'>Email</Typography>
						<Typography variant='body1' style={{paddingLeft:15}}>
								Support: supportmft@miraclesoft.com <br/>
								General: info@miraclesoft.com
						</Typography>
					</Grid>
					<Grid item xs={12} style={{paddingLeft:15}}>
						<Typography variant='h5'>Address</Typography>
						<Typography variant='body1' style={{paddingLeft:15}}>
								Arkansas Branch<br/>
								Miracle Software Systems, Inc.<br/>
								2601 North Walton Blvd<br/>
								Bentonville, AR 72712 <br/>
						</Typography>
					</Grid>
			</Grid>
    )    
  }  
}
