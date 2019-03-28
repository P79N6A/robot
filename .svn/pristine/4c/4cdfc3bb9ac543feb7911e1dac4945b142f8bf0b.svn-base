package com.bossbutler.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

public class ZkCuratorService {

	private static Logger log = Logger.getLogger(ZkCuratorService.class);

	private static final long zkInitFinalData = System.currentTimeMillis();

	private CuratorFramework curatorClient = null;
	private String applicationName = null;
	private String hostPort = null;

	public ZkCuratorService(CuratorFramework client, String appName, String host) {
		this.curatorClient = client;
		this.applicationName = appName;
		this.hostPort = host;
	}

	public void init() throws Exception {
		String path = "/bbw/" + applicationName + "/hosts/" + hostPort;
		if (curatorClient.checkExists().forPath(path) != null) {
			curatorClient.delete().forPath(path);
		}
		curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, long2Bytes(zkInitFinalData));
	}

	public void destroy() throws IOException {
		curatorClient.close();
	}

	/**
	 * 创建分布式锁
	 * 
	 * @param name
	 * @return
	 */
	public InterProcessLock newLock(String name) {
		return new InterProcessMutex(curatorClient, "/locks/" + applicationName + "/" + name);
	}

	/**
	 * 用于多进程间定时任务的单点执行检查：<br/>
	 * 若返回结果为false则不需要执行任务， 为true则必须执行任务
	 * 
	 * @param taskName
	 *            任务名， 项目中唯一
	 * @return
	 */
	public boolean isTaskNeedRun(String taskName) {
		String path = "/tasks/" + applicationName + "/" + taskName;
		try {
			if (curatorClient.checkExists().forPath(path) == null) {
				try {
					curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, hostPort.getBytes(StandardCharsets.UTF_8));
					return true;
				} catch (Exception e) {
					log.error("create task node error", e);
					return false;
				}
			}
			String id = new String(curatorClient.getData().forPath(path), StandardCharsets.UTF_8);
			return id.equals(hostPort);
		} catch (Exception e) {
			log.error("check task error: " + path, e);
		}
		return false;
	}

	protected static byte[] long2Bytes(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	protected static long bytes2Long(byte[] byteNum) {
		long num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

}
