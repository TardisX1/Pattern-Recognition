package Bayes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Vector;



public class BayesBackup {
    /*
    * ����ȫ�ֱ��� // ǰ�����Լ������ԣ�������value ������
    * */
    int testTotal = 0;// ѵ����������
    int predictTotal = 0;// ��������������
    int predictSucess = 0;// Ԥ��ɹ�������
    //�洢����
    public int [][] buy =new int [4][4];//v-high��high��med��low
    public int [][] maint =new int [4][4];//v-high��high��med��low
    public int [][] door =new int [4][4];//2,3,4,5more
    public int [][] person =new int [3][4];//2,4,more
    public int [][] lug_boot=new int [3][4];//small ,med,big
    public int [][] safe =new int [3][4];//low,med,high
    public int [] ClassValues =new int [4];//unacc��acc��good��V-good
    String[] ClassValueName = { "unacc" , "acc" , "good" , "vgood" };
    //�洢����
    float [] ClassValue_gl = new float [4];//unacc-0��acc-1��good-2��V-good-3
    float [][] buy_Vlaue_gl = new float [4][4]; //ǰ�����Լ������ԣ�������value ������ 
    float [][] maint_Value_gl = new float [4][4];
    float [][] door_Value_gl = new float [4][4];
    float [][] person_Value_gl = new float [3][4];
    float [][] lugboot_Value_gl = new float [3][4];
    float [][] safe_Value_gl = new float [3][4];
    /**
    * ������
    */
    public static void main(String[] args ) throws IOException {
        BayesBackup NBayes =new BayesBackup();
        NBayes.ReadFile("learn.txt" ); //��ȡѵ������
        NBayes.Calculated_probability();//�������
        NBayes.TestData();//���������������
        NBayes.show();//������
    }
    /*����������
    * */
    public class Property{//������6�����ԣ�ÿ�����Զ��м�����𣬸�����6���������ж��������Լ۱�Classvalue ���
        public String buying ;//v-high��high��med��low
        public String maint ;//v-high��high��med��low
        public String doors ; //2,3,4,5more
        public String persons ; //2,4,more
        public String lug_boot; //small ,med,big
        public String safety ; // low,med,high
        public String ClassValues ;//unacc��acc��good��V-good
        public String[] PredictResult = new String[5];// ��¼Ԥ����
        public Property(String b ,String m ,String d ,String p ,String l ,String s ,String c ){
            buying =b ; maint =m ; doors =d ;
            persons =p ; lug_boot=l ; safety =s ;
            ClassValues =c ;
        }
    };

    Vector<Property>Data =new Vector();//�洢����
    Vector<Property>DataTest =new Vector();//�洢��������
    /*
    * �ļ���д��ȡѵ������
    * */
    public void ReadFile(String filename ) throws IOException{
        BufferedReader br =new BufferedReader(new FileReader(filename )); 
        String temp =null ;
        temp =br .readLine();
        String []str = null ;
        Property TempClass = null ;
        while (temp !=null ){
            str =temp .split("," );
            TempClass =new Property(str [0],str [1],str [2],str [3],str [4],str [5],str [6]);
            Statistics(TempClass ); //ͳ�Ƹ���
            testTotal ++;
            temp =br .readLine();
        }
        br .close();
    }
    /*
    * ͳ��ÿһ��ĸ���vhigh,vhigh,2,2,small,low,unacc
    * */
    public void Statistics(Property car ){
        for (int i =0;i <4;i ++){
            if (car . ClassValues .equals(ClassValueName [i])){ 
                ClassValues[i]++;
                 //vhigh,high,med,low 
                 if(car.buying.equals("vhigh"))buy [0][i]++;
                 else if (car.buying.equals("high")) buy [1][i]++; 
                 else if (car . buying .equals("med")) buy [2][i]++; 
                 else buy [3][i ]++; 
                 //vhigh,high,med,low 
                 if (car . maint .equals("vhigh" )) maint [0][i ]++; 
                 else if (car . maint .equals("high" )) maint [1][i ]++; 
                 else if (car . maint .equals("med" ))  maint [2][i ]++; 
                 else maint [3][i ]++;
                 //2,3,4,5more 
                 if (car . doors .equals("2" )) door [0][i ]++; 
                 else if (car . doors .equals("3" )) door [1][i ]++; 
                 else if (car . doors .equals("4" )) door [2][i ]++; 
                 else door [3][i ]++; 
                 //2,4,more 
                 if (car . persons .equals("2" )) person [0][i ]++;
                 else if (car . persons .equals("4" )) person [1][i ]++; 
                 else person [2][i]++; 
                 //small ,med,big 
                 if (car . lug_boot.equals("small" )) lug_boot[0][i ]++; 
                 else if (car . lug_boot.equals("med" ))lug_boot[1][i ]++; 
                 else lug_boot[2][i ]++; 
                 // low,med,high 
                 if (car . safety .equals("low" )) safe [0][i ]++; 
                 else if (car . safety .equals("med" )) safe [1][i ]++; 
                 else safe [2][i ]++; 
	        } 
	    } 
    }
    /* 
     *������� 
     */ 
    public void Calculated_probability()
    {
        for (int i =0;i <ClassValues . length ; i ++) 
            ClassValue_gl[i ]=(float ) ClassValues [i ]/testTotal ;
	
        for (int i =0;i <buy_Vlaue_gl. length ; i ++) 
            for (int j =0;j <buy_Vlaue_gl[0].length ; j ++)
            { 
                buy_Vlaue_gl[i ][j ]=(float ) buy [i ][j ]/ClassValues [j ]; 
                maint_Value_gl[i ][j ]=(float ) maint [i ][j ]/ClassValues [j ]; 
                door_Value_gl[i ][j ]=(float ) door [i ][j ]/ClassValues [j ]; 
		    } 
        for (int i =0;i <person_Value_gl. length ; i ++) 
            for (int j =0;j <person_Value_gl[0].length ; j ++)
		    { 
                person_Value_gl[i ][j ]=(float ) person [i ][j ]/ClassValues [j ];
                lugboot_Value_gl[i ][j ]=(float ) lug_boot[i ][j ]/ClassValues [j ]; 
                safe_Value_gl[i ][j ]=(float ) safe [i ][j ]/ClassValues [j ];
		    }
    }
    /*
     * ��ȡ��������
     */
    public void TestData() throws IOException
    {
        BufferedReader br=new BufferedReader(new FileReader("test.txt" )); 
        String temp ;
        temp =br.readLine();
        String []str = null ;
        Property Car = null ;
        while (temp !=null ){
            str =temp .split("," );
            Car =new Property(str [0],str [1],str [2],str [3],str [4],str [5],str [6]);
            predictTotal ++;
            Data .addElement(Car );
            calculate(Car );
            temp =br .readLine();
	    }
    }
    /**
     * �Է������������ܲ��ԣ��ж���ɹ���Ϊ����
     * @param car
     */
    public void calculate(Property car ){
        //unacc��acc��good��V-good,P(yi)--ClassValueTotal_gl��P(x|yi)=low,vigh,4,2,small,low,unacc��һ��
        float itemGl ; // ÿһ���ĸ���
        int b , m , d , p , l , s ;
        b = m = d = p = l = s = -1;
        float MaxGl = 0;
        if (car . buying .equals("vhigh" )) b = 0;
        else if (car . buying .equals("high" )) b = 1;
        else if (car . buying .equals("med" )) b = 2;
        else b = 3;
        if (car . maint .equals("vhigh" )) m = 0;
        else if (car . maint .equals("high" )) m = 1;
        else if (car . maint .equals("med" )) m = 2;
        else m = 3;
        if (car . doors .equals("2" )) d = 0;
        else if (car . doors .equals("3" )) d = 1;
		else if (car . doors .equals("4" )) d = 2;
    	else d = 3;
		if (car . persons .equals("2" )) p = 0;
		else if (car . persons .equals("4" )) p = 1;
		else p = 2;
		if (car . lug_boot.equals("small" )) l = 0;
		else if (car . lug_boot.equals("med" )) l = 1;
		else l = 2;
		if (car.safety.equals("low")) s = 0;
		else if (car.safety.equals("med")) s= 1;
		else s = 2;
		int t = 0;// ��¼�����ʵ��±�
		int i;
		for (i = 0; i < ClassValue_gl.length; i++) 
	    {
		// ������unacc,acc,good,vgood�µĸ���
			itemGl = 0;
			BigDecimal[] bigDecimal = {
				new BigDecimal(Float.toString(ClassValue_gl[i])),
				new BigDecimal(Float.toString(buy_Vlaue_gl[b][i])),
				new BigDecimal(Float.toString(maint_Value_gl[m][i])),
				new BigDecimal(Float.toString(door_Value_gl[d][i])),
				new BigDecimal(Float.toString(person_Value_gl[p][i])),
				new BigDecimal(Float.toString(lugboot_Value_gl[l][i])),
				new BigDecimal(Float.toString(safe_Value_gl[s][i])),
			};
			for (int j = 1; j < bigDecimal.length; j++) //��:a.add(b); ��:a.divide(b,2);//2Ϊ����ȡֵ
				bigDecimal[0] = bigDecimal[0].multiply(bigDecimal[j]);//multiply��
			itemGl = bigDecimal[0].floatValue();
			car.PredictResult[i] = itemGl + "\t";
			if (MaxGl < itemGl) {
				MaxGl = itemGl;
				t = i;
		    }
	    }
		// �жϽ���Ƿ���ȷ
		if (car.ClassValues.equals(ClassValueName[t])) {// Ԥ�����Ϳ�ʼ�����Ľ�����
			car.PredictResult[i] = "true";
			predictSucess++;
		} 
		else
			car.PredictResult[i] = "false";
	}
	public void show()
	{
		for (int i =0; i < predictTotal; i++) 
		{
			Property c = Data.get(i);
			for (int j = 0; j < c.PredictResult.length; j++)
				System.out.print(c.PredictResult[j] + "\t");
			System.out.println();
		}
		// ��������׼ȷ��
		float t = (float) predictSucess / predictTotal;
		t=t*10000/100;
		System.out.println("\n��������׼ȷ��Ϊ:" + t+ "%");
	}

}