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
//     if (typeof props.deleteManager === 'function') {
//         props.deleteManager(e.target.value);
//     }
// }

export default function ManagerList(props) {
    return (
        <div style={{overflowX: 'auto'}}>
            <Table>
                <TableHead>
                    <TableRow>
                        <CustomTableCell align="left">Name</CustomTableCell>
                        <CustomTableCell align="left">Channel</CustomTableCell>
                        <CustomTableCell align="left">Port</CustomTableCell>
                        <CustomTableCell align="left">User ID</CustomTableCell>
                        <CustomTableCell align="left">Type</CustomTableCell>
                        <CustomTableCell align="left">Active</CustomTableCell>
                        <CustomTableCell align="right">Action</CustomTableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.managers.map((qm,index) => (
                        <TableRow key={qm.qmid}>
                        <CustomTableCell component="th" scope="row">{qm.qmName}</CustomTableCell>
                        <CustomTableCell align="left">{qm.qmChannel}</CustomTableCell>
                        <CustomTableCell align="left">{qm.qmPort}</CustomTableCell>
                        <CustomTableCell align="left">{qm.qmUserid}</CustomTableCell>
                        <CustomTableCell align="left">
                            { qm.isCoord == 'Y' && <b>COORD </b> }
                            { qm.isCmnd == 'Y' && <b>CMND </b> }
                            { qm.isAgent == 'Y' && <b>AGENT </b> }
                        </CustomTableCell>                        
                        <CustomTableCell align="left">
                        { qm.isActive == 'Y' && <b>Active </b> }
                        { qm.isActive != 'Y' && <b>In-active </b> }
                        </CustomTableCell>
                        <CustomTableCell align="right">
                            <IconButton color="primary"  onClick={() => props.editManager(qm) } >
                                <EditIcon />
                            </IconButton>
                            <IconButton color="secondary" onClick={() => props.deleteManager(qm) } >
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
