/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

//package com.raven.electre;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author ryota
 */
public class Electre {
    
   public double alternatives[][] = new double[][]{{-1,21400000,22400000,27200000,26200000,27400000,27400000,26200000},
                                                   {-1,185.1,63.3,96.1,77.7,76.2,78.5,71.1},
                                                   {-1,6,6,6,4,4,4,4},
                                                   {1,7,7,7,7,7,7,7},
                                                   {-1,852,-42,184,55,51,64,16},
                                                   {-1,2,3,2,2,2,2,2},
                                                   {1,5,1,5,5,5,5,5},
                                                   {-1,2,5,2,2,2,2,2},
                                                   {1,3,3,2,1,1,1,3},
                                                   {-1,4,6,2,2,2,2,2},
                                                   {-1,0.015410959,0.018181818,0.015410959,0.015410959,0.015410959,0.015410959,0.015410959},
                                                   {1,90,90,90,90,90,90,90},
                                                   {1,5,7,4,4,4,4,4},
                                                   {1,96.5,100,96.5,88.5,87,87,100},
                                                   {-1,15,0,15,2880,180,180,0},
                                                   {1,5,5,5,3,4,4,5},
                                                   {1,7,4,5,5,5,5,5},
                                                   {-1,5,4,5,5,5,5,5},
                                                   {-1,5,1,5,2,4,4,1},
                                                   {-1,3,4,4,5,5,5,5},
                                                   {-1,15,18,18,54,18,18,1},
                                                   {-1,0.00000162,0.000000599,0.000000821,0.000000524,0.000000533,0.000000536,0.000000547},
                                                   {-1,176,93,108,84,76,76,84},
                                                   {-1,50,24,29,22,19,19,23},
                                                   {-1,663,320,389,529,3450,3460,1100},
                                                   {-1,0.00169,0.000221,0.000574,0.000199,0.00019,0.000192,0.000221},
                                                   {-1,17995,10223,11612,8285,8170,8206,8163},
                                                   {-1,0.9,0.4,0.5,0.5,0.4,0.4,0.6},
                                                   {-1,0.0455,0.0216,0.0264,0.0179,0.0181,0.0182,0.0185},
                                                   {-1,0.00000726,0.00000341,0.00000419,0.00000368,0.00000313,0.00000306,0.00000447}
   
   };
    
    
    public final int SIZE                    = alternatives[0].length;
    public final double SEUIL_PREFERENCE[]   = new double[]{30,50,2,0,50,1,3,3,2,2,0.01,40,3,5,60,1,2,2,4,1,24,40,40,40,40,40,40,20,20,10};
    public final double SEUIL_INDIFFIRENCE[] = new double[]{10,20,1,0,20,0,1,0,0,1,0.00167,0,1,1.5,15,0,1,1,1,0,6,40,10,40,40,40,40,20,20,10};
    public final double SEUIL_VETO[]         = new double[]{101,140,7,7,1100,3,5,5,5,3,1,70,7,20,3000,5,4,4,5,7,70,101,101,101,101,101,101,101,101,101};
    public final int NATURE_SEUIL[]          = new int[]{2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2};
    public final static double WEIGHTS[]     = new double[]{2.2,6.7,24.4,17.8,22.2,26.7};
    public final int SIZE_CRITERES           = alternatives.length;

    public int getSize(){
        return SIZE;
    }

    public int getSizeCritere(){
        return SIZE_CRITERES;
    }
    
    
    
    //testing data
    /*public double alternatives[][]= new double[][]{{17800,14900},
                                                   {47.8,70.3},
                                                   {4,4.27},
                                                   {309000,299000},
                                                   {0.0508,0.0625},
                                                   {1.06E+09,1.28E+09},
                                                   {23.4,26.6}
    };
    
    
    public final double SEUIL_PREFERENCE[] = new double[]{5,5,15,10,10,10,5};
    public final double SEUIL_INDIFFIRENCE[] = new double[]{10,10,20,15,20,20,10};
    public final double SEUIL_VETO[] = new double[]{30,30,50,25,50,50,30};
    public final int NATURE_SEUIL[] = new int[]{1,1,1,1,1,1,1};
    public final int SIZE = NATURE_SEUIL.length;*/
    
    //print table
    public void printTable(double alternatives[][]){
        for(double[] rowAlternatives : alternatives ){
            for(double value : rowAlternatives){
                System.out.print(value +" ");
            }
            System.out.println("");
        }
    }
    
    //Transform all table into ones values -> max problem
    public void ConvertAllIntoMax(){
        for(double[] rowAlternatives : alternatives){
            for(int i=1;i<rowAlternatives.length;i++){
                if(rowAlternatives[0] == 1)
                    continue;
                else{
                    rowAlternatives[i] = -rowAlternatives[i];
                }
            }
        }
    }
    
    public double calcIndifference(double a , double b, int pos){
        double result= 0;
        result = NATURE_SEUIL[pos] == 1 ? (SEUIL_INDIFFIRENCE[pos]/100)*max(abs(a),abs(b)) : SEUIL_INDIFFIRENCE[pos]*max(abs(a),abs(b));
        return result;
    }
    
    public double calcPreference(double a , double b , int pos){
        double result= 0;
        result = NATURE_SEUIL[pos] == 1 ? (SEUIL_PREFERENCE[pos]/100)*max(abs(a),abs(b)) : SEUIL_PREFERENCE[pos]*max(abs(a),abs(b));
        return result;
    }
    
    public double calcVeto(double a , double b , int pos){
        double result= 0;
        result = NATURE_SEUIL[pos] == 1 ? (SEUIL_VETO[pos]/100)*max(abs(a),abs(b)) : SEUIL_VETO[pos]*max(abs(a),abs(b));
        return result;
    }
    
    public boolean isIndiffernce(int pos1 , int pos2 , int row){
        return (alternatives[row][pos2]-alternatives[row][pos1]) <= calcIndifference(alternatives[row][pos1],alternatives[row][pos2],row);
    }
    
    public boolean isPreference(int pos1, int pos2 , int row){
        return (alternatives[row][pos1] - alternatives[row][pos2]) > calcPreference(alternatives[row][pos1],alternatives[row][pos2],row);
    }
    
    public boolean isHesitation(int pos1 , int pos2 , int row){
        return ( 
                calcIndifference(alternatives[row][pos1],alternatives[row][pos2],row) < alternatives[row][pos2] - alternatives[row][pos1]
                && alternatives[row][pos2] - alternatives[row][pos1] <= calcPreference(alternatives[row][pos1],alternatives[row][pos2],row)
                
               );
                     
    }
    
    public boolean isVeto(int pos1 , int pos2 , int row){
        return  (alternatives[row][pos1]-alternatives[row][pos2]) > calcVeto(alternatives[row][pos1],alternatives[row][pos2],row);
    }
    
    public double calcCordondonce(int a , int b , int pos){
        
        if(isIndiffernce(a,b,pos) || isPreference(a,b,pos))
            return 1;
        else if(isHesitation(b,a,pos))
            return ((calcPreference(alternatives[pos][a],alternatives[pos][b],pos))-(alternatives[pos][b]-alternatives[pos][a])) / 
                    ((calcPreference(alternatives[pos][a],alternatives[pos][b],pos))-(calcIndifference(alternatives[pos][a],alternatives[pos][b],pos)));
        else
            return 0;
    }
    
    public double calcDiscordance(int a , int b , int pos){
        if(isVeto(b,a,pos))
            return 1;
        else if ((isPreference(b,a,pos)) && !(isVeto(b,a,pos)))
            return ((alternatives[pos][b]-alternatives[pos][a])-(calcPreference(alternatives[pos][a],alternatives[pos][b],pos)))
                    /
                   ((calcVeto(alternatives[pos][a],alternatives[pos][b],pos))-(calcPreference(alternatives[pos][a],alternatives[pos][b],pos)));
        else
            return 0;
    }
    
    //calcul matrice concordance
    public double[][] getConcordanceMatrix(int pos1, int pos2){
        double result[][] = new double[SIZE_CRITERES][4];
        for(int i=0;i<SIZE_CRITERES;i++){
            for(int j=0;j<4;j++){
                if(j == 0)
                    result[i][j] = alternatives[i][pos1] - alternatives[i][pos2];
                else if(j == 1)
                    result[i][j] = calcPreference(alternatives[i][pos1],alternatives[i][pos2],i);
                else if(j==2)
                    result[i][j] = calcIndifference(alternatives[i][pos1],alternatives[i][pos2],i);
                else
                    result[i][j] = calcCordondonce(pos1,pos2,i);
            }
        }
        return result;
    }
    
    //calcul matrice discordance
    public double[][] getDiscordanceMatricx(int pos1 , int pos2){
        double result[][] = new double[SIZE_CRITERES][4];
        for(int i=0;i<SIZE_CRITERES;i++){
            for(int j=0;j<4;j++){
                if(j == 0)
                    result[i][j] = alternatives[i][pos1] - alternatives[i][pos2];
                else if(j == 1)
                    result[i][j] = calcIndifference(alternatives[i][pos1],alternatives[i][pos2],i);
                else if(j==2)
                    result[i][j] = calcVeto(alternatives[i][pos1],alternatives[i][pos2],i);
                else
                    result[i][j] = calcDiscordance(pos1,pos2,i);
            }
        }
        return result;
    }

    public static double sumWeight(){
        double sum =0;
        for(double val : WEIGHTS){
            sum+=val;
        }
        return sum;
    }

    //calcul credibility Matrix
    public ArrayList<double[][]> getCredibilityMatrix(){
        ArrayList<double[][]> allTable = new ArrayList<double[][]>();
        //first one  : c(a,b)
        double m1[][] = new double[SIZE-1][SIZE-1];
        for(int i=0 ; i< SIZE-1 ; i++){
            for(int j=0 ; j< SIZE-1 ; j++){
                if( i == j)
                    continue;
                else{
                    m1[i][j] = 1;
                }
            }
        }



        //second one : 1 - d(a,b)
        double m2[][] = new double[SIZE-1][SIZE-1];



        //third one  : Rs(a,b)
        double m3[][] = new double[SIZE-1][SIZE-1];





        return allTable;
    }

    


    public static void writeIntoCSV(double[][] table, String fileName) throws FileNotFoundException {
        File csvFile = new File(fileName+".csv");
        PrintWriter out = new PrintWriter(csvFile);

        for(double[] row: table){
            int repVal = 0;
            for(double val : row){
                if(repVal == 0)
                    out.printf("%f",val);
                else 
                    out.printf(",%f",val);
                ++repVal;
            }
            out.printf("%n");
        }

        out.close();

    }
    
    
    public static void main(String[] args) throws FileNotFoundException{

        Electre electre = new Electre();
        //convert
        //electre.ConvertAllIntoMax();
        //print
        //electre.printTable();
        double cordance[][];
        /*cordance = electre.getConcordanceMatrix(0, 1);
        electre.printTable(cordance);
        writeIntoCSV(cordance,"cordance");

        System.out.println("************************");
        cordance = electre.getDiscordanceMatricx(0, 1);
        writeIntoCSV(cordance,"discordance");
        electre.printTable(cordance);*/

        //loop over all tries

        double table[][];
        //System.out.println(electre.getSize());
        int SIZE = electre.getSize();
        int SIZE_CRITERES = electre.getSizeCritere();

        //generate all excel files
        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                if( i != j){
                    table = electre.getConcordanceMatrix(i,j);
                    String name ="concordance : " + Integer.toString(i) + " --> " + Integer.toString(j);
                    writeIntoCSV(table,name);
                    table = electre.getDiscordanceMatricx(i,j);
                    name ="discondance : " + Integer.toString(i) + " --> " + Integer.toString(j);
                    writeIntoCSV(table,name);

                }
            }
        }

    }
    
}