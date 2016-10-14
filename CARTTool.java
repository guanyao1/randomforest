package randomforest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/** 
 * CART����ع����㷨������ 
 *  
 */  
public class CARTTool {  
    // ���ŵ�ֵ����  
    private final String YES = "Yes";  
    private final String NO = "No";  
  
    // �������Ե���������,���������dataԴ���ݵ�����  
    private int attrNum;  
    private String filePath;  
    // ��ʼԴ���ݣ���һ����ά�ַ�������ģ�±������  
    private String[][] data;  
    // ���ݵ������е�����  
    private String[] attrNames;  
    // ÿ�����Ե�ֵ��������  
    private HashMap<String, ArrayList<String>> attrValue;  
  
    public CARTTool(ArrayList<String[]> dataArray) {  
        attrValue = new HashMap();  
        readData(dataArray);  
    }  
  
    /** 
     * �������ѡȡ���������ݽ��г�ʼ�� 
     * @param dataArray 
     * �Ѿ�������������� 
     */  
    public void readData(ArrayList<String[]> dataArray) {  
        data = new String[dataArray.size()][];  
        dataArray.toArray(data);  
        attrNum = data[0].length;  
        attrNames = data[0];  
    }  
  
    /** 
     * ���ȳ�ʼ��ÿ�����Ե�ֵ���������ͣ����ں���������صļ���ʱ�� 
     */  
    public void initAttrValue() {  
        ArrayList<String> tempValues;  
  
        // �����еķ�ʽ������������  
        for (int j = 1; j < attrNum; j++) {  
            // ��һ���е������¿�ʼѰ��ֵ  
            tempValues = new ArrayList();  
            for (int i = 1; i < data.length; i++) {  
                if (!tempValues.contains(data[i][j])) {  
                    // ���������Ե�ֵû����ӹ��������  
                    tempValues.add(data[i][j]);  
                }  
            }  
  
            // һ�����Ե�ֵ�Ѿ�������ϣ����Ƶ�map���Ա���  
            attrValue.put(data[0][j], tempValues);  
        }  
    }  
  
    /** 
     * ���������ָ�� 
     *  
     * @param remainData 
     *            ʣ������ 
     * @param attrName 
     *            �������� 
     * @param value 
     *            ����ֵ 
     * @param beLongValue 
     *            �����Ƿ����ڴ�����ֵ 
     * @return 
     */  
    public double computeGini(String[][] remainData, String attrName,  
            String value, boolean beLongValue) {  
        // ʵ������  
        int total = 0;  
        // ��ʵ����  
        int posNum = 0;  
        // ��ʵ����  
        int negNum = 0;  
        // ����ָ��  
        double gini = 0;  
  
        // ���ǰ��д������ұ�������  
        for (int j = 1; j < attrNames.length; j++) {  
            // �ҵ���ָ��������  
            if (attrName.equals(attrNames[j])) {  
                for (int i = 1; i < remainData.length; i++) {  
                    // ͳ������ʵ���������ںͲ�����ֵ���ͽ��л���  
                    if ((beLongValue && remainData[i][j].equals(value))  
                            || (!beLongValue && !remainData[i][j].equals(value))) {  
                        if (remainData[i][attrNames.length - 1].equals(YES)) {  
                            // �жϴ��������Ƿ�Ϊ��ʵ��  
                            posNum++;  
                        } else {  
                            negNum++;  
                        }  
                    }  
                }  
            }  
        }  
  
        total = posNum + negNum;  
        double posProbobly = (double) posNum / total;  
        double negProbobly = (double) negNum / total;  
        gini = 1 - posProbobly * posProbobly - negProbobly * negProbobly;  
  
        // ���ؼ������ָ��  
        return gini;  
    }  
  
    /** 
     * �������Ի��ֵ���С����ָ����������С������ֵ���ֺ���С�Ļ���ָ����������һ�������� 
     *  
     * @param remainData 
     *            ʣ��˭ 
     * @param attrName 
     *            �������� 
     * @return 
     */  
    public String[] computeAttrGini(String[][] remainData, String attrName) {
		String[] str = new String[2];
		// ���ո����ԵĻ�������ֵ
		String spiltValue = "";
		// ��ʱ����
		int tempNum = 0;
		// �������Ե�ֵ����ʱ����С�Ļ���ָ��
		double minGiNi = Integer.MAX_VALUE;
		ArrayList<String> valueTypes = attrValue.get(attrName);
		// ���ڴ�����ֵ��ʵ����
		HashMap<String, Integer> belongNum = new HashMap();

		for (String string : valueTypes) {
			// ���¼�����ʱ�����ֹ�0
			tempNum = 0;
			// ���д������ұ�������
			for (int i = 1; i < attrNames.length; i++) {
				// �ҵ���ָ��������
				if (attrName.equals(attrNames[i])) {
					for (int j = 1; j < remainData.length; j++) {
						// ͳ������ʵ���������ںͲ�����ֵ���ͽ��л���
						if (remainData[j][i].equals(string)) {
							tempNum++;
						}
					}
				}
			}
			belongNum.put(string, tempNum);
		}

		double tempGini = 0;
		double posProbably = 1.0;
		double negProbably = 1.0;
		for (String string : valueTypes) {
			tempGini = 0;

			posProbably = 1.0 * belongNum.get(string) / (remainData.length - 1);
			negProbably = 1 - posProbably;

			tempGini += posProbably
					* computeGini(remainData, attrName, string, true);
			tempGini += negProbably
					* computeGini(remainData, attrName, string, false);

			if (tempGini < minGiNi) {
				minGiNi = tempGini;
				spiltValue = string;
			}
		}

		str[0] = spiltValue;
		str[1] = minGiNi + "";

		return str;
	}  
  
    public void buildDecisionTree(TreeNode node, String parentAttrValue,  
            String[][] remainData, ArrayList<String> remainAttr,  
            boolean beLongParentValue) {  
        // ���Ի���ֵ  
        String valueType = "";  
        // ������������  
        String spiltAttrName = "";  
        double minGini = Integer.MAX_VALUE;  
        double tempGini = 0;  
        // ����ָ�����飬�����˻���ָ���ʹ˻���ָ���Ļ�������ֵ  
        String[] giniArray;  
  
        if (beLongParentValue) {  
            node.setParentAttrValue(parentAttrValue);  
        } else {  
            node.setParentAttrValue("!" + parentAttrValue);  
        }  
  
        if (remainAttr.size() == 0) {  
            if (remainData.length > 1) {  
                ArrayList<String> indexArray = new ArrayList();  
                for (int i = 1; i < remainData.length; i++) {  
                    indexArray.add(remainData[i][0]);  
                }  
                node.setDataIndex(indexArray);  
            }  
            return;  
        }  
  
        for (String str : remainAttr) {  
            giniArray = computeAttrGini(remainData, str);  
            tempGini = Double.parseDouble(giniArray[1]);  
  
            if (tempGini < minGini) {  
                spiltAttrName = str;  
                minGini = tempGini;  
                valueType = giniArray[0];  
            }  
        }  
        // �Ƴ���������  
        remainAttr.remove(spiltAttrName);  
        node.setAttrname(spiltAttrName);  
  
        // ���ӽڵ�,����ع����У�ÿ�ζ�Ԫ���֣��ֳ�2�����ӽڵ�  
        TreeNode[] childNode = new TreeNode[2];  
        String[][] rData;  
  
        boolean[] bArray = new boolean[] { true, false };  
        for (int i = 0; i < bArray.length; i++) {  
            // ��Ԫ������������ֵ�Ļ���  
            rData = removeData(remainData, spiltAttrName, valueType, bArray[i]);  
  
            boolean sameClass = true;  
            ArrayList<String> indexArray = new ArrayList();  
            for (int k = 1; k < rData.length; k++) {  
                indexArray.add(rData[k][0]);  
                // �ж��Ƿ�Ϊͬһ���  
                if (!rData[k][attrNames.length - 1]  
                        .equals(rData[1][attrNames.length - 1])) {  
                    // ֻҪ��1������ȣ��Ͳ���ͬ���͵�  
                    sameClass = false;  
                    break;  
                }  
            }  
  
            childNode[i] = new TreeNode();  
            if (!sameClass) {  
                // �����µĶ������ԣ������ͬ�����û����  
                ArrayList<String> rAttr = new ArrayList();  
                for (String str : remainAttr) {  
                    rAttr.add(str);  
                }  
                buildDecisionTree(childNode[i], valueType, rData, rAttr,  
                        bArray[i]);  
            } else {  
                String pAtr = (bArray[i] ? valueType : "!" + valueType);  
                childNode[i].setParentAttrValue(pAtr);  
                childNode[i].setDataIndex(indexArray);  
            }  
        }  
  
        node.setChildAttrNode(childNode);  
    }  
  
    /** 
     * ���Ի�����ϣ��������ݵ��Ƴ� 
     *  
     * @param srcData 
     *            Դ���� 
     * @param attrName 
     *            ���ֵ��������� 
     * @param valueType 
     *            ���Ե�ֵ���� 
     * @parame beLongValue 
     * 			  �����Ƿ����ڴ�ֵ���� 
     */  
    private String[][] removeData(String[][] srcData, String attrName,  
            String valueType, boolean beLongValue) {  
        String[][] desDataArray;  
        ArrayList<String[]> desData = new ArrayList();  
        // ��ɾ������  
        ArrayList<String[]> selectData = new ArrayList();  
        selectData.add(attrNames);  
  
        // ��������ת�����б��У������Ƴ�  
        for (int i = 0; i < srcData.length; i++) {  
            desData.add(srcData[i]);  
        }  
  
        // ���Ǵ�������һ���еĲ���  
        for (int j = 1; j < attrNames.length; j++) {  
            if (attrNames[j].equals(attrName)) {  
                for (int i = 1; i < desData.size(); i++) {  
                    if (desData.get(i)[j].equals(valueType)) {  
                        // ���ƥ��������ݣ����Ƴ�����������  
                        selectData.add(desData.get(i));  
                    }  
                }  
            }  
        }  
  
        if (beLongValue) {  
            desDataArray = new String[selectData.size()][];  
            selectData.toArray(desDataArray);  
        } else {  
            // ���������в��Ƴ�  
            selectData.remove(attrNames);  
            // ����ǻ��ֲ����ڴ����͵�����ʱ�������Ƴ�  
            desData.removeAll(selectData);  
            desDataArray = new String[desData.size()][];  
            desData.toArray(desDataArray);  
        }  
  
        return desDataArray;  
    }  
  
    /** 
     * �������ع����������ظ��ڵ� 
     * @return 
     */  
    public TreeNode startBuildingTree() {  
        initAttrValue();  
  
        ArrayList<String> remainAttr = new ArrayList();  
        // ������ԣ��������һ����������  
        for (int i = 1; i < attrNames.length - 1; i++) {  
            remainAttr.add(attrNames[i]);  
        }  
  
        TreeNode rootNode = new TreeNode();  
        buildDecisionTree(rootNode, "", data, remainAttr, false);  
        setIndexAndAlpah(rootNode, 0, false);  
        showDecisionTree(rootNode, 1);  
          
        return rootNode;  
    }  
  
    /** 
     * ��ʾ������ 
     *  
     * @param node 
     *            ����ʾ�Ľڵ� 
     * @param blankNum 
     *            �пո����������ʾ���ͽṹ 
     */  
    private void showDecisionTree(TreeNode node, int blankNum) {  
        System.out.println();  
        for (int i = 0; i < blankNum; i++) {  
            System.out.print("    ");  
        }  
        System.out.print("--");  
        // ��ʾ���������ֵ  
        if (node.getParentAttrValue() != null  
                && node.getParentAttrValue().length() > 0) {  
            System.out.print(node.getParentAttrValue());  
        } else {  
            System.out.print("--");  
        }  
        System.out.print("--");  
  
        if (node.getDataIndex() != null && node.getDataIndex().size() > 0) {  
            String i = node.getDataIndex().get(0);  
            System.out.print("��" + node.getNodeIndex() + "�����:"  
                    + data[Integer.parseInt(i)][attrNames.length - 1]);  
            System.out.print("[");  
            for (String index : node.getDataIndex()) {  
                System.out.print(index + ", ");  
            }  
            System.out.print("]");  
        } else {  
            // �ݹ���ʾ�ӽڵ�  
            System.out.print("��" + node.getNodeIndex() + ":"  
                    + node.getAttrname() + "��");  
            if (node.getChildAttrNode() != null) {  
                for (TreeNode childNode : node.getChildAttrNode()) {  
                    showDecisionTree(childNode, 2 * blankNum);  
                }  
            } else {  
                System.out.print("��  Child Null��");  
            }  
        }  
    }  
  
    /** 
     * Ϊ�ڵ��������кţ�������ÿ���ڵ������ʣ����ں����֦ 
     *  
     * @param node 
     *            ��ʼ��ʱ������Ǹ��ڵ� 
     * @param index 
     *            ��ʼ�������ţ���1��ʼ 
     * @param ifCutNode 
     *            �Ƿ���Ҫ��֦ 
     */  
    private void setIndexAndAlpah(TreeNode node, int index, boolean ifCutNode) {  
        TreeNode tempNode;  
        // ��С�����۽ڵ㣬��������֦�Ľڵ�  
        TreeNode minAlphaNode = null;  
        double minAlpah = Integer.MAX_VALUE;  
        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();  
  
        nodeQueue.add(node);  
        while (nodeQueue.size() > 0) {  
            index++;  
            // �Ӷ���ͷ����ȡ�׸��ڵ�  
            tempNode = nodeQueue.poll();  
            tempNode.setNodeIndex(index);  
            if (tempNode.getChildAttrNode() != null) {  
                for (TreeNode childNode : tempNode.getChildAttrNode()) {  
                    nodeQueue.add(childNode);  
                }  
                computeAlpha(tempNode);  
                if (tempNode.getAlpha() < minAlpah) {  
                    minAlphaNode = tempNode;  
                    minAlpah = tempNode.getAlpha();  
                } else if (tempNode.getAlpha() == minAlpah) {  
                    // ���������ֵһ�����Ƚϰ�����Ҷ�ӽڵ��������֦�ж�Ҷ�ӽڵ����Ľڵ�  
                    if (tempNode.getLeafNum() > minAlphaNode.getLeafNum()) {  
                        minAlphaNode = tempNode;  
                    }  
                }  
            }  
        }  
  
        if (ifCutNode) {  
            // �������ļ�֦���������Һ��ӽڵ�Ϊnull  
            minAlphaNode.setChildAttrNode(null);  
        }  
    }  
  
    /** 
     * Ϊ��Ҷ�ӽڵ���������ۣ�����ĺ��֦���õ���CCP���۸��Ӷȼ�֦ 
     *  
     * @param node 
     *            ������ķ�Ҷ�ӽڵ� 
     */  
    private void computeAlpha(TreeNode node) {  
        double rt = 0;  
        double Rt = 0;  
        double alpha = 0;  
        // ��ǰ�ڵ����������  
        int sumNum = 0;  
        // ���ٵ�ƫ����  
        int minNum = 0;  
  
        ArrayList<String> dataIndex;  
        ArrayList<TreeNode> leafNodes = new ArrayList();  
  
        addLeafNode(node, leafNodes);  
        node.setLeafNum(leafNodes.size());  
        for (TreeNode attrNode : leafNodes) {  
            dataIndex = attrNode.getDataIndex();  
  
            int num = 0;  
            sumNum += dataIndex.size();  
            for (String s : dataIndex) {  
                // ͳ�Ʒ��������е�����ʵ����  
                if (data[Integer.parseInt(s)][attrNames.length - 1].equals(YES)) {  
                    num++;  
                }  
            }  
            minNum += num;  
  
            // ȡС������ֵ����  
            if (1.0 * num / dataIndex.size() > 0.5) {  
                num = dataIndex.size() - num;  
            }  
  
            rt += (1.0 * num / (data.length - 1));  
        }  
          
        //ͬ��ȡ����ƫ����ǲ���  
        if (1.0 * minNum / sumNum > 0.5) {  
            minNum = sumNum - minNum;  
        }  
  
        Rt = 1.0 * minNum / (data.length - 1);  
        alpha = 1.0 * (Rt - rt) / (leafNodes.size() - 1);  
        node.setAlpha(alpha);  
    }  
  
    /** 
     * ɸѡ���ڵ���������Ҷ�ӽڵ��� 
     *  
     * @param node 
     *            ��ɸѡ�ڵ� 
     * @param leafNode 
     *            Ҷ�ӽڵ��б����� 
     */  
    private void addLeafNode(TreeNode node, ArrayList<TreeNode> leafNode) {  
        ArrayList<String> dataIndex;  
  
        if (node.getChildAttrNode() != null) {  
            for (TreeNode childNode : node.getChildAttrNode()) {  
                dataIndex = childNode.getDataIndex();  
                if (dataIndex != null && dataIndex.size() > 0) {  
                    // ˵���˽ڵ�ΪҶ�ӽڵ�  
                    leafNode.add(childNode);  
                } else {  
                    // ������Ƿ�Ҷ�ӽڵ�������ݹ����  
                    addLeafNode(childNode, leafNode);  
                }  
            }  
        }  
    }  
}
