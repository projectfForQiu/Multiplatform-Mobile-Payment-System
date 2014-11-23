package com.billme.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class RecommandItem {
	
	
	public Random rd = new Random();;
	public static double[] tagValue = null;
	public static double maxMoney = 0;
	
	public int[] RecommandOneItem(ArrayList<Item> items) {

		HashMap<String, Double> tags = new HashMap<String, Double>();

		for (int i = 0; i < items.size(); ++i) {
			Item tempItem = items.get(i);

			// 获取Item的tag值
			HashMap<String, Double> tempTags = tempItem.getTag();
			Set<String> keySet = tempTags.keySet();
			while (keySet.iterator().hasNext()) {
				String key = keySet.iterator().next();
				if (tags.containsKey(key)) {
					double tempValue = tags.get(key) + tempTags.get(key);
					tags.put(key, tempValue);
				} else {
					tags.put(key, tempTags.get(key));
				}
			}
			// 获取价格约束
			maxMoney += Double.valueOf(tempItem.getPrice());
		}
		
		tagValue = new double [tags.size()];
		int j = 0;
		while(tags.values().iterator().hasNext()){
			tagValue[j] = tags.values().iterator().next();
			j++;
		}
		// PSO求组合优化
		// 惯性系数，追逐系数
		final double w = 2;
		final double c1 = 0.6;
		final double c2 = 0.7;

		// 生成粒子群,赋值随机解
		Particle[] p = new Particle[1000];
		for (int i = 0; i < 1000; ++i) {
			p[i] = new Particle();
		}

		// 群体最优位置
		double []GroupBest = {0,0,0,0};
		
		//进行迭代
		int iter = 1000;
		while (iter > 0) {
			for (int i = 0; i < 1000; ++i) {
				p[i].update(GroupBest, w, c1, c2);
				RecommandItem.utility(p[i], GroupBest);
			}
			--iter;
		}
		
		//群体最优位置即为最优解
		return RecommandItem.getInt(GroupBest);
	}

	//辅助函数，向上取整
	public static int[] getInt(double g[]){
		int []result = {0,0,0,0};
		for(int i =0;i<4;i++){
			result[i] = (int)Math.ceil(g[i]);
		}
		return result;
	}
	
	//效用函数，计算效用值，从而确定是否为优
	public static boolean utility(Particle p) {
		
		final int k = 9;
		Item[] items= new Item[4];
		int [] ids = new int [4];
		double utilityNow = 0;
		double utilityPast = 0;
		
		ids = RecommandItem.getInt(p.Position);
		for(int i =0;i<4;++i){
			items[i] = DataBase.getItemByID(ids[i]);
			double temputility = RecommandItem.COSSimilarity(items[i].tagValues, tagValue)*Double.valueOf(items[i].getPrice());
			utilityNow +=  k* Math.exp(temputility);
		}
		
		ids = RecommandItem.getInt(p.localBest);
		for(int i =0;i<4;++i){
			items[i] = DataBase.getItemByID(ids[i]);
			double temputility = RecommandItem.COSSimilarity(items[i].tagValues, tagValue)*Double.valueOf(items[i].getPrice());
			utilityPast +=  k* Math.exp(temputility);
		}
		if(utilityNow>utilityPast) return true;
		else return false;
	}
	
	//重载
	public static boolean utility(Particle p, double[] GroupBest){
		
		final int k = 9;
		Item[] items= new Item[4];
		int [] ids = new int [4];
		double utilityNow = 0;
		double utilityPast = 0;
		
		ids = RecommandItem.getInt(p.Position);
		for(int i =0;i<4;++i){
			items[i] = DataBase.getItemByID(ids[i]);
			double temputility = RecommandItem.COSSimilarity(items[i].tagValues, tagValue)*Double.valueOf(items[i].getPrice());
			utilityNow +=  k* Math.exp(temputility);
		}
		
		ids = RecommandItem.getInt(GroupBest);
		for(int i =0;i<4;++i){
			items[i] = DataBase.getItemByID(ids[i]);
			double temputility = RecommandItem.COSSimilarity(items[i].tagValues, tagValue)*Double.valueOf(items[i].getPrice());
			utilityPast +=  k* Math.exp(temputility);
		}
		
		if(utilityNow>utilityPast) return true;
		else return false;
	}
	
	//余弦相似度算法
	public static double COSSimilarity(double[] a, double[] b) {
		// 计算余弦相似度
		double result = 0;
		double temp1 = 0;
		double tempA = 0;
		double tempB = 0;
		for (int i = 0; i < a.length; ++i) {
			temp1 += a[i] * b[i];
			tempA += a[i];
			tempB += b[i];
		}
		result = temp1 / (Math.sqrt(tempA) * Math.sqrt(tempB));
		return result;
	}
	
	//例子类
	class Particle {
		public double[] Veciloty;
		public double[] Position;
		public double[] localBest = { 0, 0, 0, 0 };
		public double[] target = new double[4];
		private Random rd = new Random();

		public Particle() {
			for (int i = 0; i < 4; ++i) {
				Veciloty[i] = rd.nextDouble() * 100;
				Position[i] = rd.nextDouble() * 100;
				target[i] = rd.nextDouble() * 100;
			}
		}
		
		//更新速度与位置
		public double[] update(double[] GroupBest, double w, double c1,
				double c2) {
			Double temp1 = rd.nextDouble();
			Double temp2 = rd.nextDouble();
			if(RecommandItem.utility(this)){
				localBest = Position;
			}
			for (int i = 0; i < 4; ++i) {
				Veciloty[i] = w * Veciloty[i] + c1 * temp1
						* (localBest[i] - Position[i]) + c2 * temp2
						* (GroupBest[i] - Position[i]);
				Position[i] = Veciloty[i] + Position[i];
			}
			return Position;
		}
	}
}
