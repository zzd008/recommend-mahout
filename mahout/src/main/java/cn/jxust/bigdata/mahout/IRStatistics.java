package cn.jxust.bigdata.mahout;
import java.io.File;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;


/**
 *��ȡ�Ƽ�����Ĳ�׼�ʺ��ٻ���
 *
 */
public class IRStatistics {
	public static void main(String[] args) throws Exception {
        //׼������ �����ǵ�Ӱ��������
        File file = new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
        //�����ݼ��ص��ڴ��У�GroupLensDataModel����Կ��ŵ�Ӱ�������ݵ�
        DataModel dataModel = new GroupLensDataModel(file);
        
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
            	//��������UserCF
            	//�������ƶ�
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
              //��������
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // �����Ƽ�4�����ʱ�Ĳ�׼�ʺ��ٻ���
        //ʹ�������������趨�����ڵĲ���
        //4��ʾ"precision and recall at 4"���൱���Ƽ�top4��Ȼ����top-4���Ƽ��ϼ���׼ȷ�ʺ��ٻ���
        IRStatistics stats = (IRStatistics) statsEvaluator.evaluate(recommenderBuilder, null, dataModel, null, 4, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        System.out.println(((org.apache.mahout.cf.taste.eval.IRStatistics) stats).getPrecision());
        System.out.println(((org.apache.mahout.cf.taste.eval.IRStatistics) stats).getRecall());
    }

}
