package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import com.taotao.util.ExceptionUtil;
import com.taotao.util.IDUtils;

@Service
public class ItemSerivceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public TbItem getItemById(long itemId) {

		// TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	@Override
	public TaotaoResult saveItem(TbItem item, String desc, String paramData) throws Exception {
		// 需要配置产品资料然后存进去数据库
		// 生成商品id
		// 可以使用redis的自增长key，在没有redis之前使用时间+随机数策略生成
		long itemId = IDUtils.genItemId();
		// 补全不完整的字段
		// 产品Id
		item.setId(itemId);
		// '商品状态，1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		// 产品生成时间
		item.setCreated(new Date());
		// 产品更新时间
		item.setUpdated(new Date());
		// 把数据插入到商品表
		itemMapper.insert(item);
		// 添加商品描述信息
		TaotaoResult saveItemDescresult = this.saveItemDesc(itemId, desc);
		// 添加商品规格
		TaotaoResult saveItemParamDateResult = saveItemParamDate(paramData, itemId);

		if (saveItemDescresult.getStatus() != 200 || saveItemParamDateResult.getStatus() != 200) {
			throw new Exception();
		}

		return TaotaoResult.ok();
	}

	/**
	 * @param paramData
	 * @param itemId
	 */
	private TaotaoResult saveItemParamDate(String paramData, long itemId) {
		try {
			TbItemParamItem itemParamItem = new TbItemParamItem();
			itemParamItem.setCreated(new Date());
			itemParamItem.setUpdated(new Date());
			itemParamItem.setItemId(itemId);
			itemParamItem.setParamData(paramData);
			itemParamItemMapper.insert(itemParamItem);
		} catch (Exception ex) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(ex));
		}
		return TaotaoResult.ok();
	}

	/**
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult saveItemDesc(long itemId, String desc) {
		try {
			TbItemDesc itemDesc = new TbItemDesc();
			itemDesc.setItemId(itemId);
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(new Date());
			itemDescMapper.insert(itemDesc);
		} catch (Exception ex) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(ex));
		}

		return TaotaoResult.ok();
	}

	@Override
	public EUDataGridResult getItemList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "30") Integer rows) {
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

}
