package edu.fudan.hgmonitor.cache;

import edu.fudan.hgmonitor.pojo.ConfigInfo;

public class ConfigCache {

	private static ConfigCache mConfigCache;
	private ConfigInfo configInfo;
	
	private ConfigCache() {
		configInfo = new ConfigInfo();
	}
	
	public synchronized static ConfigCache getInstance() {
		if (mConfigCache == null) {
			mConfigCache = new ConfigCache();
		}
		return mConfigCache;
	}
	
	public void setConfigInfo(ConfigInfo configInfo) {
		this.configInfo = configInfo;
	}
	
	public ConfigInfo getConfigInfo() {
		return configInfo;
	}
}
