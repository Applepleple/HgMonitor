package edu.fudan.hgmonitor.client;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import edu.fudan.hgmonitor.monitor.GPUMonitor;
import edu.fudan.hgmonitor.pojo.GPUInfo;

public class NettyClient {

	private ClientBootstrap bootstrap;
	private int serverPort;
	private String serverIp;
	private int interval;
	private Logger logger = Logger.getLogger(NettyClient.class);

	public NettyClient(int serverPort, String serverIp, int interval) {
		this.serverPort = serverPort;
		this.serverIp = serverIp;
		this.interval = interval;
	}

	public void start() {
		bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(),
						new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())),
						new MonitorClientHandler());
			}
		});
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(serverIp, serverPort));

		while (!future.isCancelled()) {
			try {
				List<GPUInfo> gpuInfos = GPUMonitor.getInstance().getLocalGPUInfoList();
				if (gpuInfos != null) {
					future.getChannel().write(gpuInfos);;
				}
				
				// bootstrap.getPipelineFactory().getPipeline().getChannel().write(gpuInfos);
				Thread.sleep(interval);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		bootstrap.releaseExternalResources();
	}

	public void shutdown() {
		bootstrap.releaseExternalResources();
		bootstrap.shutdown();
	}

	class MonitorClientHandler extends SimpleChannelHandler {

		@Override
		public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
			logger.info("Connected to " + serverIp);
		}
	}
}
