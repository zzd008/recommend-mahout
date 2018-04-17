package cn.jxust.bigdata.mahout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

/**
 *基于用户的协同过滤 
 *1.准备数据：用户-物品-偏好度（降噪、归一化）
 *2.构建数据模型
 *3.计算用户间的相似度
 *4.计算邻域
 *5.构建推荐器
 *6.推荐
 *7.打印结果
 */
public class UserCF {
	public static void main(String[] args) throws IOException, TasteException {
		//准备数据：用户电影评分数据
		File f=new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
		//构件数据模型，将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
		DataModel dataModel = new GroupLensDataModel(f);
		//计算相似度，常用算法有欧几里得，皮尔逊系数等 这里使用皮尔逊系数
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		//计算最近邻域，有两种算法，基于固定数量的邻居域和基于相似度的邻居 这里使用基于固定数量的邻居
		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		//构建推荐器 协同过滤有两种：基于用户和基于物品，这里使用基于用户的
		Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
		//给用户ID为5的用户推荐10部电影
		List<RecommendedItem> recommend = recommender.recommend(5, 10);
		//打印推荐结果
		for(RecommendedItem r:recommend){
			System.out.println(r);
		}
		
		
	}
}
