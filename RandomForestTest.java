package randomforest;

import java.io.IOException;
import java.text.MessageFormat;

public class RandomForestTest {

	/**
	 * 随机森林算法测试场景
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filePath = "C:\\Users\\wang4\\Desktop\\input.txt";
		String queryStr = "Age=Youth,Income=Low,Student=No,CreditRating=Fair";
		String resuleClassType = "";
		// 决策树的样本占综述的占比率
		double sampleNumRatio = 0.4;
		// 样本数据的采集特征数量占总特征的比例
		double featureNumRatio = 0.5;

		RandomForestTool tool = new RandomForestTool(filePath, sampleNumRatio,
				featureNumRatio);
		tool.constructRandomTree();

		resuleClassType = tool.judgeClassType(queryStr);

		System.out.println();
		System.out
				.println(MessageFormat.format(
						"查询属性描述{0},预测的分类结果为 BuysCompute:{1}", queryStr,
						resuleClassType));
	}

}
