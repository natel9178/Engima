import java.util.*;

/**
 * Created by nathaniel on 1/27/16.
 */
public class YangProj4 {
    public static void main( String args[] )
    {
        int a = minAdjDist(new int[]{2, 10, 3, 4, 20, 5});
        int b = minAdjDist(new int[]{2, 10, 3, 10, 20, 15});
        int c = minAdjDist(new int[]{15, 10, -1, 2, 20, 15});

        int g=0;
    }

    public static String[] gpaBetween(String[] list, int a, int b)
    {
        ArrayList<String> peopleInRange = new ArrayList<>();
        for (int i = 0; i < list.length - 1; i++) {
            if (extractGPA(list[i]) >= a && extractGPA(list[i]) <= b) {
                peopleInRange.add(extractName(list[i]));
            }
        }
        return peopleInRange.toArray(new String[peopleInRange.size()]);
    }

    public static String highestScore(String[] roster)
    {
        int GPA = 0;
        for (int i = 0; i < roster.length - 1; i++) {
            GPA = Math.max(extractGPA(roster[i]), GPA);
        }

        for (int i = 0; i < roster.length - 1; i++) {
            if (extractGPA(roster[i]) == GPA) {
                return extractName(roster[i]);
            }
        }
    }

    public static String findStudent(String[] list, String osis)
    {
        for (int i = 0; i < list.length - 1; i++) {
            if (extractOSIS(list[i]).equals(osis)) {
                return extractName(list[i]) + ":" + extractGPA(list[i]);
            }
        }

        return "Student not found";
    }

    public static int countBefore(String[] words, String str) {
        int count = 0;
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].compareTo(str) < 0) {
                count++;
            }
            int o = 3;//yeet
        }
        return count;
    }

    public static void displayStringArr(String[] arr) {
        System.out.println("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println("}");
    }

    //precondition: nums.length>=2
    public static int minAdjDist(int[] nums) {
        int output = 999999999;
        for (int i = 0; i < nums.length - 2; i++) {
            output = Math.min(Math.abs(nums[i] - nums[i + 1]), output);
        }
        return output;
    }


    public static int[] conIntsChange(int[] nums) {
        int output = nums[0];
        for (int i = 0; i < nums.length - 1; i++) {
            if (Math.abs(nums[i] - nums[i + 1]) == 1) {
                output++;
            } else
                output = -99;
        }
        return nums;
    }

    //@param str a String in the format “Name:OSIS:GPA”
//@return the substring “Name”
    public static String extractName(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        for (int i = 0; i < 3; i++) {
            if (i = 0) {
                return tokenizer.nextToken();
            }
        }
    }

    //@param str a String in the format “Name:OSIS:GPA”
//@return the substring “OSIS”
    public static String extractOSIS(String str){
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        for (int i = 0; i < 3; i++) {
            if (i = 1) {
                return tokenizer.nextToken();
            }
        }
    }

    //@param str a String in the format “Name:OSIS:GPA”
//@return the GPA as an int.
    public static int extractGPA(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, ":");
        for (int i = 0; i < 3; i++) {
            if (i = 2) {
                return Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

}