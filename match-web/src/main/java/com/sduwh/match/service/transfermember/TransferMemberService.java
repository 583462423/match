package com.sduwh.match.service.transfermember;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.model.entity.TransferMember;
import com.sduwh.match.service.BaseService;

import java.util.List;

/**
 * Created by qxg on 17-9-14.
 */
public interface TransferMemberService extends BaseService<TransferMember,Integer> {
    List<TransferMember> selectByMatchItemId(int matchItemId);

    /** 通过match_item_id删除对应的记录*/
    int deleteAllByMatchItemId(int matchItemId);
}
