import { MftApiBaseUrl } from "../AppConfig";

const Constants = {
  Endpoints: {
    Hosts: `${MftApiBaseUrl}/hosts`,
    Agents: `${MftApiBaseUrl}/topic/SYSTEM.FTE/Agents`,
    Schedules: `${MftApiBaseUrl}/topic/SYSTEM.FTE/Schedules`,
    Monitors: `${MftApiBaseUrl}/monitors`,
    QueueManagers: `${MftApiBaseUrl}/queueManagers`,
    Queues: `${MftApiBaseUrl}/queues`,
    transferLogs: `${MftApiBaseUrl}/transfers`,
  }
};
module.exports = Constants;