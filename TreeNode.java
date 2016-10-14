package randomforest;

import java.util.ArrayList;

public class TreeNode {

	/* 节点属性名称* */
	private String attrname;

	/* 节点索引标号* */
	private int nodeIndex;

	/* 包含的叶子节点数* */
	private int leafNum;

	/* 节点误差率* */
	private double alpha;

	/* 父亲分类属性值* */
	private String parentAttrValue;

	/* 孩子节点* */
	private TreeNode[] childAttrNode;

	/* 数据记录索引* */
	private ArrayList<String> dataIndex;

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public int getLeafNum() {
		return leafNum;
	}

	public void setLeafNum(int leafNum) {
		this.leafNum = leafNum;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public String getParentAttrValue() {
		return parentAttrValue;
	}

	public void setParentAttrValue(String parentAttrValue) {
		this.parentAttrValue = parentAttrValue;
	}

	public TreeNode[] getChildAttrNode() {
		return childAttrNode;
	}

	public void setChildAttrNode(TreeNode[] childAttrNode) {
		this.childAttrNode = childAttrNode;
	}

	public ArrayList<String> getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(ArrayList<String> dataIndex) {
		this.dataIndex = dataIndex;
	}

}
