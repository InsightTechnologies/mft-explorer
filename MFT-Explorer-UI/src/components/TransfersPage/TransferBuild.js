import React, { Component } from 'react'
import {TextField, Button, Grid, IconButton, Typography} from "@material-ui/core"
import AddIcon from "@material-ui/icons/Add"
import ClearIcon from "@material-ui/icons/Clear"
import Utils from "../../Utils";
import { Endpoints } from "../../Constants"
import RestClient from "../../RestClient"

import {Radio, RadioGroup, FormControl, FormLabel, FormControlLabel } from '@material-ui/core';
import TransferUserExit from './TransferUserExit'
import DropDown from '../../CustomControls/DropDown'
import DateTimePicker from '../../CustomControls/DateTimePicker'

const UserExits = [
	{
		Name: "ARCHIVE",
		pre_src: "true",
		post_src: "true",
		pre_dest: "true",
		post_dest: "true",
		Parameter: [
		{
			name: "SuccessPath",
			required: "true",
			default_value: "%(SoureDirectory)_success/#{FileName}_#{TS11}%"
		},
		{
			name: "OPERATOR",
			required: "true",
			default_value: "COPY",
			value: ["MOVE", "COPY"]
		},
		{
			name: "SourceFileName",
			required: "true",
			default_value: "#{SPATH}"
		}
		]
	},
	{
		Name: "COMPRESS",
		pre_src: "true",
		post_src: "true",
		pre_dest: "true",
		post_dest: "true",
		Parameter: [
		{
			name: "TargetPath",
			required: "true",
			default_value: ""
		},
		{
			name: "CompressFormat",
			required: "true",
			default_value: "gz",
			value: ["ZIP", "gz"]
		}
		]
	},
	{
		Name: "FILEINFOTOQUEUE",
		pre_src: "true",
		post_src: "true",
		pre_dest: "true",
		post_dest: "true",
		Parameter: [
		{
			name: "SourceFilePath",
			required: "true",
			default_value: "${FilePath}"
		},
		{
			name: "TargetDirectory",
			required: "true",
			default_value: ""
		},
		{
			name: "SourceFileName",
			required: "true",
			default_value: "${FileName}"
		}
		]
	},
	{
		Name: "ARCHIVE_DUMMY",
		pre_src: "true",
		post_src: "true",
		pre_dest: "true",
		post_dest: "true",
		Parameter: [
		{
			name: "SuccessPath",
			required: "true",
			default_value: "%(SoureDirectory)_success/#{FileName}_#{TS11}%"
		},
		{
			name: "TargetPath",
			required: "true",
			default_value:
			"%(/SoureDirectory)/trg_eai_out/#{FileName}_#{TS11}%"
		},
		{
			name: "OPERATOR",
			required: "true",
			default_value: "COPY",
			value: ["MOVE", "COPY"]
		},
		{
			name: "SourceFileName",
			required: "true",
			default_value: "#{SPATH}"
		}
		]
	}
]
    
const styles = {
    button: {
        margin: 5
    },
    radioGroup: {
        marginLeft: 5,
    },
}

const enums = {
    PollFrequencys: [
        { Poll: 'seconds' },
        { Poll: 'minutes' },
        { Poll: 'hours' },
        { Poll: 'days' }
    ],
    PatternTypes: [
        { Type: 'Wildcard' },
        { Type: 'Regular expression' }
    ],
    CheckSums: [
        { Check: 'None' },
        { Check: 'MD5' }
    ],
    TransTypes: [
        { Type: 'File' },
        { Type: 'Directory' },
        { Type: 'Data set' },
        { Type: 'Queue' }
    ],
    FileSizeBytes: [
        { Size: 'byte', Value: 'B' },
        { Size: 'Kbyte', Value: 'KB' },
        { Size: 'Mbyte', Value: 'MB' },
        { Size: 'Gbyte', Value: 'GB' }
    ],
    TimeBases: [
        { Base: 'admin' },
        { Base: 'source' },
        { Base: 'UTC' }
    ]
}

let TransDetails = {
    Monitor_ID: null,
    Monitor_Name: null,
    Src_QueMgr: null,
    Src_HostName: null,
    Src_HostIP: null,
    Src_Channel: null,
    Src_Port: null,
    Src_UserID: null,
    //SelSrc_QueMgr: null,
    Trgt_QueMgr: null,
    Src_Agent: null,
    Trgt_Agent: null,
    Resource_Type: 'directory',
    Resource_Info: null,
    Poll_Interval: 1,
    Poll_Frequency: enums.PollFrequencys[1].Poll,
    M_Pattern: '*',
    E_Pattern: null,
    Pattern_Type: enums.PatternTypes[0].Type,
    Trg_Condition: 'fileMatch',
    FileSizeB: null,
    Polls: null,
    Transfer_Mode: 'binary',
    Src_File: null,
    Trgt_File: null,
    Src_Disposition: 'leave',
    Trgt_Exists: 'error',
    Pre_Src: '',
    Post_Src: '',
    Pre_Dest: '',
    Post_Dest: '',
    Job_Name: null,
    Reply_Queue: null,
    Transfer_Priority: 1,
    Checksums: enums.CheckSums[1].Check,
    Source_Type: enums.TransTypes[0].Type,
    Destination_Type: enums.TransTypes[0].Type,
    Schedule: {
        Timebase: enums.TimeBases[0].Base,
        Frequency: enums.PollFrequencys[1].Poll,
        Interval: 1,
        Starttime: null,
        Endtime: null
    }
}



export default class TransferBuild extends Component {

    parseAgent = input => {
        var output = {}
        try {            
            const agt = JSON.parse(input).properties.entry
            agt.map(item => {
                output[item.key] = item.content
            })
        } catch (error) {
            alert(JSON.stringify(error))
        }
        return output
    }

    fetchAgents = (managers) => {
        let qm = managers.filter(obj => {
            return obj.isCoord == 'Y'
        })[0]
        const url = `${Endpoints.Agents}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}`
		this.refs.utils.Loading(true)
		RestClient.Get(url,{},
            data => {
                if (data.Success) {
                    let agents=[];
                    for(let i=0;i<data.Result.length;i++){
                        agents.push(this.parseAgent(data.Result[i]));
                    }
                this.setState({
                    ...this.state,
                    agents: agents,
                    managers: managers,
                })
                } else {
                    this.refs.utils.Error('Failed to fetch agent details')
                }
                this.refs.utils.Loading(false)
            },
            error => {
                this.refs.utils.Error( error)
                this.refs.utils.Loading(false)
            }
        );
    }
    fetchManagers = () => {
		this.refs.utils.Loading(true)
		RestClient.Get(Endpoints.QueueManagers,{},
		(data) => {
			if (data.Success) {
                this.fetchAgents(JSON.parse(data.Result).body)
				// this.setState({
                //     ...this.state,
				// 	managers: JSON.parse(data.Result).body
				// })
			} else {
				this.refs.utils.Error('Failed to fetch queue managers')
			}
			//this.refs.utils.Loading(false)
		},
		(error) => {
			this.refs.utils.Error(error)
			this.refs.utils.Loading(false)
		})
    }

    fetchQueues = type => {
        if (this.state.Src_QueMgr === '') return
        this.refs.utils.Loading(true)
        const qm = this.state.Src_QueMgr
        const url = `${Endpoints.Queues}/${type}/${qm.host_ip}:${qm.qmChannel}:${qm.qmPort}`
        RestClient.Get(url,{},
        (data) => {
            if (data.Success) {
                this.setState({
                    ...this.state,
                    queues: data.Result
                })
            } else {
                this.refs.utils.Error('Failed to fetch queue details')
            }
            this.refs.utils.Loading(false)
        },
        (error) => {
            this.refs.utils.Error(error)
            this.refs.utils.Loading(false)
        })
    }
    
    

    state = {
        step: 0,
        managers: [],
        agents: [],
        queues: [],
        src_agents: [],
        trgt_agents: [],
        resource_type: 'directory',
        resource_info: '',
        m_pattern: '*',
        e_pattern: '',
        pattern_type: 'Wildcard',
        poll_interval: 1,
        poll_frequency: 'minutes',
        trg_condition: '',
        transfer_mode: 'binary',
        source_type: 'File',
        src_file: '',
        Src_disposition: 'leave',
        destination_type: 'File',
        trgt_file: '',
        Trgt_exists: 'error',
        job_name: '',
        transfer_priority: 1,
        checksum: 'MD5',
        reply_queue: '',
        pre_src: '',
        post_src: '',
        pre_dest: '',
        post_dest: '',
        startDateTime: new Date().toISOString().substring(0, 11) + "00:00",
        endDateTime: new Date().toISOString().substring(0, 11) + "23:59"
    }

    getDetails = () => {
        return TransDetails
    }

    setUserExit = exit => {
        let val = this.state[exit.type];
        if(val =='')
            val = exit.paramStr
        else 
            val = val + "~" + exit.paramStr
        this.setState({
            ...this.state,
            [exit.type]: val
        })
    }

    PreviousStep = () => {
        this.setState({
            step: this.props.steps().handleBack()
        })
        
    }
    ProcessSteps = () => {
        switch (this.state.step) {
            case 0:
                return this.ProcessManagers()
            case 1:
                return this.ProcessAgentTypes()
            case 2:
                return this.ProcessParameters()
            case 3:
                return this.ProcessTriggers()
            case 4:
                return this.ProcessMetadata()
        }
    }

    ProcessManagers = () => {
        const smgr = this.state["Src_QueMgr"]
        const tmgr = this.state["Trgt_QueMgr"]
        if (!smgr || !tmgr) {
            this.refs.utils.Error('Please select source & target queue managers')
            return
        }
        TransDetails.Src_QueMgr = smgr.qmName
        TransDetails.Src_HostIP = smgr.host_ip;
        TransDetails.Src_HostName = smgr.host_name;
        TransDetails.Src_Channel = smgr.qmChannel;
        TransDetails.Src_Port = smgr.qmPort;
        TransDetails.Src_UserID = smgr.qmUserid;

        TransDetails.Trgt_QueMgr = tmgr.qmName
        this.props.onChange(TransDetails)
        let src_agents = this.state.agents.filter(obj => {
            if (
                obj.queueManager === smgr.qmName && obj.agentType  !== 'BRIDGE'
            )
            return obj;
        });
        let trgt_agents = this.state.agents.filter(obj => {
            if (
                obj.queueManager === smgr.qmName 
            )
            return obj;
        });
        this.setState({
            ...this.state,
            step: this.props.steps().handleNext(),
            src_agents: src_agents,
            trgt_agents: trgt_agents
        })
    }
    ProcessAgentTypes = () => {        
        if (this.props.type > 1 && !this.state.monitor_name
            || this.props.type > 1 && this.state.monitor_name.trim() === '' ) {
            this.refs.utils.Error('Please enter a monitor name')
            return
        }

        const sagt = this.state["Src_Agent"]
        const tagt = this.state["Trgt_Agent"]
        if (!sagt || !tagt) {
            this.refs.utils.Error('Please select source & target agents')
            return
        }
        var trg_condition = this.state.trg_condition
        if (this.state.resource_type === 'directory' && trg_condition === '') 
            trg_condition = 'fileMatch'
        if (this.state.resource_type === 'queue' && trg_condition === '')
            trg_condition = 'queueNotEmpty'
        TransDetails.Monitor_Name = this.state.monitor_name
        TransDetails.Resource_Type = this.state.resource_type
        TransDetails.Src_Agent = sagt.agentName
        TransDetails.Trgt_Agent = tagt.agentName
        TransDetails.Trg_Condition = trg_condition

        if (this.state.resource_type === 'queue')
            this.fetchQueues("local")
        this.props.onChange(TransDetails)
        this.setState({
            ...this.state,
            trg_condition: trg_condition,
            step: this.props.steps().handleNext(),
        })
    }
    ProcessParameters = () => {
        if (this.state.resource_type === 'directory') {
            if (!this.state.resource_info || this.state.resource_info.trim() === '') {
                this.refs.utils.Error('Please enter a valid directory');
                return;
            }
            if (this.state.m_pattern.trim() === '' && this.state.e_pattern.trim() === '') {
                this.refs.utils.Error('Please enter a match pattern or exclude pattern');
                return;
            }
        }
        if (this.state.poll_interval < 1) {
            this.refs.utils.Error('Please enter a valid interval');
            return;
        }
        TransDetails.Resource_Info = this.state.resource_info;
        TransDetails.M_Pattern = this.state.m_pattern;
        TransDetails.E_Pattern = this.state.e_pattern;
        TransDetails.Pattern_Type = this.state.pattern_type;
        TransDetails.Poll_Interval = this.state.poll_interval;
        TransDetails.Poll_Frequency = this.state.poll_frequency;
        this.props.onChange(TransDetails);
        this.setState({
            ...this.state,
            step: this.props.steps().handleNext(),
        });
    }
    ProcessTriggers = () => {
        if (this.state.trg_condition === '') {
            this.refs.utils.Error('Please select a trigger condition');
            return;
        }
        TransDetails.Trg_Condition = this.state.trg_condition;
        TransDetails.Transfer_Mode = this.state.transfer_mode;
        TransDetails.Source_Type = this.state.source_type;
        TransDetails.Src_File = this.state.src_file;
        TransDetails.Src_Disposition = this.state.Src_disposition;
        TransDetails.Destination_Type = this.state.destination_type;
        TransDetails.Trgt_File = this.state.trgt_file;
        TransDetails.Trgt_Exists = this.state.Trgt_exists;
        this.props.onChange(TransDetails);
        this.setState({
            ...this.state,
            step: this.props.steps().handleNext(),
        });
    }
    ProcessUserExitValue = inVal => {
        var outVal = inVal
        if (outVal.startsWith("%")) {
            outVal = outVal.substring(1, outVal.length - 1);
            outVal = outVal.replace("(SoureDirectory)", this.state.resource_info);
            if (outVal.includes("(/SoureDirectory)")) {
                let dir = this.state.resource_info;
                let dirs = dir.split("/");
                dirs.splice(dirs.length - 1);
                dir = dirs.join("/.");
                outVal = outVal.replace("(/SoureDirectory)", dir);
            }
            outVal = outVal.replace("(TargetDirectory)", this.state.Trgt_file);
            outVal = outVal.replace("(SoureQueue)", this.state.resource_info);
            outVal = outVal.replace("(TargetQueue)", this.state.Trgt_file);
        } 
        return outVal
    }
    ProcessMetadata = () => {
        if (this.state.job_name === '') {
            this.refs.utils.Error('Please enter a job name');
            return;
        }
        TransDetails.Job_Name = this.state.job_name;
        TransDetails.Pre_Src = this.state.pre_src;
        TransDetails.Post_Src = this.state.post_src;
        TransDetails.Pre_Dest = this.state.pre_dest;
        TransDetails.Post_Dest = this.state.post_dest;
        this.props.onChange(TransDetails);
        this.setState({
            ...this.state,
            step: this.props.steps().handleNext(),
        });
    }
    handleChange = name => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    handlePropChange =(prop,value) => {
        this.setState({
            ...this.state,
            [prop]: value
        })
    }
    setTransDetails = () => {
        this.props.onChange(TransDetails)
    }
    formatDate = (date) => {
        var dt = new Date(date)
        var month=dt.getMonth()
        var year=dt.getFullYear()
        var day=dt.getDate()
        var hours = dt.getHours()
        var minutes = dt.getMinutes()
        var seconds = dt.getSeconds()
        var dateString = `${year}-${month<10?'0'+month:month}-${day<10?'0'+day:day}-${hours<10?'0'+hours:hours}.${minutes<10?'0'+minutes:minutes}.${seconds<10?'0'+seconds:seconds}`
        return dateString
    }
    handleDateTimeChange = (capturedDateTime, type) => {
        var formatedDT = this.formatDate(capturedDateTime)
        this.setState({
            ...this.state,
            [type]: formatedDT
        })
    }
    componentDidMount() {
        this.fetchManagers()
    }

    render() {
        return (
            <React.Fragment>
                <Utils ref="utils" />
                <Grid container spacing={24} direction="row" alignItems="center" justify="space-evenly" >

                    {/* TOP BUTTONS PANEL */}
                    {this.state.step < 5 && 
                    <Grid item md={12} style={{textAlign: "right"}}>
                        {this.state.step > 0 && 
                        <Button variant="contained"  color="primary"style={styles.button}
                            onClick={this.PreviousStep}>Back</Button>
                        }
                        <Button variant="contained"  color="primary" style={styles.button}
                            onClick={this.ProcessSteps}>Continue</Button>
                    </Grid>
                    }
                    {this.state.step == 5 && 
                    <Grid item md={12} style={{textAlign: "center"}}>
                        <Typography variant="h6">
                            Please review the details provided and click submit
                        </Typography>
                        <Button variant="contained"  color="primary"style={styles.button}
                            onClick={this.PreviousStep}>Back</Button>
                        <Button variant="contained"  color="primary"style={styles.button}
                            onClick={this.Submit}>Submit</Button>
                    </Grid>
                    }
                    {/* TOP BUTTONS PANEL */}



                    {/* QUEUE MANAGERS PANEL */}
                    { this.state.step === 0 &&
                    <Grid item md={12} >
                        <DropDown items={this.props.managers}
                            value={this.state.Src_QueMgr}
                            caption='Source Queue Manager' 
                            TextField="qmName" 
                            onChange={(val) => this.handlePropChange("Src_QueMgr",val)} /> 

                        <DropDown items={this.props.managers}
                            value={this.state.Trgt_QueMgr}
                            caption='Target Queue Manager' 
                            TextField="qmName" 
                            onChange={(val) => this.handlePropChange("Trgt_QueMgr",val)} /> 
                    </Grid>
                    }
                    {/* QUEUE MANAGERS PANEL */}



                    {/* NAME TYPE & AGENTS PANEL */}
                    { this.state.step === 1 &&
                    <Grid item md={12} >
                        { this.props.type > 1 &&
                        <TextField
                            fullWidth
                            label="Monitor name"
                            placeholder="Monitor name"
                            variant="standard"
                            margin="normal"
                            InputLabelProps={{ shrink: true }}
                            value={this.state.monitor_name}
                            onChange={this.handleChange("monitor_name")} /> 
                        }

                        <FormControl component="fieldset"
                            margin="none" fullWidth >
                            <FormLabel component="legend">Type</FormLabel>
                            <RadioGroup row 
                                aria-label="Type"
                                name="Type"
                                style={styles.radioGroup}
                                value={this.state.resource_type}
                                onChange={this.handleChange('resource_type')}
                            >
                                <FormControlLabel value="directory" control={<Radio />} label="Directory" />
                                <FormControlLabel value="queue" control={<Radio />} label="Queue" />
                            </RadioGroup>
                        </FormControl>

                        <DropDown items={this.state.src_agents}
                            value={this.state.Src_Agent}
                            caption='Source Agent' 
                            TextField="agentName" 
                            onChange={(val) => this.handlePropChange("Src_Agent",val)} /> 

                        <DropDown items={this.state.trgt_agents}
                            value={this.state.Trgt_Agent}
                            caption='Target Agent' 
                            TextField="agentName" 
                            onChange={(val) => this.handlePropChange("Trgt_Agent",val)} /> 
                    </Grid>
                    }
                    {/* NAME TYPE & AGENTS PANEL */}



                    {/* WATCH / SCHEDULE PANEL */}
                    { this.state.step === 2 && this.props.type === 0 &&
                    <Grid item md={12} >
                        <Typography variant="caption">Watch / schedule not required for One-time-transfer</Typography>
                    </Grid>
                    }
                    { this.state.step === 2 && this.props.type === 1 &&
                    <Grid item md={12} >
                        <DropDown items={enums.TimeBases}
                            value={this.state.resource_info}
                            caption='Time base' 
                            TextField="Base" 
                            ValueField="Base" 
                            onChange={(val) => this.handlePropChange("resource_info",val)} /> 

                        <Grid container spacing={24} >
                            <Grid item md={5}>
                                <TextField
                                    fullWidth
                                    label="Schedule Interval"
                                    variant="standard"
                                    margin="normal"
                                    type="number"
                                    value={this.state.poll_interval}
                                    onChange={this.handleChange("poll_interval")} /> 
                            </Grid>
                            <Grid item md={5}>
                            <DropDown items={enums.PollFrequencys}
                                value={this.state.poll_frequency}
                                caption='Schedule Frequency' 
                                TextField="Poll" 
                                ValueField="Poll" 
                                onChange={(val) => this.handlePropChange("poll_frequency",val)} /> 
                            </Grid>
                        </Grid>
                        <Grid container spacing={24}>
                            <Grid item md={5}>
                                <DateTimePicker
                                    label="Start Date and Time"
                                    capturedValue={event => this.handleDateTimeChange(event, "startDate")}
                                    defaultDateTime={this.state.startDateTime} />              
                            </Grid>
                            <Grid item md={5}>
                                <DateTimePicker
                                    label="End Date and Time"
                                    capturedValue={event => this.handleDateTimeChange(event, "endDate")}
                                    defaultDateTime={this.state.endDateTime} />
                            </Grid>
                        </Grid>
                    </Grid>
                    }
                    { this.state.step === 2 && this.props.type > 1 &&
                    <Grid item md={12} >
                        { this.state.resource_type === 'directory' &&
                        <Grid container spacing={8}>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Directory"
                                    placeholder="Directory"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.resource_info}
                                    onChange={this.handleChange("resource_info")} /> 
                            </Grid>
                            <Grid item xs={4}>
                                <TextField
                                    fullWidth
                                    label="Match Pattern"
                                    placeholder="Match Pattern"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.m_pattern}
                                    onChange={this.handleChange("m_pattern")} /> 
                            </Grid>
                            <Grid item xs={4}>
                                <TextField
                                    fullWidth
                                    label="Exclude Pattern"
                                    placeholder="Exclude Pattern"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.e_pattern}
                                    onChange={this.handleChange("e_pattern")} /> 
                            </Grid>
                            <Grid item xs={4}>
                                <DropDown items={enums.PatternTypes}
                                    value={this.state.pattern_type}
                                    caption='Pattern type' 
                                    TextField="Type" 
                                    ValueField="Type" 
                                    onChange={(val) => this.handlePropChange("pattern_type",val)} /> 
                            </Grid>
                        </Grid>
                        }
                        { this.state.resource_type === 'queue' &&
                            <DropDown items={this.state.queues}
                                value={this.state.resource_info}
                                caption='Queue' 
                                TextField="queueName" 
                                ValueField="queueName" 
                                onChange={(val) => this.handlePropChange("resource_info",val)} /> 
                        }

                        <Grid container spacing={24} >
                            <Grid item md={4}>
                                <TextField
                                    fullWidth
                                    label="Interval"
                                    placeholder="Interval"
                                    variant="standard"
                                    margin="normal"
                                    type="number"
                                    value={this.state.poll_interval}
                                    onChange={this.handleChange("poll_interval")} /> 
                            </Grid>
                            <Grid item md={4}>
                            <DropDown items={enums.PollFrequencys}
                                value={this.state.poll_frequency}
                                caption='Frequency' 
                                TextField="Poll" 
                                ValueField="Poll" 
                                onChange={(val) => this.handlePropChange("poll_frequency",val)} /> 
                            </Grid>
                        </Grid>
                    </Grid>
                    }
                    {/* WATCH / SCHEDULE PANEL */}



                    {/* TRIGGER CONDITIONS PANEL */}
                    { this.state.step === 3 &&
                    <Grid item md={12} >

                        { this.state.resource_type === 'directory' &&
                        <FormControl component="fieldset"
                            margin="none" fullWidth >
                            <FormLabel component="legend">Trigger Condition</FormLabel>
                            <RadioGroup row 
                                aria-label="Trigger Condition"
                                name="Trigger+Condition"
                                style={styles.radioGroup}
                                value={this.state.trg_condition}
                                onChange={this.handleChange('trg_condition')}
                            >
                                <FormControlLabel value="fileMatch" control={<Radio />} label="match pattern" />
                                <FormControlLabel value="fileSizeSame" control={<Radio />} label="match pattern and remains unchanged for" />
                                <FormControlLabel value="fileSize" control={<Radio />} label="match pattern and file size exceeds" />
                                <FormControlLabel value="fileNoMatch" control={<Radio />} label="do not match pattern" />
                            </RadioGroup>
                        </FormControl>
                        }

                        { this.state.resource_type === 'queue' &&
                        <FormControl component="fieldset"
                            margin="none" fullWidth >
                            <FormLabel component="legend">Trigger Conditions</FormLabel>
                            <RadioGroup row 
                                aria-label="Trigger Condition"
                                name="Trigger+Condition"
                                style={styles.radioGroup}
                                value={this.state.trg_condition}
                                onChange={this.handleChange('trg_condition')}
                            >
                                <FormControlLabel value="queueNotEmpty" control={<Radio />} label="contains one or more messages" />
                                <FormControlLabel value="completeGroups" control={<Radio />} label="contains one or more complete groups" />
                            </RadioGroup>
                        </FormControl>
                        }

                        <FormControl component="fieldset"
                            margin="none" fullWidth >
                            <FormLabel component="legend">Transfer Mode</FormLabel>
                            <RadioGroup row 
                                aria-label="Transfer Mode"
                                name="transfer-mode"
                                style={styles.radioGroup}
                                value={this.state.transfer_mode}
                                onChange={this.handleChange('transfer_mode')}
                            >
                                <FormControlLabel value="binary" control={<Radio />} label="Binary transfer" />
                                <FormControlLabel value="text" control={<Radio />} label="Text transfer" />
                            </RadioGroup>
                        </FormControl>

                        <Grid container spacing={16}>
                            <Grid item xs={3}>
                                <DropDown items={enums.TransTypes}
                                    value={this.state.source_type}
                                    caption='Source type' 
                                    TextField="Type" 
                                    ValueField="Type" 
                                    onChange={(val) => this.handlePropChange("source_type",val)} /> 
                            </Grid>
                            <Grid item xs={9}>
                                <TextField
                                    fullWidth
                                    label="Source"
                                    placeholder="Source"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.src_file}
                                    onChange={this.handleChange("src_file")} /> 
                            </Grid>
                        </Grid>

                        { this.state.source_type !== 'Queue' &&
                        <FormControl component="fieldset"
                            margin="none" fullWidth >
                            <FormLabel component="legend">Remove file after transfer complete</FormLabel>
                            <RadioGroup row 
                                aria-label="src_disposition"
                                name="src-disposition"
                                style={styles.radioGroup}
                                value={this.state.Src_disposition}
                                onChange={this.handleChange('Src_disposition')}
                            >
                                <FormControlLabel value="leave" control={<Radio />} label="Leave source file" />
                                <FormControlLabel value="delete" control={<Radio />} label="Delete source file" />
                            </RadioGroup>
                        </FormControl>
                        }

                        <Grid container spacing={16}>
                            <Grid item xs={3}>
                                <DropDown items={enums.TransTypes}
                                    value={this.state.destination_type}
                                    caption='Destination type' 
                                    TextField="Type" 
                                    ValueField="Type" 
                                    onChange={(val) => this.handlePropChange("destination_type",val)} /> 
                            </Grid>
                            <Grid item xs={9}>
                                <TextField
                                    fullWidth
                                    label="Destination"
                                    placeholder="Destination"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.trgt_file}
                                    onChange={this.handleChange("trgt_file")} /> 
                            </Grid>
                        </Grid>

                        { this.state.destination_type !== 'Queue' &&
                            <FormControl component="fieldset"
                                margin="none" fullWidth >
                                <FormLabel component="legend">Overwrite files if present</FormLabel>
                                <RadioGroup row 
                                    aria-label="Trgt_exists"
                                    name="Trgt_exists"
                                    style={styles.radioGroup}
                                    value={this.state.Trgt_exists}
                                    onChange={this.handleChange('Trgt_exists')}
                                >
                                    <FormControlLabel value="error" control={<Radio />} label="Through error" />
                                    <FormControlLabel value="overwrite" control={<Radio />} label="Delete target file" />
                                </RadioGroup>
                            </FormControl>
                        }
                    </Grid>
                    }
                    {/* TRIGGER CONDITIONS PANEL */}



                    {/* ADDITIONAL OPTIONS PANEL */}
                    { this.state.step === 4 &&
                    <React.Fragment>
                    <Grid item md={12} >
                        <Grid container spacing={16}>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Job name"
                                    placeholder="Job name"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.job_name}
                                    onChange={this.handleChange("job_name")} /> 
                            </Grid>
                            <Grid item xs={2}>
                                <TextField
                                    fullWidth
                                    label="Priority"
                                    placeholder="Priority"
                                    variant="standard"
                                    margin="normal"
                                    type="number"
                                    value={this.state.transfer_priority}
                                    onChange={this.handleChange("transfer_priority")} /> 
                            </Grid>
                            <Grid item xs={2}>
                                <DropDown items={enums.CheckSums}
                                    value={this.state.checksum}
                                    caption='Checksum' 
                                    TextField="Check" 
                                    ValueField="Check" 
                                    onChange={(val) => this.handlePropChange("checksum",val)} /> 
                            </Grid>
                            <Grid item xs={8}>
                                <TextField
                                    fullWidth
                                    label="Reply queue"
                                    placeholder="Reply queue"
                                    variant="standard"
                                    margin="normal"
                                    InputLabelProps={{ shrink: true }}
                                    value={this.state.reply_Queue}
                                    onChange={this.handleChange("reply_Queue")} /> 
                            </Grid>
                        </Grid>
                    </Grid>
                    <br/>
                    <TransferUserExit ref="userExit"
                        onSubmit={this.setUserExit} 
                        exits={UserExits} 
                        processUserExitValue={this.ProcessUserExitValue} />
                    <Grid item md={12} >
                        <Grid container spacing={16} alignContent="center" alignItems="center">
                            <Grid item xs={11}>
                                <TextField
                                    fullWidth
                                    label="Pre-source"
                                    variant="filled"
                                    margin="normal"
                                    multiline rows={2}
                                    InputLabelProps={{ shrink: true }}
                                    InputProps={{readOnly: true,}}
                                    value={this.state.pre_src}
                                    onChange={this.handleChange("pre_src")} /> 
                            </Grid>
                            <Grid item xs={1}>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.refs.userExit.Show('pre_src')}
                                    >
                                    <AddIcon />
                                </IconButton>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.handlePropChange('pre_src','')}
                                    >
                                    <ClearIcon />
                                </IconButton>
                            </Grid>
                            <Grid item xs={11}>
                                <TextField
                                    fullWidth
                                    label="Pre-destination"
                                    variant="filled"
                                    margin="normal"
                                    multiline rows={2}
                                    InputLabelProps={{ shrink: true }}
                                    InputProps={{readOnly: true,}}
                                    value={this.state.pre_dest}
                                    onChange={this.handleChange("pre_dest")} /> 
                            </Grid>
                            <Grid item xs={1}>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.refs.userExit.Show('pre_dest')}
                                    >
                                    <AddIcon />
                                </IconButton>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.handlePropChange('pre_dest','')}
                                    >
                                    <ClearIcon />
                                </IconButton>
                            </Grid>
                            <Grid item xs={11}>
                                <TextField
                                    fullWidth
                                    label="Post-source"
                                    variant="filled"
                                    margin="normal"
                                    multiline rows={2}
                                    InputLabelProps={{ shrink: true }}
                                    InputProps={{readOnly: true,}}
                                    value={this.state.post_src}
                                    onChange={this.handleChange("post_src")} /> 
                            </Grid>
                            <Grid item xs={1}>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.refs.userExit.Show('post_src')}
                                    >
                                    <AddIcon />
                                </IconButton>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.handlePropChange('post_src','')}
                                    >
                                    <ClearIcon />
                                </IconButton>
                            </Grid>
                            <Grid item xs={11}>
                                <TextField
                                    fullWidth
                                    label="Post-destination"
                                    variant="filled"
                                    margin="normal"
                                    multiline rows={2}
                                    InputLabelProps={{ shrink: true }}
                                    InputProps={{readOnly: true,}}
                                    value={this.state.post_dest}
                                    onChange={this.handleChange("post_dest")} /> 
                            </Grid>
                            <Grid item xs={1}>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.refs.userExit.Show('post_dest')}
                                    >
                                    <AddIcon />
                                </IconButton>
                                <IconButton
                                    color="primary"
                                    onClick={() => this.handlePropChange('post_dest','')}
                                    >
                                    <ClearIcon />
                                </IconButton>
                            </Grid>
                        </Grid>
                    </Grid>         
                    </React.Fragment>           
                    }
                    {/* ADDITIONAL OPTIONS PANEL */}


                </Grid>
            </React.Fragment>
        )
    }
}