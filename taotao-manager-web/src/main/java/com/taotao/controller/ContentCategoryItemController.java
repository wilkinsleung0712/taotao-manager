package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TreeNode;
import com.taotao.service.ContentCategoryItemService;

@Controller
@RequestMapping("content/category")
public class ContentCategoryItemController {

    @Autowired
    private ContentCategoryItemService contentCategoryItemService;

    @RequestMapping("/list")
    @ResponseBody
    public List<TreeNode> getContentCategoryList(
            @RequestParam(value = "id", defaultValue = "0") long parentId) {
        return contentCategoryItemService.getContentCategoryItemList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createContentCategoryItem(long parentId, String name) {
        // 返回taotaoresult
        return contentCategoryItemService.insertContentCategory(parentId, name);
    }

    @RequestMapping("/delete/")
    @ResponseBody
    public TaotaoResult deleteContentCategoryItem(long parentId, long id)
            throws Exception {
        // 返回taotaoresult
        return contentCategoryItemService.deleteContentCategory(parentId, id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategoryItem(long id, String name)
            throws Exception {
        // 返回taotaoresult
        return contentCategoryItemService.updateContentCategory(name, id);
    }
}
