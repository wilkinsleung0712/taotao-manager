package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {

    TbItem getItemById(long itemId);

    /**
     * @param item
     * @param desc
     * @param paramData - the param data related to the actual item
     * @return
     * @throws Exception
     */
    TaotaoResult saveItem(TbItem item, String desc, String paramData) throws Exception;

    EUDataGridResult getItemList(Integer page, Integer rows);
}
