package utility;

public class Helpers {

    public static String getNumbersFromString(String str)
    {
        str = str.replaceAll("[^0-9]", "");
//        System.out.println("New String will contain only Learn == "+str);
        return str;
    }
}
