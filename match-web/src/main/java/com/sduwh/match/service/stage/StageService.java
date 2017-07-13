package com.sduwh.match.service.stage;

//import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */

public interface StageService extends BaseService<Stage,Integer> {
    List<Stage> selectAllByString(String input);
    List<Stage> selectByIdList(List<Integer> ids);
}
