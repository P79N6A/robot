package com.bossbutler.util.funs;

import com.bossbutler.pojo.complaint.ComplaintModel;
import com.google.common.base.Function;

public class ComplaintFuns {

	public static Function<ComplaintModel,Long> getAccountIdList(){
		return new Function<ComplaintModel,Long>(){
			@Override
			public Long apply(ComplaintModel entity) {
				return entity.getAccountId();
			};
		};
	}
	public static Function<ComplaintModel,Long> getOrgIdList(){
		return new Function<ComplaintModel,Long>(){
			@Override
			public Long apply(ComplaintModel entity) {
				return entity.getOrgId();
			};
		};
	}

}