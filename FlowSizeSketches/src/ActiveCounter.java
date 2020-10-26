public class ActiveCounter {

    public static void main(String[] args){

//        char[] CnArr = new char[16];
//        char[] CeArr = new char[16];
        String CnStr=null;
        String CeStr=null;

        int N = 100000;//1000000;

        int i=0;
        int Cn = 0;
        int Ce = 0;
        int prob_inc = 1;
        int bitSize = 16;

        while(i<N){
//            getBinary(i,CnArr,CeArr,Cn,Ce,bitSize);
            String str = Integer.toBinaryString(Cn);
            if(str.length()>bitSize){
                Ce++;
                CeStr = Integer.toBinaryString(Ce);
//            System.out.println(CeStr);
//                CeArr = CeStr.toCharArray();
//            CeStr = Integer.toBinaryString(Ce);
                Cn = i;
                Cn = Cn>>>1;
                CnStr = Integer.toBinaryString(Cn);
//            System.out.println(CnStr);
//                CnArr = CnStr.toCharArray();
//            CnStr = Integer.toBinaryString(Cn);
            } else {
                CnStr = Integer.toBinaryString(Cn);
//                CnArr = Integer.toBinaryString(Cn).toCharArray();
                System.out.println(i+" "+CnStr);
                Cn++;
            }
            i++;
        }

        for(int j=0;j< CnStr.length();j++){
            System.out.print(CnStr.charAt(j));
        }
        System.out.print('.');
        for(int j=0;j< CeStr.length();j++){
            System.out.print(CeStr.charAt(j));
        }
        System.out.println();
        System.out.println(Cn+"."+Ce);

    }

    static void getBinary(int i, char[] CnArr, char[] CeArr,int Cn,int Ce, int bitSize){
        String str = Integer.toBinaryString(i);
//        System.out.println(i+" "+str);
        if(str.length()>bitSize){
            Ce++;
            String CeStr = Integer.toBinaryString(Ce);
//            System.out.println(CeStr);
            CeArr = CeStr.toCharArray();
//            CeStr = Integer.toBinaryString(Ce);
            Cn = Cn>>>1;
            String CnStr = Integer.toBinaryString(Cn);
//            System.out.println(CnStr);
            CnArr = CnStr.toCharArray();
//            CnStr = Integer.toBinaryString(Cn);
        } else {

            CnArr = Integer.toBinaryString(Cn).toCharArray();
            System.out.println(i+" "+Integer.toBinaryString(Cn));
            Cn++;
        }

    }
}
