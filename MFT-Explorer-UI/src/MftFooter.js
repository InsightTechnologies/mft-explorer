import React, { Component } from 'react'
import { Link as RouterLink } from 'react-router-dom'
import Link from '@material-ui/core/Link';

import Typography from '@material-ui/core/Typography';

export default class MftFooter extends Component {
  render() {
    return (
    <div  style={{
        position: 'fixed',
        bottom: '0',
        width: '100%',
        margin: 'auto',
        padding: '5px',
        backgroundColor: '#bbb'
        }} >
        <span>&copy; {new Date().getFullYear()} Copyright: </span>
        <a target="_blank" color="primary" href="https://www.miraclesoft.com">
                Miracle Software Systems
        </a>
            {/* <Link target="_blank" color="primary" href="https://www.miraclesoft.com">
                Miracle Software Systems
            </Link> */}
        {/* <Typography variant="caption" align="center">
            &copy; {new Date().getFullYear()} Copyright:    
            <Link target="_blank" color="primary" href="https://www.miraclesoft.com">
                Miracle Software Systems
            </Link>
        </Typography> */}
        
      </div>
    )
  }
}
