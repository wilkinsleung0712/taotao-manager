package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.pojo.TreeNode;
import com.taotao.service.ContentCategoryItemService;

@Service
public class ContentCategoryItemServiceImpl
        implements ContentCategoryItemService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<TreeNode> getContentCategoryItemList(long parentId) {
        // 根据parentid查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbContentCategory> list = contentCategoryMapper
                .selectByExample(example);
        // 创建返回值
        List<TreeNode> resultList = new ArrayList<>();
        for (TbContentCategory contentCategory : list) {
            // 创建一个节点
            TreeNode node = new TreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent() ? "closed" : "open");
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        // 创建一个pojo
        TbContentCategory contentCategoryItem = new TbContentCategory();
        contentCategoryItem.setParentId(parentId);
        contentCategoryItem.setName(name);
        contentCategoryItem.setCreated(new Date());
        contentCategoryItem.setUpdated(new Date());
        contentCategoryItem.setIsParent(false);
        // '状态。可选值:1(正常),2(删除)',
        contentCategoryItem.setStatus(1);
        contentCategoryItem.setSortOrder(1);
        // 添加记录
        contentCategoryMapper.insert(contentCategoryItem);
        // 查看父节点的isParent列是否为true，如果不是true改成true
        TbContentCategory parentContentCategory = this.contentCategoryMapper
                .selectByPrimaryKey(parentId);
        if (parentContentCategory != null
                && !parentContentCategory.getIsParent()) {
            parentContentCategory.setIsParent(true);
            parentContentCategory.setUpdated(new Date());
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
        return TaotaoResult.ok(contentCategoryItem);
    }

    @Override
    public TaotaoResult deleteContentCategory(long parentId, long nodeId)
            throws Exception {

        // 删除制定的id
        this.contentCategoryMapper.deleteByPrimaryKey(nodeId);

        // 删除制定的id的子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(nodeId);
        this.contentCategoryMapper.deleteByExample(example);

        // 查找parentId节点并更改节点属性
        criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = this.contentCategoryMapper
                .selectByExample(example);
        if (list != null && !list.isEmpty()) {
            // 不需要改动parentNode属性
        } else {
            // 需要改动parentNode属性
            TbContentCategory parentNode = this.contentCategoryMapper
                    .selectByPrimaryKey(parentId);
            parentNode.setIsParent(false);
            parentNode.setUpdated(new Date());
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategory(String name, long id)
            throws Exception {
        TbContentCategory node = this.contentCategoryMapper
                .selectByPrimaryKey(id);
        if (node != null) {
            node.setName(name);
            node.setUpdated(new Date());
        }
        this.contentCategoryMapper.updateByPrimaryKey(node);
        return TaotaoResult.ok(node);
    }

}
