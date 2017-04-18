package edu.fudan.hgmonitor.server;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import edu.fudan.hgmonitor.cache.GPUInfoCache;
import edu.fudan.hgmonitor.pojo.GPUInfo;

public class NettyServer {

	private ServerBootstrap bootstrap;
	private int port;

	public NettyServer(int port) {
		this.port = port;
	}

	public void start() {
		bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
						Executors.newCachedThreadPool()));

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(),
						new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())),
						new MonitorServerHandler());
			}
		});

		bootstrap.bind(new InetSocketAddress(port));

	}

	public void shutdown() {
		bootstrap.shutdown();
	}

	class MonitorServerHandler extends SimpleChannelUpstreamHandler {

		@SuppressWarnings("unchecked")
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
			// Send back the received message to the remote peer.
			List<GPUInfo> gpuInfos = (List<GPUInfo>) e.getMessage();
			if (gpuInfos != null && gpuInfos.size() > 0) {
				GPUInfoCache.getInstance().insertOrUpdateCache(gpuInfos.get(0).getHostIpAddr(),
						gpuInfos);
			}			
		}
	}
}
