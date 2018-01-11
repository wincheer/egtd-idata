package com.idata.pdm.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata.pdm.common.TreeNode;
import com.idata.pdm.common.Utils;
import com.idata.pdm.entity.TemplateFolder;
import com.idata.pdm.service.TemplateFolderService;

@RestController
@CrossOrigin
public class TemplateFolderAction {
	@Autowired
	private TemplateFolderService templateFolderService;

	@RequestMapping(value = "/templateFoderList", method = RequestMethod.GET)
	public List<TreeNode> selectTemplateFolderList(@RequestParam int templateId) throws Exception {

		List<TemplateFolder> templateFolderList = templateFolderService.selectTemplateFolderList(templateId);
		
		List<TreeNode> folderList = new ArrayList<TreeNode>();
		for (TemplateFolder tf : templateFolderList) {
			TreeNode treeNode = new TreeNode(tf.getTemplateFolderId(), tf.getTemplateFolderName(),
					tf.getParentTemplateFolderId(), tf.getTemplateId());
			folderList.add(treeNode);
		}
		
		List<TreeNode> folderTree = Utils.builderTree(folderList);

		return folderTree;
	}
	
	@RequestMapping(value = "/insertTemplateFolder", method = RequestMethod.POST)
	public int insertTemplateFolder(@RequestBody TemplateFolder folder) {

		int employeeId = templateFolderService.insertTemplateFolder(folder);

		return employeeId;
	}
	
	@RequestMapping(value = "/removeTemplateFolder", method = RequestMethod.GET)
	public int removeTemplateFolder(@RequestParam int templateFolderId) {
		return templateFolderService.deleteTemplateFolder(templateFolderId);
	}

}