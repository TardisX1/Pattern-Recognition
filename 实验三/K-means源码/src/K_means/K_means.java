package K_means;
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;  
  
public class K_means {  
      
    /** 
     * @param args 
     * @throws IOException 
     */  
      
    public static List<ArrayList<ArrayList<Double>>>   
    initHelpCenterList(List<ArrayList<ArrayList<Double>>> helpCenterList,int k){  
        for(int i=0;i<k;i++){  
            helpCenterList.add(new ArrayList<ArrayList<Double>>());  
        }     
        return helpCenterList;  
    }  
      
    /** 
     * @param args 
     * @throws IOException 
     */  
    public static void main(String[] args) throws IOException{  
    	
    	int match=0,total=0;
    	float rate=0;
          
        List<ArrayList<Double>> centers = new ArrayList<ArrayList<Double>>();  
        List<ArrayList<Double>> newCenters = new ArrayList<ArrayList<Double>>();  
        List<ArrayList<ArrayList<Double>>> helpCenterList = new ArrayList<ArrayList<ArrayList<Double>>>();  
          
        //����ԭʼ����  
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("src/K_means/Wine dataset.txt")));  
        String data = null;  
        List<ArrayList<Double>> dataList = new ArrayList<ArrayList<Double>>();  
        while((data=br.readLine())!=null){  
            //System.out.println(data);  
            String []fields = data.split(",");  
            List<Double> tmpList = new ArrayList<Double>();  
            for(int i=0; i<fields.length;i++)  
                tmpList.add(Double.parseDouble(fields[i]));  
            dataList.add((ArrayList<Double>) tmpList);  
            total++;
        }  
        br.close();  
          
        //���ȷ��K����ʼ��������  
        Random rd = new Random();  
        int k=5;  
        int [] initIndex={59,71,48,66,71,92};  
        int [] helpIndex = {0,59,130,53,43,81};  
        int [] givenIndex = {0,1,2};  
        System.out.println("random centers' index");  
        for(int i=0;i<k;i++){  
            int index = rd.nextInt(initIndex[i]) + helpIndex[i];  
            //int index = givenIndex[i];  
            System.out.println("index "+index);  
            centers.add(dataList.get(index));  
            helpCenterList.add(new ArrayList<ArrayList<Double>>());  
        }     
          
        /* 
        //ע�͵����ⲿ��Ŀ���ǣ�ȡ�������ݼ�����ȶ���������صľ���������Ϊ��ʼ�������� 
        centers = new ArrayList<ArrayList<Double>>(); 
        for(int i=0;i<59;i++) 
            helpCenterList.get(0).add(dataList.get(i)); 
        for(int i=59;i<130;i++) 
            helpCenterList.get(1).add(dataList.get(i)); 
        for(int i=130;i<178;i++) 
            helpCenterList.get(2).add(dataList.get(i)); 
        for(int i=0;i<k;i++){ 
             
            ArrayList<Double> tmp = new ArrayList<Double>(); 
             
            for(int j=0;j<dataList.get(0).size();j++){ 
                double sum=0; 
                for(int t=0;t<helpCenterList.get(i).size();t++) 
                    sum+=helpCenterList.get(i).get(t).get(j); 
                tmp.add(sum/helpCenterList.get(i).size()); 
            } 
            centers.add(tmp); 
        } 
        */  
          
        //���k����ʼ����  
        System.out.println("original centers:");  
        for(int i=0;i<k;i++)  
            System.out.println(centers.get(i));  
          
        while(true)  
        {//�������ɴε�����ֱ�����������ȶ�  
              
            for(int i=0;i<dataList.size();i++){//��עÿһ����¼�����ڵ�����  
                double minDistance=99999999;  
                int centerIndex=-1;  
                for(int j=0;j<k;j++){//��0~k֮���ĸ��������  
                    double currentDistance=0;  
                    for(int t=1;t<centers.get(0).size();t++){//��������֮���ŷʽ����  
                        currentDistance +=  ((centers.get(j).get(t)-dataList.get(i).get(t))/(centers.get(j).get(t)+dataList.get(i).get(t))) * ((centers.get(j).get(t)-dataList.get(i).get(t))/(centers.get(j).get(t)+dataList.get(i).get(t)));   
                    }  
                    if(minDistance>currentDistance){  
                        minDistance=currentDistance;  
                        centerIndex=j;  
                    }  
                }  
                helpCenterList.get(centerIndex).add(dataList.get(i));  
            }  
              
        //  System.out.println(helpCenterList);  
              
            //�����µ�k����������  
            for(int i=0;i<k;i++){  
                  
                ArrayList<Double> tmp = new ArrayList<Double>();  
                  
                for(int j=0;j<centers.get(0).size();j++){  
                    double sum=0;  
                    for(int t=0;t<helpCenterList.get(i).size();t++)  
                        sum+=helpCenterList.get(i).get(t).get(j);  
                    tmp.add(sum/helpCenterList.get(i).size());  
                }  
                  
                newCenters.add(tmp);  
                  
            }  
            System.out.println("\nnew clusters' centers:\n");  
            for(int i=0;i<k;i++)  
                System.out.println(newCenters.get(i));  
            //�����¾�����֮��ľ��룬������С����ֵʱ�������㷨����  
            double distance=0;  
              
            for(int i=0;i<k;i++){  
                for(int j=1;j<centers.get(0).size();j++){//��������֮���ŷʽ����  
                    distance += ((centers.get(i).get(j)-newCenters.get(i).get(j))/(centers.get(i).get(j)+newCenters.get(i).get(j))) * ((centers.get(i).get(j)-newCenters.get(i).get(j))/(centers.get(i).get(j)+newCenters.get(i).get(j)));   
                }  
                //System.out.println(i+" "+distance);  
            }  
            System.out.println("\ndistance: "+distance+"\n\n");  
            if(distance==0)//С����ֵʱ������ѭ��  
                break;  
            else//�����µ�����������ɵ����ģ�������һ�ֵ���  
            {  
                centers = new ArrayList<ArrayList<Double>>(newCenters);  
                //System.out.println(newCenters);  
                newCenters = new ArrayList<ArrayList<Double>>();  
                helpCenterList = new ArrayList<ArrayList<ArrayList<Double>>>();  
                helpCenterList=initHelpCenterList(helpCenterList,k);  
            }  
        }  
        //�����������  
        for(int i=0;i<k;i++){  
            System.out.println("\n\nCluster: "+(i+1)+"   size: "+helpCenterList.get(i).size()+" :\n\n");  
            for(int j=0;j<helpCenterList.get(i).size();j++)  
            {  
                System.out.println(helpCenterList.get(i).get(j)); 
                if(helpCenterList.get(i).get(j).get(0)==(i+1))
                	match++;
            }  
        }  
        rate=(float)match/total;
        rate=rate*10000/100;
        System.out.println("�ܲ�������"+total);
        System.out.println("��ȷ����"+match);
        System.out.println("��ȷ�ʣ�"+rate+ "%");
    }  
}  