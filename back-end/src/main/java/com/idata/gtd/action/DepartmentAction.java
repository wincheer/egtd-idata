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
import com.idata.gtd.entity.Department;
import com.idata.gtd.entity.ParamValue;
import com.idata.gtd.service.DepartmentService;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 向UI提供部门表的restful访问接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:43:27 | 创建
 */
@RestController
@CrossOrigin
public class DepartmentAction {

	@Autowired
	private DepartmentService depService;

	@RequestMapping(value = "/selectDepTreeList", method = RequestMethod.POST)
	public List<TreeNode> selectDepTreeList() throws Exception {

		List<Department> depList = depService.selectDepartmentList();
		List<TreeNode> depTreeNodeList = new ArrayList<TreeNode>();
		for (Department dep : depList) {
			TreeNode treeNode = new TreeNode(dep.getId(), dep.getDepName(), dep.getDepDesc(),dep.getParentId());
			depTreeNodeList.add(treeNode);
		}
		List<TreeNode> depTreeList = Utils.builderTree(depTreeNodeList);

		return depTreeList;
	}
	
	@RequestMapping(value = "/updateDep", method = RequestMethod.POST)
	public int updateDepartment(@RequestBody Department dep) {

		if (dep.getId() == null)
			return depService.insertDepartment(dep);
		else
			return depService.updateDepartment(dep);
	};
	
	@RequestMapping(value = "/deleteDep", method = RequestMethod.POST)
	public int deleteDep(@RequestBody Department dep) {

		return depService.deleteDepartment(dep.getId());
	};

}
