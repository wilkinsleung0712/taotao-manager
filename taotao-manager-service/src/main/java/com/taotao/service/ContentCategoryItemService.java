package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TreeNode;

public interface ContentCategoryItemService {

    public List<TreeNode> getContentCategoryItemList(long parentId);
    
    public TaotaoResult insertContentCategory(long parentId, String name);
}
