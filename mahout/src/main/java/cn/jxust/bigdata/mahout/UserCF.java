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
 *�����û���Эͬ���� 
 *1.׼�����ݣ��û�-��Ʒ-ƫ�öȣ����롢��һ����
 *2.��������ģ��
 *3.�����û�������ƶ�
 *4.��������
 *5.�����Ƽ���
 *6.�Ƽ�
 *7.��ӡ���
 */
public class UserCF {
	public static void main(String[] args) throws IOException, TasteException {
		//׼�����ݣ��û���Ӱ��������
		File f=new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
		//��������ģ�ͣ������ݼ��ص��ڴ��У�GroupLensDataModel����Կ��ŵ�Ӱ�������ݵ�
		DataModel dataModel = new GroupLensDataModel(f);
		//�������ƶȣ������㷨��ŷ����ã�Ƥ��ѷϵ���� ����ʹ��Ƥ��ѷϵ��
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		//������������������㷨�����ڹ̶��������ھ���ͻ������ƶȵ��ھ� ����ʹ�û��ڹ̶��������ھ�
		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		//�����Ƽ��� Эͬ���������֣������û��ͻ�����Ʒ������ʹ�û����û���
		Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
		//���û�IDΪ5���û��Ƽ�10����Ӱ
		List<RecommendedItem> recommend = recommender.recommend(5, 10);
		//��ӡ�Ƽ����
		for(RecommendedItem r:recommend){
			System.out.println(r);
		}
		
		
	}
}
