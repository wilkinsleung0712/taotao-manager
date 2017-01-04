package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    // @Autowired
    // private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public EUDataGridResult getItemParamList(Integer page, Integer row) {
        // 查询商品列表
        TbItemParamExample example = new TbItemParamExample();
        // 分页处理
        PageHelper.startPage(page, row);
        List<TbItemParam> list = itemParamMapper
                .selectByExampleWithBLOBs(example);

        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
        // 创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        // 取记录总条数
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.taotao.service.ItemParamService#getItemParamByCid(long)
     */
    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        // 根据cid查询数据库
        TbItemParamExample example = new TbItemParamExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        // 根据cid查询数据库
        if (list != null && !list.isEmpty()) {
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult saveItemParam(long cid, String paramData)
            throws Exception {
        // 创建TbItemParam对象
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        // 向数据库添加数据
        this.itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

}
