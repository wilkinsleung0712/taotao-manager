package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
public class ContentItemController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(@RequestParam(defaultValue = "0") long categoryId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int rows) {
		return contentService.getContentItemList(categoryId, page, rows);
	}

	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content) throws Exception {
		return contentService.saveContentItem(content);

	}

	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(@RequestParam long ids) throws Exception {
		return contentService.deleteContentItem(ids);
	}

	@RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult editContent(TbContent content) {
		return contentService.editContentItem(content);
	}

}
