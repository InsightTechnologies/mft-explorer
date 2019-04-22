import React from 'react'
import { IconButton, Table, TableBody, TableCell, TableHead, TableRow} from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';


const CustomTableCell = withStyles(theme => ({
    // head: {
    //     backgroundColor: '#00897b',
    //     color: theme.palette.common.white,
    //     fontSize: 12,
    // },
    body: {
        fontSize: 12,
    },
}))(TableCell);

// var clickHandler = e => {
//     if (typeof props.deleteHost === 'function') {
//         props.deleteHost(e.target.value);
//     }
// }

export default function HostList(props) {
    return (
        <div style={{overflowX: 'auto'}}>
            <Table>
                <TableHead>
                    <TableRow>
                        <CustomTableCell align="left">Name</CustomTableCell>
                        <CustomTableCell align="left">Coordination QMGR & Port</CustomTableCell>
                        {/* <CustomTableCell align="center">Coordination QMGR Port</CustomTableCell> */}
                        <CustomTableCell align="left">Agent QMGR & Port</CustomTableCell>
                        {/* <CustomTableCell align="center">Agent QMGR Port</CustomTableCell> */}
                        <CustomTableCell align="left">Channel</CustomTableCell>
                        {/* <CustomTableCell align="center">IP</CustomTableCell>                
                        <CustomTableCell align="center">Status</CustomTableCell> */}
                        <CustomTableCell align="right">Action</CustomTableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.hosts.map((row,index) => (
                        <TableRow key={row.hostId}>
                        <CustomTableCell component="th" scope="row">{row.hostName}</CustomTableCell>
                        <CustomTableCell scope="row">{row.coordQmgr} : {row.coordPort} </CustomTableCell>
                        {/* <CustomTableCell scope="row">{row.coordPort}</CustomTableCell> */}
                        <CustomTableCell scope="row">{row.agentQmgr} : {row.agentPort}</CustomTableCell>
                        {/* <CustomTableCell scope="row">{row.agentPort}</CustomTableCell> */}
                        <CustomTableCell align="left">{row.defaultChannel}</CustomTableCell>
                        {/* <CustomTableCell align="right">{row.hostIp}</CustomTableCell>                
                        <CustomTableCell align="right">{row.active}</CustomTableCell> */}
                        <CustomTableCell align="right">
                            <IconButton color="primary"  onClick={() => props.editHost(row) } >
                                <EditIcon />
                            </IconButton>
                            <IconButton color="secondary" onClick={() => props.deleteHost(row) } >
                                <DeleteIcon/>
                            </IconButton>
                        </CustomTableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </div>
    )
}
