package edu.fudan.hgmonitor;

public class Constants {

	public static final String CONFIG_FILE = "conf/conf.ini";
	public static final String CONFIG_TIMEOUT = "prop.timeout";
	public static final String CONFIG_INTERVAL = "prop.interval";
	public static final String CONFIG_SERVER_IP = "server.ip";
	public static final String CONFIG_SERVER_PORT = "server.port";
	public static final String CONFIG_SERVER_HTTPPORT = "server.httpPort";
	
	public static final Integer DEFAULT_INTERVAL = 1000;
	public static final Integer DEFAULT_TIMEOUT = 3000;
	public static final Integer DEFAULT_HTTPPORT = 8080;
	
	public enum TemperatureSensorType {
		NVML_TEMPERATURE_GPU,
	}
	
	public enum TemperatureThresholdType {
		NVML_TEMPERATURE_THRESHOLD_SHUTDOWN,
		NVML_TEMPERATURE_THRESHOLD_SLOWDOWN
	}
	
	public enum PcieUtilCounterType {
		NVML_PCIE_UTIL_TX_BYTES,
		NVML_PCIE_UTIL_RX_BYTES
	}
}
