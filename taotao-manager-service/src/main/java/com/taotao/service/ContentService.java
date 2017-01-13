package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	/**
	 * @param categoryId
	 * @param row
	 * @param pageSize
	 * @return
	 */
	public EUDataGridResult getContentItemList(long categoryId, int pageSize, int row);

	/**
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public TaotaoResult saveContentItem(TbContent content) throws Exception;

	/**
	 * @param contentId
	 * @return
	 */
	public TaotaoResult deleteContentItem(long contentId) throws Exception;

}
