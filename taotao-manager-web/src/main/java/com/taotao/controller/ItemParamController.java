package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

@RestController
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;
    
    

    @RequestMapping("/list")
    public EUDataGridResult getItemParam(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "30") Integer rows) {
        return itemParamService.getItemParamList(page, rows);
    }

    @RequestMapping("/query/itemcatid/{cid}")
    public TaotaoResult getItemParamById(@PathVariable long cid) {
        return itemParamService.getItemParamByCid(cid);
    }

    @RequestMapping("/save/{cid}")
    public TaotaoResult saveItemParamById(@PathVariable long cid, String paramData) throws Exception {
        return itemParamService.saveItemParam(cid, paramData);
    }

}