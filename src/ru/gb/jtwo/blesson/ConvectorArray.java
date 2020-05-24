package ru.gb.jtwo.blesson;

public abstract class ConvectorArray{

    public static String[][] convectorDoubleArray(String s) throws ArrayExceprion{
        String strArr[] = s.split("\n");
        String[][] strDoubleArr = new String[strArr.length][strArr.length];
        for (int i = 0; i < strArr.length; i++){
            strDoubleArr[i] = strArr[i].split(" ");
        }
        if(strDoubleArr.length != 4 || strDoubleArr[0].length !=  4 || strDoubleArr[1].length !=  4 || strDoubleArr[2].length !=  4 || strDoubleArr[3].length != 4)
        {
            throw new ArrayExceprion("Массив не размера 4х4");
        }
        return strDoubleArr;
    }

    public static int summArray(String[][] s) throws ArrayExceprion {
        int sumArray = 0;
        for (int i = 0; i < s.length; i++){
            for ( int j = 0; j < s[i].length; j++){
                if(s[i][j].matches("\\d+")) {
                    sumArray += Integer.parseInt(s[i][j]);
                } else {
                    throw new ArrayExceprion("В одной их ячеек не число");
                }



            }
        }
        sumArray = sumArray/2;
        return sumArray;
    }


}
