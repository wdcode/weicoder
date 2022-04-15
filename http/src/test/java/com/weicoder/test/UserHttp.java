package com.weicoder.test;

import com.weicoder.common.bean.Result;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserHttp {
	@POST("user/get/info")
	Result<User> info(@Query("uid") long uid);
}
