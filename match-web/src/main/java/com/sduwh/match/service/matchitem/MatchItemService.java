package com.sduwh.match.service.matchitem;

import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.service.BaseService;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 */
public interface MatchItemService extends BaseService<MatchItem,Integer> {
    List<MatchItem> selectAllByStringIds(String ids);
}
