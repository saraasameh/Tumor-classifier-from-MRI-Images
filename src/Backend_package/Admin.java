/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author B-pro
 */
public class Admin extends User {
    private String pass;
    private Features image;
    private ArrayList<String[]> TrainLines = new ArrayList<>();
    private ArrayList<String[]> validatelines = new ArrayList<>();

    //attributes
    //mthods
    //default constructor

    public Admin() {
        super(null, null, null);
    }

    public Admin(String user_name, String pass, Features image, String Name, String Address, String Age) {
        super(Name, Address, Age);
        this.pass = pass;
        this.image = image;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Features getImage() {
        return image;
    }

    public void setImage(Features image) {
        this.image = image;
    }
    
    public void extract(String imagePath)
    {
        File input = new File(imagePath);
        File output=new File("project Images\\grayScale.jpg");
        File features=new File("Project Files\\Features.txt");
        image=new Features(input, output, features);
        image.getFeatures();
        image.writeTofile(0);
    }
    
    public String Apply_classifier()
    {
 
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("Project Files\\Accuracy.txt"));
            String line = bufReader.readLine();
            String []line_arr;
            
                for (int i=0; i<7; i++) //collect 70% of images to use it in training
                {
                    line_arr=line.split("@");
                    TrainLines.add(line_arr);
                    line = bufReader.readLine();
                }
                for (int i=7; i<11; i++) //collect 30% of images to use it in validation
                {
                    line_arr=line.split("@");
                    validatelines.add(line_arr);
                    line = bufReader.readLine();   
                }
            
            bufReader.close();
        } catch (IOException ex) {
            System.out.println("error with file");
        }
      //  System.out.println(TrainLines.size());
      // System.out.println(validatelines.size());
        
        
        int tumor_count=0;
        int normal_count=0;
        int n=TrainLines.size();
        
        for(int i=0;i<TrainLines.size();i++)
        {
            if(TrainLines.get(i)[5].equals("1")) // tumor
            {
                tumor_count++;
            }
            else if(TrainLines.get(i)[5].equals("0")) // normal
            {
                normal_count++;
            }
        }
        double prop_yes= (double)tumor_count/n;
        double prop_no=(double) normal_count/n;
        double meanOfFeatures;
        double varOfFeatures;
        double features_yes=1;
        double features_no=1;
        

        double[] prop_normal  = new double[5];
        double[] prop_tumor  = new double[5];
        
        int predectedCorrectly=0;
        
        for(int z=0; z< validatelines.size(); z++)
        {
            for(int j=0;j<5;j++)
            {
                double sum=0;
                //-----yes-------
                //mean
                for(int i=0;i<TrainLines.size();i++)
                {
                    if(TrainLines.get(i)[5].equals("1"))
                        sum+=Double.parseDouble(TrainLines.get(i)[j]);

                }
                meanOfFeatures=(double)sum/tumor_count;
                sum=0;
                //variance
                for(int i=0;i<TrainLines.size();i++)
                {
                    if(TrainLines.get(i)[5].equals("1"))
                        sum+=Math.pow((Double.parseDouble(TrainLines.get(i)[j])-meanOfFeatures), 2);

                }
                varOfFeatures=(double)sum/tumor_count;
                sum=0;
                double prop_feature_tumor =(1/(Math.sqrt(2*3.14)*Math.sqrt(varOfFeatures)))*Math.exp(-1*(Math.pow(Double.valueOf(validatelines.get(z)[j])-meanOfFeatures, 2))/(2*varOfFeatures));
                prop_tumor[j]=prop_feature_tumor;

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //////////////no
                //mean
                for(int i=0;i<TrainLines.size();i++)
                {
                    if(TrainLines.get(i)[5].equals("0"))
                        sum+=Double.parseDouble(TrainLines.get(i)[j]);

                }
                meanOfFeatures=(double)sum/normal_count;
                sum=0.0;
                //variance
                for(int i=0;i<TrainLines.size();i++)
                {
                    if(TrainLines.get(i)[5].equals("0"))
                        sum+=Math.pow((Double.parseDouble(TrainLines.get(i)[j])-meanOfFeatures), 2);

                }
                varOfFeatures=(double)sum/normal_count;
                sum=0.0;
                double prop_feature_normal =(1/(Math.sqrt(2*3.14)*Math.sqrt(varOfFeatures)))*Math.exp(-1*(Math.pow(Double.valueOf(validatelines.get(z)[j])-meanOfFeatures, 2))/(2*varOfFeatures));

                prop_normal[j] = prop_feature_normal;
            }
            
            
            for(int i=0;i<5;i++)
            {
                features_yes *=prop_tumor[i]; 
                features_no *=prop_normal[i];
            }

            //double x=(features_yes*prop_yes)+(features_no*prop_no);
            if((features_yes*prop_yes)>(features_no*prop_no))
            {
//                System.out.println("yes");
                if (validatelines.get(z)[5].equals("1"))
                    predectedCorrectly++;
            }
            else if ((features_yes*prop_yes) <(features_no*prop_no))
            {
//                System.out.println("no");
                if (validatelines.get(z)[5].equals("0"))
                    predectedCorrectly++;   
            }
        }
        return ("accuracy is "+String.valueOf((double) predectedCorrectly/validatelines.size()*100) + "%");
    }
    public String test(Features ft)
    {


        ArrayList<String[]> listOfLines = new ArrayList<>();
        
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("Project Files\\Features.txt"));
            String line = bufReader.readLine();
            String []line_arr;
            
                for (int i=0; i<7; i++)
                {
                    line_arr=line.split("@");
                    listOfLines.add(line_arr);
                    line = bufReader.readLine();
                }
                
            
            bufReader.close();
        } catch (IOException ex) {
            System.out.println("error with file");
        }

        int tumor_count=0;
        int normal_count=0;
        int n=listOfLines.size();
        
        for(int i=0;i<listOfLines.size();i++)
        {
            if(listOfLines.get(i)[5].equals("1")) // tumor
            {
                tumor_count++;
            }
            else if(listOfLines.get(i)[5].equals("0")) // normal
            {
                normal_count++;
            }
        }
        double prop_yes= (double)tumor_count/n;
        double prop_no=(double) normal_count/n;
        double meanOfFeatures;
        double varOfFeatures;
        double features_yes=1;
        double features_no=1;
        
        double [] features={ft.getMean(), ft.getVariance(), ft.getSmoothness(), ft.getSkewness(),ft.getKurtosis()};

        double[] prop_normal  = new double[5];
        double[] prop_tumor  = new double[5];
        
        
        for(int j=0;j<5;j++)
        {
            double sum=0;
            for(int i=0;i<listOfLines.size();i++)
            {
                if(listOfLines.get(i)[5].equals("1"))
                    sum+=Double.parseDouble(listOfLines.get(i)[j]);
            
            }
            meanOfFeatures=(double)sum/tumor_count;
            sum=0;
            //variance
            for(int i=0;i<listOfLines.size();i++)
            {
                if(listOfLines.get(i)[5].equals("1"))
                    sum+=Math.pow((Double.parseDouble(listOfLines.get(i)[j])-meanOfFeatures), 2);

            }
            varOfFeatures=(double)sum/tumor_count;
            sum=0;
            double prop_feature_tumor =(1/(Math.sqrt(2*3.14)*Math.sqrt(varOfFeatures)))*Math.exp(-1*(Math.pow(features[j]-meanOfFeatures, 2))/(2*varOfFeatures));
            prop_tumor[j]=prop_feature_tumor;
            
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            //////////////no
            //mean
            for(int i=0;i<listOfLines.size();i++)
            {
                if(listOfLines.get(i)[5].equals("0"))
                    sum+=Double.parseDouble(listOfLines.get(i)[j]);

            }
            meanOfFeatures=(double)sum/normal_count;
            sum=0.0;
            //variance
            for(int i=0;i<listOfLines.size();i++)
            {
                if(listOfLines.get(i)[5].equals("0"))
                    sum+=Math.pow((Double.parseDouble(listOfLines.get(i)[j])-meanOfFeatures), 2);
            
            }
            varOfFeatures=(double)sum/normal_count;
            sum=0.0;
            double prop_feature_normal =(1/(Math.sqrt(2*3.14)*Math.sqrt(varOfFeatures)))*Math.exp(-1*(Math.pow(features[j]-meanOfFeatures, 2))/(2*varOfFeatures));
            
            prop_normal[j] = prop_feature_normal;
        }
        

        for(int i=0;i<5;i++)
        {
            features_yes *=prop_tumor[i]; 
            features_no *=prop_normal[i];
        }
        String result=null;
        //double x=(features_yes*prop_yes)+(features_no*prop_no);
        if((features_yes*prop_yes)>(features_no*prop_no))
        {
            result="1";
            return "Patient has a tumor...";
        }
        else if ((features_yes*prop_yes) <(features_no*prop_no))
        {
            result="0";
            return "Patient is normal...";
        }        
        return "There is an error occure";

    }

    @Override
    public void DisplayInfo() {
        System.out.println("Username: "+Name);
        System.out.println("Password: "+ pass);
        System.out.println("Age: "+ Age);
        System.out.println("Address: "+Address);
    }
}
