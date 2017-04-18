package edu.fudan.hgmonitor.jni;

import edu.fudan.hgmonitor.Constants.PcieUtilCounterType;
import edu.fudan.hgmonitor.Constants.TemperatureSensorType;
import edu.fudan.hgmonitor.Constants.TemperatureThresholdType;

public class HWMonitorHelper {

	public static native int init();
	public static native void exit();
	
	public static native int getDeviceNum();
	
	public static native String getGPUName(int gpuIndex);	
	public static native long getGPUFreeMemory(int gpuIndex);
	public static native long getGPUTotalMemory(int gpuIndex);

	public static native int getGPUPowerUsage(int gpuIndex);
	public static native int getGPUPowerManagementLimit(int gpuIndex);

	public static int getGPUTemperature(int gpuIndex, TemperatureSensorType sensorType) {
		int sensorTypeInt = 0;
		switch (sensorType) {
		case NVML_TEMPERATURE_GPU :
			sensorTypeInt = 0;
			break;
		}		
		return getGPUTemperature(gpuIndex, sensorTypeInt);
	}
	
	private static native int getGPUTemperature(int gpuIndex, int sensorType);
	
	public static int getGPUTemperatureThreshold(int gpuIndex, TemperatureThresholdType thresholdType) {
		int thresholdTypeInt = 0;
		switch (thresholdType) {
		case NVML_TEMPERATURE_THRESHOLD_SHUTDOWN :
			thresholdTypeInt = 0;
			break;
		case NVML_TEMPERATURE_THRESHOLD_SLOWDOWN :
			thresholdTypeInt = 1;
			break;
		}		
		return getGPUTemperatureThreshold(gpuIndex, thresholdTypeInt);
	}
	
	private static native int getGPUTemperatureThreshold(int gpuIndex, int thresholdType);
	
	public static native int getMaxPCILinkWidth(int gpuIndex);
	public static native int getMaxPCILinkGeneration(int gpuIndex);
	
	public static int getPCIThroughput(int gpuIndex, PcieUtilCounterType pcieUtilCounterType) {
		int pcieUtilCounterTypeInt = 0;
		switch (pcieUtilCounterType) {
		case NVML_PCIE_UTIL_TX_BYTES :
			pcieUtilCounterTypeInt = 0;
			break;
		case NVML_PCIE_UTIL_RX_BYTES :
			pcieUtilCounterTypeInt = 1;
			break;
		}		
		return getPCIThroughput(gpuIndex, pcieUtilCounterTypeInt);
	}
	
	private static native int getPCIThroughput(int gpuIndex, int counter);
}
