package com.taotao.service;

public interface ItemParamItemService {

    /**
     * 接收商品id查询规格参数表。根据返回的规格参数生成html返回html
     * @param itemId
     * @return
     */
    public String getItemParamItemByItemId(long itemId); 
}
