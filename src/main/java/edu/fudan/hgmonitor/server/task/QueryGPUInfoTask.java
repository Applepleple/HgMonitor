package edu.fudan.hgmonitor.server.task;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import edu.fudan.hgmonitor.cache.GPUInfoCache;
import edu.fudan.hgmonitor.monitor.GPUMonitor;
import edu.fudan.hgmonitor.pojo.GPUInfo;

public class QueryGPUInfoTask extends TimerTask {
	
	private Logger logger = Logger.getLogger(QueryGPUInfoTask.class);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<GPUInfo> gpuInfos = GPUMonitor.getInstance().getLocalGPUInfoList();
		if (gpuInfos != null && gpuInfos.size() > 0) {
			GPUInfoCache.getInstance().insertOrUpdateCache(gpuInfos.get(0).getHostIpAddr(),
					gpuInfos);
		}
		logger.info("QueryGPUInfoTask is running...");
	}

}
