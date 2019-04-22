import React, { Component } from 'react';
import { AppBar, Toolbar, Typography, IconButton, Menu } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import { createStyles, withStyles } from '@material-ui/core/styles'; 
import PropTypes from 'prop-types';
import MenuItem from '@material-ui/core/MenuItem';
import {Link} from 'react-router-dom'

import MoreIcon from '@material-ui/icons/MoreVert';
import HomeIcon from '@material-ui/icons/Home';
import PeopleIcon from '@material-ui/icons/People';
import DesktopMacIcon from '@material-ui/icons/DesktopMac';
import SwapHorizIcon from '@material-ui/icons/SwapHoriz';
import LinkIcon from '@material-ui/icons/Link';
import ListAltIcon from '@material-ui/icons/ListAlt';
import SettingsIcon from '@material-ui/icons/Settings';

const styles = {
  root: {
    flexGrow: 1,
  },
  grow: {
    flexGrow: 0.5,
  },
  menuButton: {
    marginLeft: -40,
    marginRight: 40,
    flexGrow: 0.4,
  },
  icon: {
    paddingRight: 5,
    fontSize: 20
  }
};

// function MftHeader(props) {
  class MftHeader extends React.Component {

  state = {
    anchorEl: null,
    mobileMoreAnchorEl: null,
  };

  handleProfileMenuOpen = event => {
    this.setState({ anchorEl: event.currentTarget });
  };
  handleTransferMonitorMenuOpen = event => {
    this.setState({ anchorTM: event.currentTarget });
  };

  handleMenuClose = () => {
    this.setState({ anchorEl: null, anchorTM: null });
    this.handleMobileMenuClose();
  };

  handleMobileMenuOpen = event => {
    this.setState({ mobileMoreAnchorEl: event.currentTarget });
  };

  handleMobileMenuClose = () => {
    this.setState({ mobileMoreAnchorEl: null });
  };

  render() {
    const { classes } = this.props;
    const { anchorEl,anchorTM, mobileMoreAnchorEl } = this.state;
    const isMenuOpen = Boolean(anchorEl);
    const isTMMenuOpen = Boolean(anchorTM);
    const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

    const renderMenu = (
      <Menu
        anchorEl={anchorEl}
        anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        open={isMenuOpen}
        onClose={this.handleMenuClose}
      >
        <MenuItem onClick={this.handleMenuClose}
        component={Link} to='/hostdetails'>Host Details</MenuItem>
        <MenuItem onClick={this.handleMenuClose}
        component={Link} to='/queuemanagers'>Queue Managers</MenuItem>
        {/* <MenuItem onClick={this.handleMenuClose}
        component={Link} to='/userroles'>User Roles</MenuItem> */}
      </Menu>
    );
    const renderTransfersMenu = (
      <Menu
        anchorEl={anchorTM}
        anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        open={isTMMenuOpen}
        onClose={this.handleMenuClose}
      >
        <MenuItem onClick={this.handleMenuClose}
        component={Link} to='/monitors'>Monitor Details</MenuItem>
        <MenuItem onClick={this.handleMenuClose}
        component={Link} to='/schedules'>Scheduled Transfers</MenuItem>
        <MenuItem onClick={this.handleMenuClose}
        component={Link} to='/transfers'>Create / Update</MenuItem>
      </Menu>
    );

    // const renderMobileMenu = (
    //   <Menu
    //     anchorEl={mobileMoreAnchorEl}
    //     anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
    //     transformOrigin={{ vertical: 'top', horizontal: 'right' }}
    //     open={isMobileMenuOpen}
    //     onClose={this.handleMenuClose}
    //   >
    //     <MenuItem onClick={this.handleMobileMenuClose}>
    //       <IconButton color="inherit">
    //       mAILiCON
    //       </IconButton>
    //       <p>Messages</p>
    //     </MenuItem>
    //     <MenuItem onClick={this.handleMobileMenuClose}>
    //       <IconButton color="inherit">
    //       NotificationsIcon
    //       </IconButton>
    //       <p>Notifications</p>
    //     </MenuItem>
    //     <MenuItem onClick={this.handleProfileMenuOpen}>
    //       <IconButton color="inherit">
    //       AccountCircle
    //       </IconButton>
    //       <p>Profile</p>
    //     </MenuItem>
    //   </Menu>
    // );


    return (
      <div className={classes.root}>
        <AppBar position="static" color='primary'>
          <Toolbar>

            <IconButton className={classes.menuButton}
            component={Link} to='/'
            color="inherit" aria-label="MFT Explorer">
            <Typography variant="h5" color="inherit" className={classes.grow} >
              MFT Explorer
            </Typography>
            </IconButton>
            
              <div>
                <IconButton color="inherit" 
                component={Link} to='/home'>
                <HomeIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Home</Typography>
                </IconButton>
                <IconButton color="inherit"
                component={Link} to='/agents'>
                  <PeopleIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Agents</Typography>
                </IconButton>
                {/* <IconButton color="inherit"
                component={Link} to='/monitors'>
                  <DesktopMacIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Monitors</Typography>
                </IconButton> */}
                <IconButton color="inherit"
                component={Link} to='/queues'>
                <LinkIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Queues</Typography>
                </IconButton>
                {/* <IconButton color="inherit"
                component={Link} to='/transfers'>
                <SwapHorizIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Transfers / Monitors</Typography>
                </IconButton> */}
                <IconButton
                  aria-owns={isTMMenuOpen ? 'material-appbar' : undefined}
                  aria-haspopup="true"
                  onClick={this.handleTransferMonitorMenuOpen}
                  color="inherit"
                >
                  <DesktopMacIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Transfers / Monitors</Typography>
                </IconButton>
                <IconButton color="inherit"
                component={Link} to='/transactionlog'>
                <ListAltIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Transaction Log</Typography>
                </IconButton>
                <IconButton
                  aria-owns={isMenuOpen ? 'material-appbar' : undefined}
                  aria-haspopup="true"
                  onClick={this.handleProfileMenuOpen}
                  color="inherit"
                >
                  <SettingsIcon className={classes.icon} /> <Typography variant="button" color="inherit"> Configuration</Typography>
                </IconButton>
              </div>
              
              {/* <div className={classes.sectionMobile}>
                <IconButton color="inherit">
                  <MoreIcon />
                </IconButton>
              </div> */}

          </Toolbar>
        </AppBar>
        {renderMenu}
        {renderTransfersMenu}
        {/* {renderMobileMenu} */}
      </div>
    );
  }
}
MftHeader.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MftHeader);

