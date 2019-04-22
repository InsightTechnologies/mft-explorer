import React, { Component } from 'react'
import { Switch, Route } from 'react-router'
import Main from './Main'

export default class App extends Component {
  render() {
    return (
          <Switch>
              <Route path="/" render={props => (
                <Main {...props} />
              )} />
          </Switch>        
    )
  }
}
