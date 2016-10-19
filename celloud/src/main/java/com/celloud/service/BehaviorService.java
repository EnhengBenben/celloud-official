package com.celloud.service;

import com.celloud.model.mongo.Behavior;

/**
 * 用户行为分析接口
 * 
 * @author lin
 * @date 2016年3月17日 下午1:20:12
 */
public interface BehaviorService {
	/**
	 * 保存用户行为
	 * 
	 * @param ub
	 * @return
	 * @author lin
	 * @date 2016年3月17日下午1:19:53
	 */
	public void saveUserBehavior(Behavior ub);
}
