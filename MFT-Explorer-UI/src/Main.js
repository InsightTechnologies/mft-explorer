import React, { Component, Fragment } from 'react'
import MftHeader from './MftHeader'
import MftContent from './MftContent'
import MftFooter from './MftFooter'
import CssBaseline from '@material-ui/core/CssBaseline';

export default class Main extends Component {

  render() {
    return (
      <Fragment>
        <CssBaseline />
        <MftHeader />
        <MftContent {...this.props} />
        <MftFooter />
      </Fragment>
    )
  }
}
