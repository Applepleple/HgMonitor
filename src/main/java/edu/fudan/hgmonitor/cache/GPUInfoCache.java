package edu.fudan.hgmonitor.cache;

import java.util.List;
import edu.fudan.hgmonitor.pojo.GPUInfo;

public class GPUInfoCache extends BaseCache<String, List<GPUInfo>> {
	
	private static GPUInfoCache mGPUInfoCache;
	
	private GPUInfoCache() {
	}
	
	public synchronized static GPUInfoCache getInstance() {
		if (mGPUInfoCache == null) {
			mGPUInfoCache = new GPUInfoCache();
		}
		return mGPUInfoCache;
	}
}
