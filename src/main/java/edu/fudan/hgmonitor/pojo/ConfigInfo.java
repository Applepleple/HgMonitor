package edu.fudan.hgmonitor.pojo;

import edu.fudan.hgmonitor.Constants;

public class ConfigInfo {

	private Integer interval;
	private Integer timeout;
	private Integer port;
	private String serverIp;
	private Integer httpPort;
	
	public ConfigInfo() {
		interval = Constants.DEFAULT_INTERVAL;
		timeout = Constants.DEFAULT_TIMEOUT;
		httpPort = Constants.DEFAULT_HTTPPORT;
	}
	
	public Integer getInterval() {
		return interval;
	}
	
	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Integer getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(Integer httpPort) {
		this.httpPort = httpPort;
	}
	
}
