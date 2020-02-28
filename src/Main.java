import java.util.*;
import java.io.*;

class people{
    //sex: male to 0, female to 1
    int state, step, index, PartnerIndex;
    int[] list;
    people(int n, int index){
        this.index = index;
        this.state = 0;
        this.step = 0;
        this.list = new int[n];
    }
}

public class Main{

    public static void main(String args[]){
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader cin = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        int n = cin.nextInt();
        int loadNum = 2 * ( n * n + n );
        String[] loadName = new String[loadNum];
        for(int i = 0; i < loadNum; i++){
            loadName[i] = cin.next();
        }
        //将人名与序号对应,序号从1开始
        HashMap<String,Integer> hsTest= new HashMap<String,Integer>();
        HashMap<Integer,String> hsTest2= new HashMap<Integer,String>();
        String[] nameToNum = new String[2 * n];
        int[] numToName = new int[2 * n];
        for(int i = 0; i < 2 * n; i++){
            //nameToNum[i] = loadName[(n + 1) * i];
            hsTest.put(loadName[(n + 1) * i], i+1);
            hsTest2.put(i+1, loadName[(n + 1) * i]);
            //numToName[i] = i + 1;
        }
        //将所读取人名译为序号
        int[] indexList = new int[loadNum];
        for(int i = 0; i < loadNum; i++){
            indexList[i] = hsTest.get(loadName[i]);
            /*if((i < n * n + n && i % (n + 1)!= 0) || (i >= n * n + n && i % (n + 1)== 0)){
                for(int j = n; j < 2 * n; j++) {
                    if(nameToNum[j].equals(loadName[i])){
                        indexList[i] = numToName[j];
                        break;
                    }
                }
            }else{
                for(int j = 0; j < n; j++) {
                    if(nameToNum[j].equals(loadName[i])){
                        indexList[i] = numToName[j];
                        break;
                    }
                }
            }*/
        }
        //初始化数据
        people[] them = new people[2 * n + 1];
        for(int i = 1; i <= 2 * n; i++){
            them[i] = new people(n, i);
            //out.print(them[i].index + ": ");
            for(int j = 0; j < n; j++) {
                them[i].list[j] = indexList[(i - 1) * n + i + j];
                //out.print(them[i].list[j]);
            }
            //out.println();
        }
        //开始GS匹配过程
        int engaged = 0, manIndex = 1;
        while(engaged != n){
            if(them[manIndex].state == 0){
                if(them[them[manIndex].list[them[manIndex].step]].state == 0){
                    them[manIndex].state = 1;
                    them[manIndex].PartnerIndex = them[manIndex].list[them[manIndex].step];
                    them[them[manIndex].list[them[manIndex].step]].state = 1;
                    them[them[manIndex].list[them[manIndex].step]].PartnerIndex = manIndex;
                    engaged++;
                }else{
                    for(int i = 0; i < n; i++){
                        if(them[them[manIndex].list[them[manIndex].step]].list[i] == them[manIndex].index){
                            them[manIndex].state = 1;
                            them[them[them[manIndex].list[them[manIndex].step]].PartnerIndex].state = 0;
                            them[manIndex].PartnerIndex = them[manIndex].list[them[manIndex].step];
                            them[them[manIndex].list[them[manIndex].step]].PartnerIndex = manIndex;
                            break;
                        }else if(them[them[manIndex].list[them[manIndex].step]].list[i] == them[them[manIndex].list[them[manIndex].step]].PartnerIndex){
                            break;
                        }
                    }
                }
            }
            if(them[manIndex].step < n){
                them[manIndex].step++;
            }
            manIndex = (manIndex + 1) % n + 1;
        }
        //输出数据
        /*for(int i = 0; i < n; i++){
            out.print(nameToNum[them[i+1].index - 1]+ " "+ nameToNum[them[i+1].PartnerIndex - 1]);
            if(i + 1 != n){
                out.println();
            }
        }*/
        for(int i = 0; i < n; i++){
            out.print(hsTest2.get(them[i+1].index)+ " "+ hsTest2.get(them[i+1].PartnerIndex));
            if(i + 1 != n){
                out.println();
            }
        }

        out.close();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
