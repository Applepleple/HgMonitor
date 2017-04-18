package edu.fudan.hgmonitor.pojo;

import java.io.Serializable;

public class GPUInfo implements Serializable {

	private static final long serialVersionUID = 8560362623823237019L;
	
	private int id;
	private String hostIpAddr;
	private String name;
	
	private long freeMemory;
	private long totalMemory;
	
	private int power;
	private int powerLimit;
	
	private int temperature;
	private int temperatureThresholdSlowDown;
	private int temperatureThresholdShutdown;
	
	private int pcieLinkWidth;
	private int pcieLinkGeneration;
	private int pcieThroughputRead;
	private int pcieThroughputWrite;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getHostIpAddr() {
		return hostIpAddr;
	}

	public void setHostIpAddr(String hostIpAddr) {
		this.hostIpAddr = hostIpAddr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPowerLimit() {
		return powerLimit;
	}

	public void setPowerLimit(int powerLimit) {
		this.powerLimit = powerLimit;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getTemperatureThresholdSlowDown() {
		return temperatureThresholdSlowDown;
	}

	public void setTemperatureThresholdSlowDown(int temperatureThresholdSlowDown) {
		this.temperatureThresholdSlowDown = temperatureThresholdSlowDown;
	}

	public int getTemperatureThresholdShutdown() {
		return temperatureThresholdShutdown;
	}

	public void setTemperatureThresholdShutdown(int temperatureThresholdShutdown) {
		this.temperatureThresholdShutdown = temperatureThresholdShutdown;
	}

	public int getPcieLinkWidth() {
		return pcieLinkWidth;
	}

	public void setPcieLinkWidth(int pcieLinkWidth) {
		this.pcieLinkWidth = pcieLinkWidth;
	}

	public int getPcieLinkGeneration() {
		return pcieLinkGeneration;
	}

	public void setPcieLinkGeneration(int pcieLinkGeneration) {
		this.pcieLinkGeneration = pcieLinkGeneration;
	}

	public int getPcieThroughputRead() {
		return pcieThroughputRead;
	}

	public void setPcieThroughputRead(int pcieThroughputRead) {
		this.pcieThroughputRead = pcieThroughputRead;
	}

	public int getPcieThroughputWrite() {
		return pcieThroughputWrite;
	}

	public void setPcieThroughputWrite(int pcieThroughputWrite) {
		this.pcieThroughputWrite = pcieThroughputWrite;
	}
	
}
