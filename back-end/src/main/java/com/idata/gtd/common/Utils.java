package com.idata.gtd.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 辅助类，其中行记录转换为树节点比较重要<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:41:48 | 创建
 */
public class Utils {
	private static Logger log = Logger.getLogger(Utils.class);

	/**
	 * 将map中value等于空的key去掉
	 * 
	 * @param map
	 */
	public static Map<String, Object> cleanMap(Map<String, Object> map) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		for (String key : map.keySet()) {
			log.info(key + " = " + map.get(key));
			if (map.get(key) != null && map.get(key).toString() != "") {
				resultMap.put(key, map.get(key));
			}
		}
		return resultMap;
	}

	/**
	 * Map转换为对象(Object)
	 * 
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	/**
	 * 对象(Object)转换为Map
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}

		return map;
	}
	
	/************************* 下面的方法用来将模板目录表记录构建为树  ***************************/

	public static List<TreeNode> builderTree(List<TreeNode> nodeList) {

		// 生成树
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		// 生成树 -- 添加根节点
		for (TreeNode node : nodeList) {
			if (node.getParentId() == 0) {
				treeNodeList.add(node);
			}
		}
		// 生成树 -- 递归填充子节点
		for (TreeNode node : treeNodeList) {
			node.setChildren(getChildList(node.getId(), nodeList));
		}

		return treeNodeList;
	}

	private static List<TreeNode> getChildList(Integer nodeId, List<TreeNode> sourceTreeNodeList) {

		List<TreeNode> childList = new ArrayList<TreeNode>();
		for (TreeNode node : sourceTreeNodeList) {
			if (node.getParentId().equals(nodeId)) {
				childList.add(node);
			}
		}

		for (TreeNode node : childList) {
			node.setChildren(getChildList(node.getId(), sourceTreeNodeList));
		}

		if (childList.size() == 0) {
			childList = null;
		}

		return childList;
	}

}
