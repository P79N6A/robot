package com.bossbutler.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.NoGeneratorMapper;

@Service
public class NoGeneratorService {

	@Autowired
	private NoGeneratorMapper nogeneratorMapper;

	@SuppressWarnings("rawtypes")
	public String getNoGeneratorByUsedCat(String usedCat) throws Exception {
		String no = "";
		Map map = nogeneratorMapper.getNoGeneratorByUsedCat(usedCat);
		if (map.size() > 0) {
			long intVal = (Long) map.get("next_value");
			int length = map.get("next_value").toString().length();
			String temp = (String) map.get("template");
			// 把数据库中的数字加一，留给下一次使用
			nogeneratorMapper.updateNoGeneratorByUsedCat(usedCat);

			// 开始号码位置
			long nStartPos = (Long) map.get("start_num");
			// 从开始位置开始的数字长度
			int nDigitalLength = (Integer) map.get("num_length");
			StringBuffer format = new StringBuffer();
			// 加上前缀
			if (StringUtils.isNotBlank(temp)) {
				format.append(temp);
			}
			// 不够位补零
			for (long i = nStartPos; i < nDigitalLength - length; i++) {
				format.append("0");
			}
			format.append(intVal);
			no = format.toString();

		} // end if
		return no;
	}
}
