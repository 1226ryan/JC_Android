package blind.project.social.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] A = {60, 80, 40};
        int[] B = {2, 3, 5};
        System.out.println("solution(A) = " + solution(A, B, 5, 2, 200));
    }

    // M : 최고 층, X : 최대 사람 수, Y : 최대 몸무게
    public int solution(int[] A, int[] B, int M, int X, int Y) {
        for(int i=0; i<B.length; i++) {
            if(B[i] > M) return 0;
        }

        int count = 0;
        int weight = 0;
        System.out.println("A.length = " + A.length);
        for(int i=0; i<A.length-1; i++) {
            weight = A[i];
            System.out.println("weight = " + weight);
            for(int j=i+1; j<A.length; j++) {
                weight = weight + A[j];
                System.out.println("A["+j+"] = "+A[j]+", weight = " + weight);
                if(i+j+1 <= X) {
                    if(weight >= Y && j != A.length-1) {
                        System.out.println("weight = " + (weight - A[j])+", j = "+j);
                        count += findValue(B, j);
                        System.out.println("1 count = " + count);
                        i = j-1;
                        break;
                    } else if(weight < Y && j == A.length-1) {
                        count += findValue(B, A.length);
                        System.out.println("2 count = " + count);
                        i = j;
                        break;
                    }
                } else {

                }
            }
        }
        return count;
    }

    private int findValue(int[] nums, int length) {
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (!resultList.contains(nums[i])) {
                resultList.add(nums[i]);
            }
        }

        return resultList.size()+1;
    }

//    private List<String> result = new ArrayList<>();
//    public static int mainCount = 0;
//    private static Map<String, Integer> mapCountOfCityName = new HashMap<>();
//    private static Map<String, Integer> mapCountOfDateName = new HashMap<>();
//
//    public String solution(String S) {
//        String[] strEnter = S.split("\n");
//        String[] photoName = new String[strEnter.length];
//        String[] cityName = new String[strEnter.length];
//        String[] dateAndTime = new String[strEnter.length];
//
//        for (int i = 0; i < strEnter.length; i++) {
//            String[] strComma = strEnter[i].split(",");
//
//            photoName[i] = strComma[0];
//            cityName[i] = strComma[1];
//            dateAndTime[i] = strComma[2];
//        }
//        for (int i = 0; i < photoName.length; i++) {
////            System.out.println("photoName["+i+"] = "+photoName[i]);
//            System.out.println("cityName[" + i + "] = " + cityName[i]);
////            System.out.println("dateAndTime["+i+"] = "+dateAndTime[i]);
//        }
//
//        String prevItem = "";
//        Arrays.sort(cityName);
//        for (String item : cityName) {
//            if (!prevItem.equals(item)) {
//                mainCount = 0;
//                countArray(cityName, 0, item);
//                prevItem = item;
//            }
//        }
//        System.out.println("mapCountOfCityName.toString() = " + mapCountOfCityName.toString());
//
//
////        for (int i = 0; i < cityName.length-1; i++) {
////            for (int j = i+1; j < cityName.length; j++) {
////                if(cityName[i].equals(cityName[j])) {
////                    if(comparisonDate(dateAndTime[i], dateAndTime[j])) {
////                        result.add()
////                    }
////                }
////            }
////        }
//
//        return result.toString();
//    }
//
//    private static void countArray(String[] arr, int currentPos, String item) {
//        if (currentPos == arr.length) {
//            mapCountOfCityName.put(item, mainCount);
//            System.out.println(item + " " + mainCount);
//            return;
//        } else {
//            if (arr[currentPos].equals(item)) {
//                mainCount += 1;
//            }
//            countArray(arr, currentPos + 1, item);
//        }
//    }
//
//    private boolean comparisonDate(String first, String second) {
//        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
//        Date date1 = null;
//        Date date2 = null;
//        try {
//            date1 = dateFormat.parse(first);
//            date2 = dateFormat.parse(second);
//            return date1.after(date2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


//    private boolean a(String date) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date beginDate = formatter.parse(start);
//            Date endDate = formatter.parse(end);
//
//            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
//            long diff = endDate.getTime() - beginDate.getTime();
//            long diffDays = diff / (24 * 60 * 60 * 1000);
//
//            System.out.println("날짜차이=" + diffDays);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//    private boolean b(String[] time) {
//        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
//        try {
//            for(int i=0; i<time.length; i++) {
//                Date d_+i = f.parse(time[i]);
//            }
//            Date d1 = f.parse("01:05:10");
//            Date d2 = f.parse("01:05:07");
//            long diff = d1.getTime() - d2.getTime();
//            long sec = diff / 1000;
//            System.out.println(sec);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }

//    public int solution(String s) {
//        int minValue = 0;
//        int value = Integer.parseInt(s, 2);
//
//        while(true) {
//            int result = a(value);
//            System.out.println("result = "+result);
//            minValue++;
//            System.out.println("minValue = "+minValue);
//            if(result == 0) {
//                break;
//            }
//            value = result;
//            System.out.println("value = "+value);
//        }
//
//        return minValue;
//    }
//
//    private int a(int value) {
//        if(value%2 == 0) {
//            return value/2;
//        } else {
//            return value-1;
//        }
//    }

//    public int solution(int[] A) {
//        int result = 0;
//        int minInteger = 1;
//
//        for (int aA : A) {
//            System.out.println("checkValueOfArray(A, "+minInteger+") = "+checkValueOfArray(A, minInteger));
//            if (checkValueOfArray(A, minInteger)) {
//                minInteger++;
//            } else {
//                break;
//            }
//        }
//        result = minInteger;
//        return result;
//    }

//    private boolean checkValueOfArray(int[] arr, int minValue) {
//        System.out.println("+arr.toString() = "+arr.toString());
//        for(int i : arr) {
//            if(i == minValue) return true;
//        }
//        return false;
//    }
}
