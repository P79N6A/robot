package com.bossbutler.mapper.system;

import java.util.List;
import java.util.Map;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.system.EmpQuery;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.system.Employee;

public interface EmpMapper extends IBaseMapper<Employee, Employee, EmpQuery> {

	public int update(Employee emp);

	List<EmpRelations> queryEmpByAccProPer(Map<String, Object> params);

	List<EmpRelations> queryEmpByAcc(Map<String, Object> params);

	public List<Employee> queryDynamic(Employee employee);
}