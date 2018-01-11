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
import com.idata.pdm.entity.Folder;
import com.idata.pdm.service.FolderService;

@RestController
@CrossOrigin
public class FolderAction
{
	@Autowired
	private FolderService folderService;

	@RequestMapping(value = "/folderList", method = RequestMethod.GET)
	public List<TreeNode> selectFolderList(@RequestParam int projectId) throws Exception {

		List<Folder> projectFolderList = folderService.selectFolderList(projectId);
		List<TreeNode> folderList = new ArrayList<TreeNode>();
		for (Folder pf : projectFolderList) {
			TreeNode treeNode = new TreeNode(pf.getFolderId(), pf.getFolderName(),
					pf.getParentFolderId(), pf.getProjectId());
			folderList.add(treeNode);
		}
		List<TreeNode> folderTree = Utils.builderTree(folderList);
		
		//--begin 以下为测试，不合适就删除
		//List<TreeNode> _treeNodeList = new ArrayList<TreeNode>();
		//TreeNode _root = new TreeNode(0, "根目录", null, projectId);
		//_root.setChildren(folderTree);
		//_treeNodeList.add(_root);
		//return _treeNodeList;
		//--end

		return folderTree;
	}
	
	@RequestMapping(value = "/insertFolder", method = RequestMethod.POST)
	public int insertFolder(@RequestBody Folder folder) {

		int folderId = folderService.insertFolder(folder);

		return folderId;
	}
	
	@RequestMapping(value = "/removeFolder", method = RequestMethod.GET)
	public int removeFolder(@RequestParam int folderId) {
		return folderService.deleteFolder(folderId);
	}
}