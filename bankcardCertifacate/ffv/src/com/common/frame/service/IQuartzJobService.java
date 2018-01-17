package com.common.frame.service;

import java.util.Map;

import com.common.base.service.ICommonService;
import com.common.frame.model.QuartzJob;
import com.common.web.page.Page;

public interface IQuartzJobService extends ICommonService<QuartzJob>{
@SuppressWarnings("unchecked")
public Page queryJobByPage(QuartzJob obj,Map param);
//public void runOnce(QuartzJob obj);
}
