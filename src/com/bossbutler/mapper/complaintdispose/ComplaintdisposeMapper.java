package com.bossbutler.mapper.complaintdispose;


import java.util.List;

import com.bossbutler.pojo.complaintdispose.ComplaintdisposeModel;


public interface ComplaintdisposeMapper {
	

	int insert (ComplaintdisposeModel complaintdisposeModel);
	
     List<ComplaintdisposeMapper> queryDynamic(ComplaintdisposeMapper complaintdisposeMapper);

}
