package com.mqrest.MQService;

public class LocalQueues {
	
	public String queueName;
	public int queueDepth;
	public int queueMaxDepth;
	public int outputCount;
	public int inputCount;
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public int getQueueDepth() {
		return queueDepth;
	}
	public void setQueueDepth(int queueDepth) {
		this.queueDepth = queueDepth;
	}
	public int getQueueMaxDepth() {
		return queueMaxDepth;
	}
	public void setQueueMaxDepth(int queueMaxDepth) {
		this.queueMaxDepth = queueMaxDepth;
	}
	public int getOutputCount() {
		return outputCount;
	}
	public void setOutputCount(int outputCount) {
		this.outputCount = outputCount;
	}
	public int getInputCount() {
		return inputCount;
	}
	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}
	

}
