package com.nova.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.nova.sdo.Classify;
import com.nova.sdo.TreeNode;
import com.nova.service.IClassifyService;
import com.nova.utils.Page;

/**
 * 软件分类action
 * 
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2013-1-23下午7:56:54
 * @version Revision: 1.0
 */
public class ClassifyAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Inject
	private IClassifyService classifyService;

	private Classify classify;
	private int flag;
	private List<Classify> list;
	private Page page;
	private List<TreeNode> treeNodeList;
	private List<Map<String, String>> classifyMapList;

	/**
	 * 获取所有二级分类
	 * 
	 * @return
	 */
	public String getAllSubClassifyList() {
		list = classifyService.getAllSubClassifyList();
		classifyMapList = new ArrayList<Map<String, String>>();
		for (Classify c : list) {
			Map<String, String> one = new HashMap<String, String>();
			one.put("id", c.getClassifyId() + "");
			one.put("text", c.getClassifyName());
			classifyMapList.add(one);
		}
		return SUCCESS;
	}

	/**
	 * 获取某分类的所有子分类
	 * 
	 * @return
	 */
	public String getSubClassifyById() {
		list = classifyService.selectChildNode(classify.getClassifyId());
		return SUCCESS;
	}

	/**
	 * 新增软件分类
	 * 
	 * @return
	 */
	public String createClassify() {
		flag = classifyService.createClassify(classify);
		return SUCCESS;
	}

	/**
	 * 删除软件分类
	 * 
	 * @return
	 */
	public String deleteClassify() {
		// if (classifyService.selectChildSoft(classify.getClassifyId()).size()
		// == 0
		// && classifyService.selectChildNode(classify.getClassifyId())) {
		// flag = classifyService.deleteClassify(classify.getClassifyId());
		// } else {
		// flag = -1;
		// }
		return SUCCESS;
	}

	/**
	 * 修改软件分类
	 * 
	 * @return
	 */
	public String updateClassify() {
		flag = classifyService.updateClassify(classify);
		return SUCCESS;
	}

	/**
	 * 查询所有的软件一级分类
	 * 
	 * @return
	 */
	public String getAllClassify() {
		list = classifyService.getAllClassifyList();
		return SUCCESS;
	}

	/**
	 * 查询软件分类树
	 * 
	 * @return
	 */
	public String getClassifyTree() {
		list = classifyService.getAllClassifyList();
		treeNodeList = new ArrayList<TreeNode>();
		TreeNode treeNode = new TreeNode();
		treeNode.setId(0);
		treeNode.setName("root");
		treeNode.setOpen(true);
		treeNodeList.add(treeNode);
		if (list != null) {
			for (Classify classify : list) {
				treeNode = new TreeNode();
				treeNode.setId(classify.getClassifyId());
				treeNode.setName(classify.getClassifyName());
				treeNode.setpId(classify.getClassifyPid());
				treeNodeList.add(treeNode);
			}
		}
		return SUCCESS;
	}

	/**
	 * 分页查询软件分类
	 * 
	 * @return
	 */
	public String getPageClassify() {
		list = classifyService.getPageClassify(page);
		page = new Page(page.getPageNow(), page.getPageSize(),
				classifyService.getTotalClassify());
		return SUCCESS;
	}

	/**
	 * 查看软件分类名称是否重复
	 * 
	 * @return
	 */
	public String checkClassifyName() {
		String compare = classifyService.selectClassifyName(classify
				.getClassifyName());
		flag = (compare == null || "".equals(compare)) ? 1 : 0;
		return SUCCESS;
	}

	public Classify getClassify() {
		return classify;
	}

	public void setClassify(Classify classify) {
		this.classify = classify;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<Classify> getList() {
		return list;
	}

	public void setList(List<Classify> list) {
		this.list = list;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<TreeNode> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNode> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	public List<Map<String, String>> getClassifyMapList() {
		return classifyMapList;
	}

	public void setClassifyMapList(List<Map<String, String>> classifyMapList) {
		this.classifyMapList = classifyMapList;
	}

}