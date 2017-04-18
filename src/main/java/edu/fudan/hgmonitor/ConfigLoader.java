package edu.fudan.hgmonitor;

import org.apache.commons.configuration.HierarchicalINIConfiguration;

import edu.fudan.hgmonitor.pojo.ConfigInfo;

public class ConfigLoader {

	private static ConfigLoader mConfigLoader;
	
	private ConfigLoader() {
		
	}
	
	public synchronized static ConfigLoader getInstance() {
		if (mConfigLoader == null) {
			mConfigLoader = new ConfigLoader();
		}
		return mConfigLoader;
	}
	
	public ConfigInfo loadConfigInfo(String configFile) {
		ConfigInfo configInfo = new ConfigInfo();
		
		try {	
			HierarchicalINIConfiguration ini = new HierarchicalINIConfiguration(configFile);
			
			configInfo.setServerIp(ini.getString(Constants.CONFIG_SERVER_IP));
			configInfo.setPort(ini.getInt(Constants.CONFIG_SERVER_PORT));
			configInfo.setHttpPort(ini.getInt(Constants.CONFIG_SERVER_HTTPPORT));
			configInfo.setTimeout(ini.getInt(Constants.CONFIG_TIMEOUT));
			configInfo.setInterval(ini.getInt(Constants.CONFIG_INTERVAL));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return configInfo;
	}
}
