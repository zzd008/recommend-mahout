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
 *������Ʒ��Эͬ���� 
 *1.׼�����ݣ��û�-��Ʒ-ƫ�öȣ����롢��һ����
 *2.��������ģ��
 *3.������Ʒ������ƶ�
 *4.�������򣨲���Ҫ��
 *5.�����Ƽ���
 *6.�Ƽ�
 *7.��ӡ���
 */
public class ItemCF {
	public static void main(String[] args) throws IOException, TasteException {
		//׼�����ݣ��û���Ӱ��������
		File f=new File("C:/Users/zzd/Desktop/mv-data/ratings.dat");
		//��������ģ�ͣ������ݼ��ص��ڴ��У�GroupLensDataModel����Կ��ŵ�Ӱ�������ݵ�
		DataModel dataModel = new GroupLensDataModel(f);
		//�������ƶȣ������㷨��ŷ����ã�Ƥ��ѷϵ���� ����ʹ��Ƥ��ѷϵ��
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		//ItemCf����Ҫ��������
		//�����Ƽ��� Эͬ���������֣������û��ͻ�����Ʒ������ʹ�û�����Ʒ��
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel,  similarity);
		//���û�IDΪ5���û��Ƽ�10����2398��Ӱ���Ƶĵ�Ӱ
		List<RecommendedItem> recommend = recommender.recommendedBecause(5,2398, 10);
		//��ӡ�Ƽ����
		 System.out.println("�����û�5��ǰ�������Ʒ2398���Ƽ�10�����Ƶ���Ʒ");
		for(RecommendedItem r:recommend){
			System.out.println(r);
		}
		
		
	}
}
