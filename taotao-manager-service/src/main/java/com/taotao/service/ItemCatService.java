package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TreeNode;

public interface ItemCatService {
    public List<TreeNode> getItemCatList(long parentId);
}
