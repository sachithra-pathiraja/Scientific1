/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scientific2;

import java.util.Random;

/**
 *
 * @author sachithra
 */
class Q2{
    public Double eigen(){
       Double[][] matrix=new Double[3][3];
       Double eigenValue=1000.0;
       Double[] vector=new Double[3];
       Double[] answer=new Double[3];
       vector[0]=0.0;
       vector[1]=0.0;
       vector[2]=1.0;
       matrix[0][0]=2.0;
       matrix[0][1]=3.0;
       matrix[0][2]=2.0;
       matrix[1][0]=10.0;
       matrix[1][1]=3.0;
       matrix[1][2]=4.0;
       matrix[2][0]=3.0;
       matrix[2][1]=6.0;
       matrix[2][2]=1.0;
       Double sum=0.0;
       int counter=0;
       Double error=(11-eigenValue)/11;
       while(Math.abs(error)>0.0000009){
           counter++;
           for(int i=0;i<3;i++){
               sum=0.0;
               for(int j=0;j<3;j++){
                   sum=sum+matrix[i][j]*vector[j];
               }
               answer[i]=sum;
           }
           Double max=0.0;
           for(int i=0;i<3;i++){
               if(answer[i]>max){
                   max=answer[i];
               }
           }
           eigenValue=max;
           for(int i=0;i<3;i++){
               vector[i]=answer[i]/max;
           }
           error=(11-eigenValue)/11;
       }
       System.out.println("Number of iterations to converge without Shifting="+counter);
        System.out.println("The Dominent eigen value="+eigenValue);
       return eigenValue; 
       
    }
    public Double ShiftEigen(){
        Double[][] matrix=new Double[3][3];
       Double eigenValue=1000.0;
       Double[] vector=new Double[3];
       Double[] answer=new Double[3];
       vector[0]=0.0;
       vector[1]=0.0;
       vector[2]=1.0;
       matrix[0][0]=2.0+3;
       matrix[0][1]=3.0;
       matrix[0][2]=2.0;
       matrix[1][0]=10.0;
       matrix[1][1]=3.0+3;
       matrix[1][2]=4.0;
       matrix[2][0]=3.0;
       matrix[2][1]=6.0;
       matrix[2][2]=1.0+3;
       Double sum=0.0;
       Double error=(11-eigenValue)/11;
       int counter=0;
       while(Math.abs(error)>0.0000009){
           counter++;
           for(int i=0;i<3;i++){
               sum=0.0;
               for(int j=0;j<3;j++){
                   sum=sum+matrix[i][j]*vector[j];
               }
               answer[i]=sum;
           }
           Double max=0.0;
           for(int i=0;i<3;i++){
               if(answer[i]>max){
                   max=answer[i];
               }
           }
           eigenValue=max-3;
           for(int i=0;i<3;i++){
               vector[i]=answer[i]/max;
           }
           error=(11-eigenValue)/11;
       }
       System.out.println("Number of iterations to converge with Shifting="+counter);
        System.out.println("The Dominent eigen value="+eigenValue);
       return eigenValue; 
       
    }
    public void pageRank(){
       Double[][] transition=new Double[1000][1000];
       Double[] vector=new Double[1000];
       Double[] answer=new Double[1000];
       Random randomGenerator = new Random();
       int[] prob=new int[1000];
       for(int i=0;i<1000;i++){
           for(int j=0;j<1000;j++){
               transition[i][j]=0.0;
           }
       }
       for(int i=0;i<1000;i++){
           vector[i]=1.0/1000;
       }
       
       for(int i=0;i<1000;i++){
           prob[i]=randomGenerator.nextInt(1000);
           int[] row=new int[prob[i]];
           for(int j=0;j<prob[i];j++){
               while(true){
               if(j==0){
               row[j]=randomGenerator.nextInt(1000);
               transition[row[j]][i]=1.0/prob[i];
               break;
               }
               else{
                   int k=randomGenerator.nextInt(1000);
                   for(int l=0;l<j;l++){
                       if(k==row[l]){
                           k=-5367;
                           break;
                       }
                   }
                   if(k!=-5367){
                       row[j]=k;
                        transition[row[j]][i]=1.0/prob[i];
                       break;
                   }
               }
           }
           }
           
           
       }
    Double eigenValue=1000.0;
      Double sum=0.0;
       Double error=(1-eigenValue);
       int counter=0;
      while(Math.abs(error)>0.09){
           counter++;
           for(int i=0;i<1000;i++){
               sum=0.0;
               for(int j=0;j<1000;j++){
                   sum=sum+transition[j][i]*vector[i];
               }
               answer[i]=sum;
           }
           Double max=0.0;
           for(int i=0;i<1000;i++){
               if(answer[i]>max){
                   max=answer[i];
               }
           }
           eigenValue=max;
           for(int i=0;i<3;i++){
               vector[i]=answer[i]/max;
           }
           error=(1-eigenValue);
       }  
       
      System.out.println("*********Dominent eigen value from pagerank algorithm= "+eigenValue+ "**********");
    }
      
}
