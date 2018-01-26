package com.idata.gtd.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.common.TreeNode;
import com.idata.gtd.common.Utils;
import com.idata.gtd.entity.TplGroup;
import com.idata.gtd.entity.TplProject;
import com.idata.gtd.entity.TplStage;
import com.idata.gtd.service.TemplateService;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 向UI提供模板表的restful访问接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:43:27 | 创建
 */
@RestController
@CrossOrigin
public class TemplateAction {

	@Autowired
	private TemplateService tplService;

	@RequestMapping(value = "/selectTplProjectList", method = RequestMethod.POST)
	public List<TplProject> selectTplProjectList() throws Exception {

		List<TplProject> tplProjectList = tplService.selectTplProjectList();
		return tplProjectList;
	}

	@RequestMapping(value = "/updateTplProject", method = RequestMethod.POST)
	public int updateTplProject(@RequestBody TplProject tplProject) {

		if (tplProject.getId() == null)
			return tplService.insertTplProject(tplProject);
		else
			return tplService.updateTplProject(tplProject);
	};

	@RequestMapping(value = "/deleteTplProject", method = RequestMethod.POST)
	public int deleteTplProject(@RequestBody TplProject tplProject) {

		return tplService.deleteTplProject(tplProject.getId());
	};

	/******/

	@RequestMapping(value = "/selectTplStageTreeList", method = RequestMethod.POST)
	public List<TreeNode> selectTplStageTreeList(@RequestBody TplStage tplStage) {

		List<TplStage> tplStageList = tplService.selectTplStageList(tplStage.getTplId());

		List<TreeNode> tplStageTreeNodeList = new ArrayList<TreeNode>();
		for (TplStage stage : tplStageList) {
			TreeNode treeNode = new TreeNode(stage.getId(), stage.getStageName(), stage.getParentId(), stage);
			tplStageTreeNodeList.add(treeNode);
		}
		List<TreeNode> treeList = Utils.builderTree(tplStageTreeNodeList);

		return treeList;
	}

	@RequestMapping(value = "/updateTplStage", method = RequestMethod.POST)
	public int updateTplStage(@RequestBody TplStage tplStage) {

		if (tplStage.getId() == null)
			return tplService.insertTplStage(tplStage);
		else
			return tplService.updateTplStage(tplStage);
	};

	@RequestMapping(value = "/deleteTplStage", method = RequestMethod.POST)
	public int deleteTplStage(@RequestBody TplStage tplStage) {

		return tplService.deleteTplStage(tplStage.getId());
	};

	/******/

	@RequestMapping(value = "/selectTplGroupTreeList", method = RequestMethod.POST)
	public List<TreeNode> selectTplGroupTreeList(@RequestBody TplGroup tplGroup) {

		List<TplGroup> tplGroupList = tplService.selectTplGroupList(tplGroup.getTplId());

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (TplGroup group : tplGroupList) {
			TreeNode treeNode = new TreeNode(group.getId(), group.getGroupName(), group.getParentId(), group);
			treeNodeList.add(treeNode);
		}
		List<TreeNode> treeList = Utils.builderTree(treeNodeList);

		return treeList;
	}

	@RequestMapping(value = "/updateTplGroup", method = RequestMethod.POST)
	public int updateTplGroup(@RequestBody TplGroup tplGroup) {

		if (tplGroup.getId() == null)
			return tplService.insertTplGroup(tplGroup);
		else
			return tplService.updateTplGroup(tplGroup);
	};

	@RequestMapping(value = "/deleteTplGroup", method = RequestMethod.POST)
	public int deleteTplGroup(@RequestBody TplGroup tplGroup) {

		return tplService.deleteTplGroup(tplGroup.getId());
	};

}
