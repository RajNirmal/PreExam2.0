package com.spintum.preexam;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class Item {

    private String price;
    private String sub;
    private String u1;
    private String u2;
    private String u3;
    private String u4;
    private String u5;
    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;
    private Integer pt;
    private String fromAddress;
    private String toAddress;
    private int requestsCount;
    private String date;
    private String time;
    private View.OnClickListener requestBtnClickListener;

    public Item() {
    }

    public Item(String price, String fromAddress,String sub,String u1,String u2,String u3,String u4,String u5,String c1,String c2,String c3,String c4,String c5,Integer pt) {
        this.price = price;
        this.fromAddress = fromAddress;
        this.sub = sub;
       this.u1 = u1;
        this.u2 = u2;
        this.u3 = u3;
        this.u4 = u4;
        this.u5 = u5;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.pt = pt;
      //  this.toAddress = toAddress;
        //this.requestsCount = requestsCount;
       // this.date = date;
        //this.time = time;

    }

    public String getPrice() {
        return price;
    }
    public String getU1() {
        return u1;
    }
    public String getU2() {
        return u2;
    }
    public String getU3() {
        return u3;
    }
    public String getU4() {
        return u4;
    }
    public String getU5() {
        return u5;
    }
    public String getC1() {
        return c1;
    }
    public String getC2() {
        return c2;
    }
    public String getC3() {
        return c3;
    }
    public String getC4() {
        return c4;
    }
    public String getC5() {
        return c5;
    }
    public Integer getPt() {
        return pt;
    }
  /*  public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
    }*/
  public String getSub() {
      return sub;
  }

    public String getFromAddress() {
        return fromAddress;
    }


/*
    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }
*/

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("1", "Mathematical","Mathematical","unit:m1","unit:2","unit:3","unit:4","unit:5",
                "Unit1:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit2:Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",
                "Unit3:online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit4:And Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit5:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).", R.drawable.s1));
        items.add(new Item("2", "Physics","Physics","unit:p1","unit:2","unit:3","unit:4","unit:5",
                "Unit1:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit2:Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",
                "Unit3:online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit4:And Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit5:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).", R.drawable.s2));
        items.add(new Item("3", "Chemistry","Chemistry","unit:c1","unit:2","unit:3","unit:4","unit:5",
                "Unit1:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit2:Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",
                "Unit3:online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit4:And Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit5:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).", R.drawable.s3));
        items.add(new Item("4", "Biology","Biology","unit:b1","unit:2","unit:3","unit:4","unit:5",
                "Unit1:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit2:Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",
                "Unit3:online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit4:And Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit5:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",R.drawable.s4));
        items.add(new Item("5", "Computer Science","Computer Science","unit:cs1","unit:2","unit:3","unit:4","unit:5",
                "Unit1:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit2:Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",
                "Unit3:online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit4:And Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG)",
                "Unit5:Relations and Functions-Relations and Functions online test for class 12 Mathematics. These online MCQ tests includes all main concepts of the chapter in CBSE class 12 Mathematics. It is also useful for CBSE JEE (mains) and CBSE NEET (UG).",R.drawable.s5));;
        return items;

    }

}
