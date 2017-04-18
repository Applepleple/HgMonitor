package edu.fudan.hgmonitor;

import org.kohsuke.args4j.Option;

public class HgMonitorCmdOption {

	@Option(name="-c", aliases="-config", usage="Config file path")
	public String conf;
	
	@Option(name="-n", aliases="-native", usage="Native libraries")
	public String nativeLibs;
	
	@Option(name="-h", aliases="-help", usage="Help command")
	public boolean help;
	
}
