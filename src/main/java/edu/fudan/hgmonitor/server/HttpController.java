package edu.fudan.hgmonitor.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fudan.hgmonitor.cache.GPUInfoCache;
import edu.fudan.hgmonitor.pojo.GPUInfo;

@RestController
public class HttpController {

	@RequestMapping("/gpuInfo")
	public List<GPUInfo> getGPUInfos() {
		List<GPUInfo> gpuInfos = new ArrayList<GPUInfo>();
		Set<String> gpuInfoKeySet = GPUInfoCache.getInstance().getKeySet();
		
		for (String hostIp : gpuInfoKeySet) {
			gpuInfos.addAll(GPUInfoCache.getInstance().get(hostIp));
		}
		
		return gpuInfos;
	}
}
