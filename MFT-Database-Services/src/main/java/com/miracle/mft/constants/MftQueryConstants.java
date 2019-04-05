package com.miracle.mft.constants;

public class MftQueryConstants {

	public static String getTransferValues = "select M.key as KEYNAME,M.value as KEYVALUES,T.source_filename as SFileName,T.Destination_filename as DFileName,T.destination_message_queue_name as DMQName from FTELOG.metadata M inner join FTELOG.Transfer TS on TS.transfer_id='%s' inner join FTELOG.Transfer_item T on T.transfer_id=TS.transfer_id where M.transfer_event_id=TS.complete_id with ur";
	public static String Get_All_JOBS = "SELECT DISTINCT JOB_NAME FROM FTELOG.TRANSFER order by job_name with ur;";
	public static String DeleteUserExitValues = "Update FTELOG.MQMFT_User_Exit_Details set Deleted='Y' where Exit_id=%d";
	public static String InsertUserExitvalues = "Insert into FTELOG.MQMFT_User_Exit_Details(ID,User_Exit_value,Exit_Params)values(%d,'%s','%s')";
	public static String UpdateUserExitValues = "Update FTELOG.MQMFT_User_Exit_Details set User_Exit_Value='%s',Exit_Params='%s' where Exit_id=%d";
	public static String GetUserExitValues = "select Exit_id,ID,user_exit_value,Exit_Params from FTELOG.mqmft_user_exit_details where ID=%d and Deleted='Y' order by ID with ur";
	public static String UpdateUserExitTypes = "Update FTELOG.MQMFT_User_Exits set User_Exit_Type='%s' where ID=%d";

	public static String GetUserExitTypes = "Select ID,User_Exit_Type from FTELOG.mqmft_user_exits with ur";
	public static String InsertUserExitTypes = "Insert into FTELOG.MQMFT_User_Exits(User_Exit_Type)values('%s')";
	public static String GetUserExits = "select u.user_exit_type as userexittype,ud.user_Exit_value as userexitdetail, ud.Exit_Params  from FTELOG.MQMFT_USER_EXITS u , FTELOG.MQMFT_USER_EXIT_DETAILS ud where ud.id=u.id and ud.deleted='N' group by u.user_exit_type,ud.user_exit_value,ud.Exit_Params";
	public static String GetQueueManagers = "SELECT qm.*,host.host_name, host.host_ip FROM FTELOG.MQMFT_QM_DETAILS qm inner join FTELOG.MQMFT_HOST host on qm.HOST_ID = host.HOST_ID with ur";
	public static String GetHost = "select * from FTELOG.MQMFT_HOST where deleted='N' with ur";
	public static String Get_JobXML = "select MA.original_xml_request as JBXML from(select monitor,original_xml_request from FTELOG.monitor_action where original_xml_request is not null and original_xml_request like '%s' order by ID desc  FETCH FIRST 1 ROWS only)MA inner join FTELOG.monitor M on M.ID=MA.monitor with ur";
	public static String Get_MonitorXML = "select ma.original_xml_request as MO_XML from (select max(id) as id from FTELOG.monitor where id='%s')ss inner join FTELOG.monitor_action ma on ma.monitor=ss.id and ma.action='create' and ma.original_xml_request is not null with ur";

	public static String Insert_QM = "insert into FTELOG.MQMFT_QM_DETAILS  (QM_NAME,QM_CHANNEL,QM_PORT,QM_USERID,IS_AGENT,IS_CMND,IS_COORD,IS_ACTIVE,HOST_ID) values  ('%s','%s',%d,'%s','%s','%s','%s','%s',%d)";
	public static String Update_QM = "update FTELOG.MQMFT_QM_DETAILS  set QM_NAME='%s',QM_CHANNEL='%s',QM_PORT=%d,QM_USERID='%s',IS_AGENT='%s',IS_CMND='%s',IS_COORD='%s',IS_ACTIVE='%s',HOST_ID=%d where QMID=%d";
	public static String Delete_QM = "delete from  FTELOG.MQMFT_QM_DETAILS  where QMID=%d";

	public static String Insert_Host = "Insert into FTELOG.MQMFT_HOST(HOST_NAME,HOST_IP,COORD_QMGR,COORD_PORT,AGENT_QMGR,AGENT_PORT,DEFAULT_CHANNEL,USER_ID,ACTIVE)VALUES('%s','%s','%s',%d,'%s',%d,'%s','%s','%s')";
	public static String Update_Host = "Update FTELOG.MQMFT_HOST set HOST_NAME='%s',HOST_IP='%s',COORD_QMGR='%s',COORD_PORT=%d,AGENT_QMGR='%s',AGENT_PORT=%d,DEFAULT_CHANNEL='%s',USER_ID='%s',ACTIVE='%s' where HOST_ID=%d";
	public static String Delete_Host = "update FTELOG.MQMFT_HOST set deleted='Y' where Host_ID=%d";

	public static String InsertDeletedMonitor = "INSERT INTO FTELOG.MQMFT_MONITORSTATUS (HOST_ID,Monitor_Name)values(%d,'%s')";
	public static String DeletedMonitorsList = "select distinct Monitor_Name as MonitorNames from FTELOG.MQMFT_MONITORSTATUS with ur";
	public static String Transfer_Log = "SELECT TRANSFER.JOB_NAME AS JOBNAME,TRANSFER.TRANSFER_ID AS TRANSFERID ,TRANSFER.RESULTCODE AS OVERALLRESULT,TRANSFER_START.SOURCE_AGENT AS SOURCESYSTEM,TRANSFER_START.DESTINATION_AGENT AS TARGETSYSTEM,TRANSFER.RESULTTEXT AS OVERALLRESULTTEXT,TRANSFER_START.ACTION_TIME AS STARTTIME,TRANSFER_END.ACTION_TIME AS ENDTIME,COALESCE(TRANSFER_ITEM.SOURCE_FILENAME,'') AS SOURCEFILE,COALESCE(TRANSFER_ITEM.SOURCE_FILE_SIZE, 0) AS SOURCEFILESIZE,COALESCE(TRANSFER_ITEM.DESTINATION_FILE_SIZE, 0) AS DESTINATIONFILESIZE,COALESCE(TRANSFER_START.TRANSFERSET_SIZE, 0) AS NUMBEROFFILES,COALESCE(transfer_item.DESTINATION_FILENAME, '') AS DESTINATIONFILE,TRANSFER_ITEM.RESULTCODE AS FILERESULT,COALESCE(TRANSFER_ITEM.RESULT_TEXT, '') AS FILERESULTMESSAGE,TRANSFER.STATUS AS STATUS FROM FTELOG.TRANSFER TRANSFER inner join FTELOG.TRANSFER_EVENT TRANSFER_START on TRANSFER_START.ID = TRANSFER.START_ID and TRANSFER_START.ACTION_TIME >= '%s' inner join FTELOG.TRANSFER_EVENT TRANSFER_END on TRANSFER_END.ID = TRANSFER.COMPLETE_ID AND TRANSFER_END.ACTION_TIME <= '%s' inner join FTELOG.TRANSFER_ITEM TRANSFER_ITEM on TRANSFER_ITEM.TRANSFER_ID = TRANSFER.TRANSFER_ID where TRANSFER.JOB_NAME LIKE '%s' ORDER BY TRANSFER_START.ACTION_TIME DESC WITH UR";

}
