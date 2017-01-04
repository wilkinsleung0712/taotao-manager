package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;

public interface ItemParamService {
    public EUDataGridResult getItemParamList(Integer page, Integer row);

    /**
     * 查询是否有一定义的规格类
     * 
     * @param cid
     *            规格类ID
     * @return 返回一个TaotaoResult Object, 如果有已经定义好的规格类,返回第一条合适的id, 如果没有则不返回任何值.
     */
    public TaotaoResult getItemParamByCid(long cid);

    /**
     * 保存新规格
     * 
     * @param cid
     *            规格类ID
     * @param itemParam
     *            规格类数据
     * @return 返回一个TaotaoResult Object
     * @throws Exception 
     */
    public TaotaoResult saveItemParam(long cid, String itemParam) throws Exception;
}
