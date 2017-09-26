package com.sduwh.match.service.middlecheck;

import com.sduwh.match.model.entity.MiddleCheck;
import com.sduwh.match.service.BaseService;

/**
 * Created by qxg on 17-9-11.
 */
public interface MiddleCheckService extends BaseService<MiddleCheck,Integer> {
    MiddleCheck selectByMatchItemId(int matchItemId);
    int updateByMatchItemId(MiddleCheck middleCheck);
    int updateByMatchItemIdSelective(MiddleCheck middleCheck);

    /** 通过match_item_id删除对应的记录*/
    int deleteAllByMatchItemId(int matchItemId);
}
