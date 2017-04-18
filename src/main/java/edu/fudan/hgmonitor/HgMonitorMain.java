package edu.fudan.hgmonitor;

import java.util.Timer;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.util.StringUtils;

import edu.fudan.hgmonitor.cache.ConfigCache;
import edu.fudan.hgmonitor.client.NettyClient;
import edu.fudan.hgmonitor.jni.NativeLibraryLoader;
import edu.fudan.hgmonitor.pojo.ConfigInfo;
import edu.fudan.hgmonitor.server.NettyServer;
import edu.fudan.hgmonitor.server.task.MonitorCacheTask;
import edu.fudan.hgmonitor.server.task.QueryGPUInfoTask;

@SpringBootApplication
public class HgMonitorMain implements EmbeddedServletContainerCustomizer {
	
	public static void main(String[] args) {
		
		if (!init(args)) {
			return;
		}
		
		ConfigInfo configInfo = ConfigCache.getInstance().getConfigInfo();
		if (NetUtil.getLocalIpAddress().equals(configInfo.getServerIp())) {
			NettyServer server = new NettyServer(configInfo.getPort());
			new Timer("QueryGPUInfoTimer").scheduleAtFixedRate(new QueryGPUInfoTask(), 0, configInfo.getInterval());
			new Timer("MonitorCacheTimer").scheduleAtFixedRate(new MonitorCacheTask(), 0, configInfo.getTimeout());
			server.start();
			SpringApplication.run(HgMonitorMain.class, args);
		} else {
			NettyClient client = new NettyClient(configInfo.getPort(),
					configInfo.getServerIp(), configInfo.getInterval());
			client.start();
		}
		
	}
	
	private static boolean init(String[] args) {
		HgMonitorCmdOption mHgMonitorCmdOption = new HgMonitorCmdOption();
		CmdLineParser parser = new CmdLineParser(mHgMonitorCmdOption);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			parser.printUsage(System.err);
		}
		
		if (mHgMonitorCmdOption.help) {
			parser.printUsage(System.out);
			return false;
		}
		
		if (StringUtils.isEmpty(mHgMonitorCmdOption.conf)) {
			parser.printUsage(System.out);
			return false;
		}
		
		if (StringUtils.isEmpty(mHgMonitorCmdOption.nativeLibs)) {
			parser.printUsage(System.out);
			return false;
		}
		
		ConfigInfo configInfo = ConfigLoader.getInstance().loadConfigInfo(mHgMonitorCmdOption.conf);
		if (!validateConfigInfo(configInfo)) {
			return false;
		}
		
		ConfigCache.getInstance().setConfigInfo(configInfo);
		
		NativeLibraryLoader.loadNativeLibraries(mHgMonitorCmdOption.nativeLibs);
		return true;
	}
	
	private static boolean validateConfigInfo(ConfigInfo configInfo) {
		if (StringUtils.isEmpty(configInfo.getServerIp())) {
			System.err.println("Server ip address not found in config file.");
			return false;
		}
		if (configInfo.getPort() == null) {
			System.err.println("Server port not found in config file.");
			return false;
		}
		
		return true;
	}

	public void customize(ConfigurableEmbeddedServletContainer container) {
		// TODO Auto-generated method stub
		container.setPort(ConfigCache.getInstance().getConfigInfo().getHttpPort());
	}
}
