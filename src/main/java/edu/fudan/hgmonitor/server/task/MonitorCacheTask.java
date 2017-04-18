package edu.fudan.hgmonitor.server.task;

import java.util.Set;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import edu.fudan.hgmonitor.cache.GPUInfoCache;

public class MonitorCacheTask extends TimerTask {

	private Logger logger = Logger.getLogger(MonitorCacheTask.class);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Set<String> gpuInfoKeySet = GPUInfoCache.getInstance().getKeySet();
		
		for (String key : gpuInfoKeySet) {
			if (!GPUInfoCache.getInstance().isEntryValid(key)) {
				GPUInfoCache.getInstance().removeEntry(key);
			}
		}
		logger.info("MonitorCacheTask is running...");
	}

}
