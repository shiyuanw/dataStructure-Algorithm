package com.luoExpress.bfprt;

public class BFPRT {
    //O(N)
    public static int[] getMinKNumsByBFPRT(int[] arr,int k) {
        if (k < 1 || k > arr.length) {
            return arr;
        }
        int minKth = getMinKthByBFPRT(arr,k);
        int[] res = new int[k];
        int index = 0;
        for (int i = 0; i != arr.length ; i++) {
            if (arr[i] < minKth) {
                res[index++] = arr[i];
            }
        }
        for (; index !=res.length ; index++) {
            res[index] = minKth;
        }

        return res;

    }

    public static int getMinKthByBFPRT(int[] arr, int k) {
        int[] copyArr = copyArray(arr);
        return bfprt(copyArr,0,copyArr.length-1,k-1);
    }



    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i <res.length ; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static int bfprt(int[] arr, int begin, int end, int i) {
        if (begin == end) {
            return arr[begin];
        }
        int pivot = medianOfMedians(arr,begin,end);
        int[] pivotRange = partition(arr,begin,end,pivot);//返回的是等于区域
        if (i >= pivotRange[0] && i<= pivotRange[1]) {
            return arr[i];
        }else if (i<pivotRange[0]){
            return bfprt(arr,begin,pivotRange[0]-1,i);
        }else {
            return bfprt(arr,pivotRange[1]+1,end,i);
        }
    }



    public static int medianOfMedians(int[] arr, int begin, int end) {
        int num = end-begin+1;
        int offset = num%5==0?0:1;
        int[] mArr = new int[num/5+offset];
        for (int i = 0; i <mArr.length ; i++) {
            int beginI = begin+ i*5;
            int endI  = beginI+4;
            mArr[i] = getMedian(arr,beginI,Math.min(end,endI));
        }
        return  bfprt(mArr,0,mArr.length-1,mArr.length/2);
    }

    private static int[] partition(int[] arr, int begin, int end, int pivotValue) {
        int small = begin-1;
        int cur = begin;
        int big = end+1;
        while (cur!= big){
            if (arr[cur]<pivotValue) {
                swap(arr,++small,cur++);
            }else if (arr[cur] > pivotValue) {
                swap(arr,cur,--big);
            }else {
                cur++;
            }
        }
        int[] range = new int[2];
        range[0] = small+1;
        range[1] = big-1;
        return range;
    }
    
    public static int getMedian(int[] arr,int begin,int end){
        insertionSort(arr,begin,end);
        int sum = end +begin;
        int mid = (sum/2)+(sum%2);
        return arr[mid];
    }

    private static void insertionSort(int[] arr, int begin, int end) {
        for (int i = begin+1; i != end+1 ; i++) {
            for (int j = i; j != begin ; j--) {
                if (arr[j-1] > arr[j]) {
                    swap(arr,j-1,j);
                }else {
                    break;
                }
            }
        }
    }

    public static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;

    }

    public static void printArray(int[] arr){
        for (int i = 0; i !=arr.length ; i++) {
            System.out.println(arr[i]+"");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {6,9,1,3,1,2,2,5,6,1,3,5,9,7,2,5,6,1,9};
        printArray(getMinKNumsByBFPRT(arr,10));
    }


}
