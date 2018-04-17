package cn.jxust.bigdata.mahout;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

/**
 * 评估器
 *
 */
public class Evaluator {
	public static void main(String[] args) throws IOException, TasteException {
		 //准备数据 这里是电影评分数据
        File file = new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        //推荐评估，使用均方根
        //RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        //推荐评估，使用平均差值
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        
        RecommenderBuilder builder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
            	//这里评估UserCF
            	//计算相似度
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                //计算邻域
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        
        // 用70%的数据用作训练，剩下的30%用来测试
        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        //最后得出的评估值越小，说明推荐结果越好
        System.out.println(score);
	}
}
