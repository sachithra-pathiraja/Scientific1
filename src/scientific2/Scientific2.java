/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scientific2;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sachithra
 */
public class Scientific2 {

    /**
     * @param args the command line arguments
     */
          static ArrayList<Double> val1;
          static ArrayList<Integer> col_ind1;
          static ArrayList<Integer> row_ptr1;
          static ArrayList<Double> val;
          static ArrayList<Integer> col_ind;
          static ArrayList<Integer> row_ptr;
          int index=0;
          int globalN=4;
          static question2 q= new question2();
          
    
    public static void main(String[] args) {
        // TODO code application logic here
        int n=9;
       Double[][] k;
       
      
       
        
        n=100;  //size of the matrix
        Scientific2 s=new Scientific2();
        
       
        System.out.println( q.eigen()); //finding eigen value using normerlize power iteration
       System.out.println( q.ShiftEigen()); ////finding eigen value using normerlize power iteration with shifting
       q.pageRank(); //pageran algorithm
       
      Double[][] matrix1=s.generate(n); //generate the matrix
      s.CSR(matrix1); //convertion to CSR 
        row_ptr1=row_ptr;
        col_ind1=col_ind;
        val1=val;
      
        Double[][] matrix2=s.generate(n);
        Double[] answer=new Double[n];
        for(int i=0;i<n;i++){
            answer[i]=0.0;
        }
      s.CSR(matrix2);
      Double[][] answer1=new Double[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                answer1[i][j]=0.0;
            }
        }
      Double[] vector=new Double[n];
      Random rand1 = new Random();
      for(int i=0;i<n;i++){
          vector[i]=rand1.nextDouble()+rand1.nextInt(5000);
      }
      Double sum=0.0;
      for(int i=0;i<20;i++){ //time for sparce algorithm in multiplication
      long startTime = System.currentTimeMillis();
      s.Multiply(val, col_ind, row_ptr, vector, answer);
      long endTime   = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      System.out.println("sparse time= "+totalTime); 
      if(i!=0)
      sum=sum+totalTime;
      }
       System.out.println("Average sparse time for multiplication= "+sum/19);
      
       sum=0.0;
       for(int i=0;i<20;i++){ //time for dence algorithm
      long startTime=System.currentTimeMillis();
      s.MultiplyDence(matrix2, vector);
     long endTime   = System.currentTimeMillis();
     long totalTime = endTime - startTime;
     System.out.println("dence time= "+totalTime);
     if(i!=0)
     sum=sum+totalTime;
       }
       System.out.println("Average dence time for multiplication= "+sum/19);
       
       sum=0.0;
       for(int i=0;i<20;i++){ // time for sparce algorithm in addition
      long startTime=System.currentTimeMillis();
      s.add(val, col_ind, row_ptr, val1, col_ind1, row_ptr1,answer1);
     long endTime   = System.currentTimeMillis();
     long totalTime = endTime - startTime;
     System.out.println("dence time= "+totalTime);
     if(i!=0)
     sum=sum+totalTime;
       }
       System.out.println("Average sparce time for addition= "+sum/19);
       
       sum=0.0;
       for(int i=0;i<20;i++){ //time for dence algorithm
      long startTime=System.currentTimeMillis();
      s.AddDence(matrix1, matrix2);
     long endTime   = System.currentTimeMillis();
     long totalTime = endTime - startTime;
     System.out.println("dence time= "+totalTime);
     if(i!=0)
     sum=sum+totalTime;
       }
       System.out.println("Average dence time for addition= "+sum/19);
       
         for(int i=0;i<n;i++){
            s.setIthJthElement(i, i, val, col_ind, row_ptr, 2016.0); //diagonal elements to 2016
        }
       
       
    }
    public Double[][] generate(int n){
        globalN=n;
        Random rand = new Random();
        int  val_size;
        val_size = rand.nextInt((int)(n*n*10/100))+1;
    //int val_size=(int)(Math.random()*(n*n*10/100));
       // System.out.println(val_size);
    Double[] val=new Double[val_size];
    for(int i=0;i<val_size;i++){
         Random rand1 = new Random();
       val[i]=rand1.nextDouble()+rand.nextInt(5000);
     //  System.out.println(val[i]  );
        
    }
    int[] possitionX=new int[val_size];
    int[] possitionY=new int[val_size];
    possitionX[0]=rand.nextInt(n*n);
    for(int i=1;i<val_size;i++){
        int k;
        while(true){
            k=rand.nextInt(n*n);
            for(int j=0;j<i;j++){
                if(k==possitionX[j])
                    k=-5367;
            }
            if(k!=-5367){
                possitionX[i]=k;
                break;
            }
        }
        //System.out.println( possitionX[i]);
    }
     Double[][] matrix=new Double[n][n];
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            matrix[i][j]=0.0;
        }
    }
   
    int counter=0;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            for(int t=0;t<val_size;t++){
               // System.out.println("counter="+counter+" possition="+possitionX[t]);
                if(possitionX[t]==counter){
                    matrix[i][j]=val[t];
                    
                }
               
            }
            //matrix[i][j]=0.0;
            counter++;
           
                
            }
        }
    
    
    
    return matrix;
    
    }
    
    public void CSR(Double[][] matrix){
        int x=matrix[0].length;
        int y=matrix[1].length;
        globalN=matrix[0].length;
        int counter;
        Boolean isNonZero;
        ArrayList<Double> val=new ArrayList<Double>();
          ArrayList<Integer> col_ind=new ArrayList<Integer>();
          ArrayList<Integer> row_ptr=new ArrayList<Integer>();
        for(int i=0;i<x;i++){
            counter=0;
            isNonZero=false;
            for(int j=0;j<y;j++){
                if(matrix[i][j]!=0.0){
                   val.add(matrix[i][j]);
                    col_ind.add(j);
                    isNonZero=true;
                    if(counter==0){
                       row_ptr.add(val.size()-1); 
                    }
                    counter++;
                }
                 
            }
            if(!isNonZero)
                  row_ptr.add(-9); 
        }
        int size=row_ptr.size();
        int element=row_ptr.get(size-1);
        row_ptr.add(element+1);
        this.val=val;
        this.col_ind=col_ind;
        this.row_ptr=row_ptr;
      System.out.println("********************Col_Ind vectoe********************");   
       for(int i:col_ind) {
            System.out.print(i+",");
            // prints [Tommy, tiger]
        }
       System.out.println("********************Val Vector********************");
        for(Double i:val) {
            System.out.print(i+",");
            // prints [Tommy, tiger]
        }
        System.out.println("********************row_ptr Vector********************");
        for(int i:row_ptr) {
            System.out.print(i+",");
            // prints [Tommy, tiger]
        }
    }
    
    public Double getIthJthElement(int i,int j,ArrayList<Double> val,ArrayList<Integer> col_ind,ArrayList<Integer> row_ptr){
        int row=row_ptr.get(i);
        if(row==-9){
            return 0.0;
        }
        else if(col_ind.get(row)>j){
            return 0.0;
        }
            else{
                 
           //     for(int l=col_ind.get(row);l<col_ind.size();l++){
                   /* int l=col_ind.get(row);
                    if(col_ind.get(l)==j){
                             return val.get(row);
                         }*/
                    for(int l=row;l<row_ptr.get(i+1);l++){
                         if(col_ind.get(l)==j){
                             index=l;
                             return val.get(l);
                         }
                    }
                    
                    /* while(col_ind.get(l)>col_ind.get(row)){
                         if(col_ind.get(l)==j){
                             return val.get(l);
                         }
                     */
                     return 0.0;
                }
               /*  return 0.0;
                    }*/
        }
    public void setIthJthElement(int i,int j,ArrayList<Double> val,ArrayList<Integer> col_ind,ArrayList<Integer> row_ptr,Double e){
        Double old=this.getIthJthElement(i, j, val, col_ind, row_ptr); 
        if( old!=0.0 && e!=0.0){
            val.remove(index);
            val.add(index, e);
        }
        else if(old!=0.0 && e==0.0 ){
            int m=row_ptr.size();
            int element=row_ptr.get(i);
           
           if(!row_ptr.contains(index)){
                if(element>index){
                row_ptr.remove(index);
                row_ptr.add(i,--element);
            }
               for(int k=i+1;k<m;k++){
                    int element1=row_ptr.get(k);
                   if(element1!=-9){
                        row_ptr.remove(k);
                       row_ptr.add(k, --element1);
                   }
               }
           }
           else{
               for(int k=j;k<globalN;k++){
                   if(this.getIthJthElement(i, k, val, col_ind, row_ptr)!=0){
                       row_ptr.remove(i);
                       row_ptr.add(i, --index);
                       for(int o=i+1;o<m;o++){
                    int element1=row_ptr.get(k);
                   if(element1!=-9){
                       row_ptr.remove(k);
                       row_ptr.add(k, --element1);
                   }
               }
                       break;
                   }
                   else{
                       row_ptr.remove(i);
                       row_ptr.add(i,-9);
                   }
               }
           }
            val.remove(index);
            col_ind.remove(index);
            
        }
        else if(old==0 && e!=0){
            int before;
            int after=0;
            int loop,loop1;
            if(row_ptr.get(i)==-9){
                loop=i;
                loop1=i;
                while(loop!=0 && row_ptr.get(loop)==-9 ){
                    loop--;
                }
                if(row_ptr.get(loop)!=-9){
                before=row_ptr.get(loop);
                }
                while(loop1!=globalN && row_ptr.get(loop1)==-9 ){
                    loop1++;
                }
                if(row_ptr.get(loop1)!=-9){
                after=row_ptr.get(loop1);
                row_ptr.add(i, after);
                for(int k=loop1;k<globalN;k++){
                   int element=row_ptr.get(k);
                   row_ptr.add(k, ++element);
                }
                }
                for(int k=after;k<val.size();k++){
                    Double element=val.get(k);
                    val.add(k+1, element);
                }
                val.add(after,e);
                for(int k=after;k<col_ind.size();k++){
                    int element=col_ind.get(k);
                    col_ind.add(k+1, element);
                }
                col_ind.add(after,j);
            }
            else{
                 loop=row_ptr.get(i);
                if(col_ind.get(loop)<j){
                   /* for(int k=col_ind.get(loop);k<j;k++){
                        
                    }*/
                    int ss=loop;
                    while(col_ind.get(ss)<j && col_ind.get(loop)<col_ind.get(ss) ){
                        ss++;
                    }
                    for(int k=ss;k<col_ind.size();k++){
                        int element=col_ind.get(k);
                         col_ind.add(k+1, element);
                    }
                    col_ind.add(ss, j);
                     for(int k=ss;k<val.size();k++){
                        Double element=val.get(k);
                         val.add(k+1, element);
                    }
                    val.add(ss, e);
                }
                else{
                    loop=row_ptr.get(i);
                    int ss=loop;
                     for(int k=ss;k<col_ind.size();k++){
                        int element=col_ind.get(k);
                         col_ind.add(k+1, element);
                    }
                     col_ind.add(ss, j);
                      for(int k=ss;k<val.size();k++){
                        Double element=val.get(k);
                         val.add(k+1, element);
                    }
                    val.add(ss, e);
                }
            }
        }
    }
    public void CSC(ArrayList<Double> val,ArrayList<Integer> col_ind,ArrayList<Integer> row_ptr){
        
        
    }
    public void add(ArrayList<Double> val,ArrayList<Integer> col_ind,ArrayList<Integer> row_ptr,ArrayList<Double> val1,ArrayList<Integer> col_ind1,ArrayList<Integer> row_ptr1,Double[][] answer){
        
        int val_size=val.size();
        for(int l=0;l<val_size;l++){
            int i;
            int j=col_ind.get(l);
            int k=0;
        /*    while(l>row_ptr.get(k) && k!=row_ptr.size()-2){
                k++;
            }*/
            for(k=0;k<row_ptr.size()-2;k++){
                if(l<row_ptr.get(k+1)){
                    break;
                }
            }
            i=k;
            answer[i][j]=this.getIthJthElement(i, j, val1, col_ind1, row_ptr1)+val.get(l);
        }
         int val1_size=val1.size();
         for(int l=0;l<val1_size;l++){
            int i;
            int j=col_ind1.get(l);
            int k=0;
          /*  while(l>row_ptr1.get(k) && k!=row_ptr1.size()-2){
                k++;
            }*/
             for(k=0;k<row_ptr1.size()-2;k++){
                if(l<row_ptr1.get(k+1)){
                    break;
                }
            }
            i=k;
            answer[i][j]=this.getIthJthElement(i, j, val, col_ind, row_ptr)+val1.get(l);
        }
         /*for(int i=0;i<globalN;i++){
             for(int j=0;j<globalN;j++){
                 System.out.print(answer[i][j]+" ");
             }
             System.out.println("");
         }
         this.CSR(answer);*/
    }
    
    public void Multiply(ArrayList<Double> val,ArrayList<Integer> col_ind,ArrayList<Integer> row_ptr,Double[] vector1, Double[] vector){
      /*  Double[] vector=new Double[globalN];
        for(int i=0;i<globalN;i++){
            vector[i]=0.0;
        }*/
        int temp=0;
        Double sum=0.0;
        int i;
        for(int m=0;m<val.size();m++){
            
             
            int j=col_ind.get(m);
            int k=0;
          /*  while(l>row_ptr1.get(k) && k!=row_ptr1.size()-2){
                k++;
            }*/
             for(k=0;k<row_ptr1.size()-2;k++){
                if(m<row_ptr1.get(k+1)){
                    break;
                }
            }
            i=k;
            if(i!=temp){
               
                vector[temp]=sum;
                 sum=0.0;
            }
            temp=i;
            sum=sum+val.get(m)*vector1[j];
            if(m==val.size()-1){
                 vector[temp]=sum;
            }
        }
        
       /* for(int m=0;m<vector.length;m++){
            System.out.println(vector[m]);
        }*/
        
    }
    public void MultiplyDence(Double[][] matrix,Double[] vector){
        Double sum=0.0;
        Double[] answer=new Double[globalN];
     for(int i=0;i<globalN;i++){
               sum=0.0;
               for(int j=0;j<globalN;j++){
                   sum=sum+matrix[i][j]*vector[j];
               }
               answer[i]=sum;
           }
}
    public void AddDence(Double[][] matrix1, Double[][] matrix2){
        Double[][] answer=new Double[globalN][globalN];
        for(int i=0;i<globalN;i++){
            for(int j=0;j<globalN;j++){
                answer[i][j]=matrix1[i][j]+matrix2[i][j];
            }
        }
    }
    
    
    
}

class question2{
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


