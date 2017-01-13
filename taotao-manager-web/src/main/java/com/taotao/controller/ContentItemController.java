package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentItemController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/query/list")
	@ResponseBody 
	public EUDataGridResult getContentList(@RequestParam(defaultValue = "0") long categoryId,
			@RequestParam(defaultValue = "1") int pageSize, @RequestParam(defaultValue = "30") int row) {
		return contentService.getContentItemList(categoryId, pageSize, row);
	}

	@RequestMapping("/save")
	@ResponseBody 
	public TaotaoResult saveContent(TbContent content) throws Exception {
		return contentService.saveContentItem(content);

	}

	@RequestMapping("/delete")
	@ResponseBody 
	public TaotaoResult deleteContent(@RequestBody long ids) throws Exception {
		return contentService.deleteContentItem(ids);
	}
}
