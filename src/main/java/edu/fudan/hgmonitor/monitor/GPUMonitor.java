package edu.fudan.hgmonitor.monitor;

import java.util.ArrayList;
import java.util.List;

import edu.fudan.hgmonitor.Constants.PcieUtilCounterType;
import edu.fudan.hgmonitor.Constants.TemperatureSensorType;
import edu.fudan.hgmonitor.Constants.TemperatureThresholdType;
import edu.fudan.hgmonitor.NetUtil;
import edu.fudan.hgmonitor.jni.HWMonitorHelper;
import edu.fudan.hgmonitor.pojo.GPUInfo;

public class GPUMonitor {

	private static GPUMonitor mGpuMonitor;
	private boolean isInit = false;
//	private Logger logger = Logger.getLogger(GPUMonitor.class);
	
	private GPUMonitor() {
		if (HWMonitorHelper.init() != 0) {
			isInit = true;
		}
	}
	
	public static synchronized GPUMonitor getInstance() {
		if (mGpuMonitor == null) {
			mGpuMonitor = new GPUMonitor();
		}
		return mGpuMonitor;
	}
	
	public List<GPUInfo> getLocalGPUInfoList() {
		if (!isInit) {
			return null;
		}
		
		int deviceNum = HWMonitorHelper.getDeviceNum();
		List<GPUInfo> gpuInfos = new ArrayList<GPUInfo>();
		
//		logger.info("Get device num : " + deviceNum);
		
		for (int i = 0; i < deviceNum; i++) {
			GPUInfo gpuInfo = new GPUInfo();
			gpuInfo.setId(i);
			gpuInfo.setHostIpAddr(NetUtil.getLocalIpAddress());
			gpuInfo.setName(HWMonitorHelper.getGPUName(i));
			gpuInfo.setFreeMemory(HWMonitorHelper.getGPUFreeMemory(i));
			gpuInfo.setTotalMemory(HWMonitorHelper.getGPUTotalMemory(i));
			gpuInfo.setPower(HWMonitorHelper.getGPUPowerUsage(i));
			gpuInfo.setPowerLimit(HWMonitorHelper.getGPUPowerManagementLimit(i));
			gpuInfo.setTemperature(
					HWMonitorHelper.getGPUTemperature(i,
							TemperatureSensorType.NVML_TEMPERATURE_GPU));
			gpuInfo.setTemperatureThresholdSlowDown(
					HWMonitorHelper.getGPUTemperatureThreshold(i,
							TemperatureThresholdType.NVML_TEMPERATURE_THRESHOLD_SLOWDOWN));
			gpuInfo.setTemperatureThresholdShutdown(
					HWMonitorHelper.getGPUTemperatureThreshold(i,
							TemperatureThresholdType.NVML_TEMPERATURE_THRESHOLD_SHUTDOWN));
			gpuInfo.setPcieLinkWidth(HWMonitorHelper.getMaxPCILinkWidth(i));
			gpuInfo.setPcieLinkGeneration(HWMonitorHelper.getMaxPCILinkGeneration(i));
			gpuInfo.setPcieThroughputRead(
					HWMonitorHelper.getPCIThroughput(i,
							PcieUtilCounterType.NVML_PCIE_UTIL_RX_BYTES));
			gpuInfo.setPcieThroughputWrite(
					HWMonitorHelper.getPCIThroughput(i,
							PcieUtilCounterType.NVML_PCIE_UTIL_TX_BYTES));
			gpuInfos.add(gpuInfo);
		}
		return gpuInfos;
	}
}
