package randomforest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * ������
 * 
 * **/
public class DecisionTree {

	// ���ĸ��ڵ�
	TreeNode rootNode;
	// ���ݵ�����������
	String[] featureNames;
	// �����������������
	ArrayList<String[]> datas;
	// ����������Ĺ�����
	CARTTool tool;

	public DecisionTree(ArrayList<String[]> datas) {
		this.datas = datas;
		this.featureNames = datas.get(0);

		tool = new CARTTool(datas);
		// ͨ��CART��������о������Ĺ��������������ĸ��ڵ�
		rootNode = tool.startBuildingTree();
	}

	/**
	 * ����ָ�������������������������ж�
	 * 
	 * @param features
	 * 
	 */
	public String decideClassType(String features) {
		String classType = "";
		// ��ѯ������
		String[] queryFeatures;
		// �ڱ��������ж�Ӧ�Ĳ�ѯ������ֵ����
		ArrayList<String[]> featureStrs;

		featureStrs = new ArrayList();
		queryFeatures = features.split(",");

		String[] array;
		for (String name : featureNames) {
			for (String featureValue : queryFeatures) {
				array = featureValue.split("=");
				// ����Ӧ������ֵ���뵽�б���
				if (array[0].equals(name)) {
					featureStrs.add(array);
				}
			}
		}

		// ��ʼ�Ӹ��ڵ����µݹ�����
		classType = recusiveSearchClassType(rootNode, featureStrs);
		return classType;
	}

	/*
	 * �ݹ�����������ѯ���Եķ������
	 * 
	 * @param node
	 *            ��ǰ�������Ľڵ�
	 * @param remainFeatures
	 *            ʣ��δ�жϵ�����
	 * @return
	 */
	private String recusiveSearchClassType(TreeNode node,
			ArrayList<String[]> remainFeatures) {
		String classType = null;

		// ����ڵ���������ݵ�id������˵�����ൽ����
		if (node.getDataIndex() != null && node.getDataIndex().size() > 0) {
			classType = judgeClassType(node.getDataIndex());

			return classType;
		}

		// ȡ��ʣ�������е�һ��ƥ��������Ϊ��ǰ���ж���������
		String[] currentFeature = null;
		for (String[] featueValue : remainFeatures) {
			if (node.getAttrname().equals(featueValue[0])) {
				currentFeature = featueValue;
				break;
			}
		}

		for (TreeNode childNode : node.getChildAttrNode()) {
			// Ѱ�ҽڵ������ڴ�����ֵ�ķ�֧
			if (childNode.getParentAttrValue().equals(currentFeature[1])) {
				remainFeatures.remove(currentFeature);
				classType = recusiveSearchClassType(childNode, remainFeatures);
				// ����ҵ��˷���������ֱ������ѭ��
				break;
			} else {
				// ���еڶ���������жϼ���!���ŵ����
				String value = childNode.getParentAttrValue();
				if (value != null && value.charAt(0) == '!') {
					// ȥ����һ��"!"�ַ�
					value = value.substring(1, value.length());
					

					if (!value.equals(currentFeature[1])) {
						remainFeatures.remove(currentFeature);
						classType = recusiveSearchClassType(childNode,
								remainFeatures);
						break;
					}

				}
			}
		}
		return classType;
	}

	/**
	 * ���ݵõ��������з���������ľ���
	 * 
	 * @param dataIndex
	 *            ���ݷ��������������
	 * @return
	 */
	private String judgeClassType(ArrayList<String> dataIndex) {
		// �������ֵ
		String resultClassType = "";
		String classType = "";
		int count = 0;
		int temp = 0;
		Map<String, Integer> type2Num = new HashMap<String, Integer>();

		for (String index : dataIndex) {
			temp = Integer.parseInt(index);
			// ȡ�����һ�еľ����������
			classType = datas.get(temp)[featureNames.length - 1];

			if (type2Num.containsKey(classType)) {
				// �������Ѿ����ڣ���ʹ������1
				count = type2Num.get(classType);
				count++;
			} else {
				count = 1;
			}
			type2Num.put(classType, count);
		}

		// ѡ���������֧�ּ�������һ�����ֵ
		count = -1;
		for (Map.Entry entry : type2Num.entrySet()) {
			int entryValue = Integer.parseInt(entry.getValue().toString());
			if (entryValue > count) {  
				count = entryValue;  
	            resultClassType = (String) entry.getKey();  
	        }  
	    }  
		return resultClassType;
	}
}
