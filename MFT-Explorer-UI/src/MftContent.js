import React, { Component } from "react";

import Grid from "@material-ui/core/Grid";

import HomePage from "./components/HomePage";
import ContactPage from "./components/ContactPage";
import AgentsPage from "./components/AgentsPage";
import SchedulesPage from "./components/SchedulesPage";
import MonitorsPage from "./components/MonitorsPage";
import QueuesPage from "./components/QueuesPage";
import TransfersPage from "./components/TransfersPage";
import TransactionLogPage from "./components/TransactionLogPage";
import HostDetailsPage from "./components/HostDetailsPage";
import QueueManagersPage from "./components/QueueManagersPage";
import UserRolesPage from "./components/UserRolesPage";

export default class MftContent extends Component {
  state = {
    str: "Content"
  };
  getString = () => {
    this.setState({ str: JSON.stringify(this.props) });
  };

  render() {
    const project = () => {
      switch (this.props.location.pathname) {
        case "/home":
          return <ContactPage />;
        case "/agents":
          return <AgentsPage />;
        case "/schedules":
          return <SchedulesPage />;
        case "/queues":
          return <QueuesPage />;
        case "/monitors":
          return <MonitorsPage />;
        case "/transfers":
          return <TransfersPage />;
        case "/transactionlog":
          return <TransactionLogPage />;
        case "/hostdetails":
          return <HostDetailsPage />;
        case "/queuemanagers":
          return <QueueManagersPage />;
        case "/userroles":
          return <UserRolesPage />;
        default:
          return <HomePage />;
      }
    };

    return (
      <Grid container>
        <Grid item xs={1} />
        <Grid item style={{ paddingTop: 20, marginBottom:50 }} xs={10}>
          {project()}
        </Grid>
        <Grid item xs={1} />
      </Grid>
    );
  }
}
