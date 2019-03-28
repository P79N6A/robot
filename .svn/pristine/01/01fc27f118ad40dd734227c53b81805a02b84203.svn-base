package com.bossbutler.util.enums;

import com.company.util.IdWorker;

/**
 * 创建IdWorker实例
 * 
 * @author wangqiao
 */
public enum IdWorkerBuider {

	IDWORKER;

	private volatile IdWorker idWorker;

	public void build(long workerId, long datacenterId) {
		try {
			this.idWorker = new IdWorker(workerId, datacenterId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public IdWorker getIdWorker() {
		return this.idWorker;
	}

	public long nextId() {
		return this.idWorker.nextId();
	}

}
