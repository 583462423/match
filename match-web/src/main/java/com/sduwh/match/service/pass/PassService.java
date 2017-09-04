package com.sduwh.match.service.pass;

import com.sduwh.match.model.entity.Pass;
import com.sduwh.match.service.BaseService;

import java.util.List;

/**
 * Created by qxg on 17-9-1.
 */
public interface PassService extends BaseService<Pass,Integer> {
    Pass selectByUserAndItem(int userId,int matchItemId);
    //判断当前pass表是否通过了审核
    boolean checkPass(Pass pass);
    List<Pass> selectByUserId(int userId);
}
