package com.idata.pdm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.common.TreeNode;
import com.idata.pdm.common.Utils;
import com.idata.pdm.dao.FolderMapper;
import com.idata.pdm.dao.ProjectMapper;
import com.idata.pdm.dao.TemplateFolderMapper;
import com.idata.pdm.entity.Folder;
import com.idata.pdm.entity.Project;
import com.idata.pdm.entity.TemplateFolder;

@Service
@Transactional
public class ProjectService {
	private final static Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectMapper projectDao;
	@Autowired
	private TemplateFolderMapper templateFolderDao;
	@Autowired
	private FolderMapper folderDao;

	public int insertProject(Project project) {

		projectDao.insertProject(project);
		int projectId = project.getProjectId();
		// 获取模板对应的目录，
		List<TemplateFolder> templateFolderList = templateFolderDao.selectTemplateFolderList(project.getTemplateId());
		//转换为TreeNode格式
		List<TreeNode> folderList = new ArrayList<TreeNode>();
		for (TemplateFolder tf : templateFolderList) {
			TreeNode treeNode = new TreeNode(tf.getTemplateFolderId(), tf.getTemplateFolderName(),
					tf.getParentTemplateFolderId(), tf.getTemplateId());
			folderList.add(treeNode);
		}
		// 变成树状结构 - 父子关系
		List<TreeNode> folderTree = Utils.builderTree(folderList);
		for(TreeNode node:folderTree){
			buildChildren(node,projectId);
		}

		return projectId;
	}
	
	//通过递归遍历指定节点的子节点
	private void buildChildren(TreeNode node,int projectId){
		//转化为(项目)目录节点
		Folder folder = new Folder();
		folder.setProjectId(projectId);
		folder.setFolderName(node.getLabel());
		folder.setParentFolderId(node.getParentId());
		//保存当前结点，得到当前目录的ID
		folderDao.insertFolder(folder); 
		int folderId = folder.getFolderId();
		//递归调用
		if(node.getChildren()!=null){
			for(TreeNode _node:node.getChildren()){
				_node.setParentId(folderId);
				buildChildren(_node,projectId);
			}
		}
	}

	public List<Project> selectProjectPageList(Map<String,Object> queryParam)
	{
		int pageNo = (Integer) queryParam.get("pageNo");
		int pageSize = (Integer) queryParam.get("pageSize");
		int start = (pageNo - 1) * pageSize;

		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) queryParam.get("filter");
		logger.info(params.toString());
		params.put("start", start);
		params.put("pageSize", pageSize);
		
		return projectDao.selectProjectPageList(Utils.cleanMap(params));
	}

	public List<Project> selectProjectListByEmployeeId(int employeeId) {
		return projectDao.selectProjectListByEmployeeId(employeeId);
	}

}
