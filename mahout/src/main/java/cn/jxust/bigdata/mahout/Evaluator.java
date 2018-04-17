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
 * ������
 *
 */
public class Evaluator {
	public static void main(String[] args) throws IOException, TasteException {
		 //׼������ �����ǵ�Ӱ��������
        File file = new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
        //�����ݼ��ص��ڴ��У�GroupLensDataModel����Կ��ŵ�Ӱ�������ݵ�
        DataModel dataModel = new GroupLensDataModel(file);
        //�Ƽ�������ʹ�þ�����
        //RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        //�Ƽ�������ʹ��ƽ����ֵ
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        
        RecommenderBuilder builder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
            	//��������UserCF
            	//�������ƶ�
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                //��������
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        
        // ��70%����������ѵ����ʣ�µ�30%��������
        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        //���ó�������ֵԽС��˵���Ƽ����Խ��
        System.out.println(score);
	}
}
