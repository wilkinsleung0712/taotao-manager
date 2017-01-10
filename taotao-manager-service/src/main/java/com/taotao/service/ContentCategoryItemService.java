package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TreeNode;

public interface ContentCategoryItemService {

    public List<TreeNode> getContentCategoryItemList(long parentId);

    public TaotaoResult insertContentCategory(long parentId, String name);

    public TaotaoResult deleteContentCategory(long parentId, long nodeId)
            throws Exception;

    public TaotaoResult updateContentCategory(String name, long id) throws Exception;
}
