package com.sduwh.match.controller;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.common.ResponseResult;
import com.sduwh.match.service.matchitem.MatchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by qxg on 17-7-19.
 */
@Controller
@RequestMapping("/item")
public class MatchItemController extends BaseController {

    @Autowired
    MatchItemService matchItemService;

    /** 通过id查找所有的mathItem*/
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseResult getMatchItemsById(@PathVariable("id") Integer id){
        if(id == null)return fail(false,"id为空");
        return success(true,"查找成功",matchItemService.selectAllByInfoId(id));
    }
}
