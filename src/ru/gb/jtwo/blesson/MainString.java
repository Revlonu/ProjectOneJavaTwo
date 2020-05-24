package ru.gb.jtwo.blesson;

import java.util.Arrays;

public class MainString {


    public static void main(String[] args) {
        System.out.println(Data.getOneString());
        try{
            String [][] strDoubleArr = ConvectorArray.convectorDoubleArray(Data.getOneString());
            System.out.println(Arrays.deepToString(strDoubleArr));
            int sumArray = ConvectorArray.summArray(strDoubleArr);
            System.out.println(sumArray);
        }
        catch (ArrayExceprion ex){
            System.out.println(ex.getMessage());
        }





    }

}
