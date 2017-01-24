package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
import com.taotao.util.ExceptionUtil;
import com.taotao.util.HttpClientUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public EUDataGridResult getContentItemList(long categoryId, int pageSize, int row) {
		// 根据categoryId查询节点列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 分页处理
		PageHelper.startPage(pageSize, row);
		// 执行查询
		List<TbContent> resultList = contentMapper.selectByExampleWithBLOBs(example);
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

		try {
			// 删除已有的REDIUS服务器上的数据
			HttpClientUtils.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + contentId);
			contentMapper.deleteByPrimaryKey(contentId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult editContentItem(TbContent content) {
		try {
			// 补全pojo内容
			content.setCreated(new Date());
			content.setUpdated(new Date());
			// 删除已有的REDIUS服务器上的数据
			// HttpClientUtils.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL +
			// content.getId());
			// 更新content数据
			contentMapper.updateByPrimaryKeySelective(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return TaotaoResult.ok();
	}

}
