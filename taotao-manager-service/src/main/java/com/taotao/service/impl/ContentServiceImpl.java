package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EUDataGridResult getContentItemList(long categoryId, int pageSize, int row) {
		// 根据categoryId查询节点列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 分页处理
		PageHelper.startPage(pageSize, row);
		// 执行查询
		List<TbContent> resultList = contentMapper.selectByExample(example);
		// 执行分页信息处理
		PageInfo<TbContent> pageInfo = new PageInfo<>(resultList);
		// 返回Easyui grid object
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(resultList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult saveContentItem(TbContent content) throws Exception {
		// 补全pojo内容
		if (content == null) {
			// 需要调用exception
			throw new NullPointerException();
		}

		content.setUpdated(new Date());
		content.setCreated(new Date());
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContentItem(long contentId) throws Exception {
		contentMapper.deleteByPrimaryKey(contentId);
		return TaotaoResult.ok();
	}

}
