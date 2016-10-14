package randomforest;

import java.io.IOException;
import java.text.MessageFormat;

public class RandomForestTest {

	/**
	 * ���ɭ���㷨���Գ���
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filePath = "C:\\Users\\wang4\\Desktop\\input.txt";
		String queryStr = "Age=Youth,Income=Low,Student=No,CreditRating=Fair";
		String resuleClassType = "";
		// ������������ռ������ռ����
		double sampleNumRatio = 0.4;
		// �������ݵĲɼ���������ռ�������ı���
		double featureNumRatio = 0.5;

		RandomForestTool tool = new RandomForestTool(filePath, sampleNumRatio,
				featureNumRatio);
		tool.constructRandomTree();

		resuleClassType = tool.judgeClassType(queryStr);

		System.out.println();
		System.out
				.println(MessageFormat.format(
						"��ѯ��������{0},Ԥ��ķ�����Ϊ BuysCompute:{1}", queryStr,
						resuleClassType));
	}

}
