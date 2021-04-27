

import org.junit.Test;

import java.math.BigInteger;

public class Huawei {

    /**
     * 给你一个非空模板串S，一个文本串T，问S在T中出现了多少次
     */
    @Test
    public void kmp(){
        String S = "ab";
        String T = "abababab";
        int count = 0;
        int index = 0;
        //思路：循环将T一点点截取S的长度字符与S做比较，如果相等则记录下来
        while (index <= T.length()-S.length()){
            String t = T.substring(index,index+ S.length());
            if(S.equals(t)){
                count++;
            }
            index++;
        }
        System.out.println(count);
    }

    /**
     * 给定两个字符串str1和str2,输出两个字符串的最长公共子串
     * 题目保证str1和str2的最长公共子串存在且唯一。
     */
    @Test
    public void 最长公共子串(){
        String str1 = "2LQ74WK8Ld0x7d8FP8l61pD7Wsz1E9xOMp920hM948eGjL9Kb5KJt80";
        String str2 = "U08U29zzuodz16CBZ8xfpmmn5SKD80smJbK83F2T37JRqYfE76vh6hrE451uFQ100ye9hog1Y52LDk0L52SuD948eGjLz0htzd5YF9J1Y6oI7562z4T2";
        int l1 = str1.length();
        int l2 = str2.length();
        int max = 0;
        int index = 0;
        int[][] dp = new int[l1+1][l2+1];
        for(int i = 0;i<l1;i++){
            for(int j = 0;j<l2;j++){
                if(str1.charAt(i) == str2.charAt(j)){
                    dp[i+1][j+1] = dp[i][j]+1;
                    if(max <= dp[i][j]){
                        max = dp[i][j];
                        index = i+1;
                    }
                }
            }
        }
        String rs=max>0?str1.substring(index-max-1,index):"-1";
        System.out.println(rs);
    }

    @Test
    public void 最长公共子序列(){
        String str1 = "2LQ74WK8Ld0x7d8FP8l61pD7Wsz1E9xOMp920hM948eGjL9Kb5KJt80";
        String str2 = "U08U29zzuodz16CBZ8xfpmmn5SKD80smJbK83F2T37JRqYfE76vh6hrE451uFQ100ye9hog1Y52LDk0L52SuD948eGjLz0htzd5YF9J1Y6oI7562z4T2";

        int l1 = str1.length();
        int l2 = str2.length();
        String max="";
        String[][] dp = new String[l1+1][l2+1];
        for (int i=0;i<=l1;i++){
            for (int j=0;j<=l2;j++){
                if(i ==0 || j==0){
                    dp[i][j] = "";
                }else if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + str1.charAt(i-1);
                    if(dp[i][j].length() >= max.length()){
                        max = dp[i][j];
                    }
                }else{
                    dp[i][j] = dp[i][j-1].length()>dp[i-1][j].length()?dp[i][j-1]:dp[i-1][j];
                }
            }
        }
        System.out.println(max);
    }
}
