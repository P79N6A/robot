package com.bossbutler.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.ConfigureDbMapper;

@Service
public class ConfigureDbService {

	@Autowired
	private ConfigureDbMapper configureDbMapper;

	public Map<String, Integer> getCenterIdWorkId(String district, String station) {
		return configureDbMapper.selectCenterIdWorkId(district, station);
	}

}