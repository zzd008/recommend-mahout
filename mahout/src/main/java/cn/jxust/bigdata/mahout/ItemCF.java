package cn.jxust.bigdata.mahout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

/**
 *基于物品的协同过滤 
 *1.准备数据：用户-物品-偏好度（降噪、归一化）
 *2.构建数据模型
 *3.计算物品间的相似度
 *4.计算邻域（不需要）
 *5.构建推荐器
 *6.推荐
 *7.打印结果
 */
public class ItemCF {
	public static void main(String[] args) throws IOException, TasteException {
		//准备数据：用户电影评分数据
		File f=new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
		//构件数据模型，将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
		DataModel dataModel = new GroupLensDataModel(f);
		//计算相似度，常用算法有欧几里得，皮尔逊系数等 这里使用皮尔逊系数
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		//ItemCf不需要计算邻域
		//构建推荐器 协同过滤有两种：基于用户和基于物品，这里使用基于物品的
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel,  similarity);
		//给用户ID为5的用户推荐10部和2398电影相似的电影
		List<RecommendedItem> recommend = recommender.recommendedBecause(5,2398, 10);
		//打印推荐结果
		 System.out.println("根据用户5当前浏览的商品2398，推荐10个相似的商品");
		for(RecommendedItem r:recommend){
			System.out.println(r);
		}
		
		
	}
}
