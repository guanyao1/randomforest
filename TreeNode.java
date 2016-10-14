package randomforest;

import java.util.ArrayList;

public class TreeNode {

	/* �ڵ���������* */
	private String attrname;

	/* �ڵ��������* */
	private int nodeIndex;

	/* ������Ҷ�ӽڵ���* */
	private int leafNum;

	/* �ڵ������* */
	private double alpha;

	/* ���׷�������ֵ* */
	private String parentAttrValue;

	/* ���ӽڵ�* */
	private TreeNode[] childAttrNode;

	/* ���ݼ�¼����* */
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
