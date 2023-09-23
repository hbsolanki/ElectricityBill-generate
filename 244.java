package Jaynish;
import java.util.*;
import java.io.*;

class Electricity{
    public static HashMap<Integer,User> allUser=new HashMap<>();
    public static HashMap<Integer,Bill> billDetails=new HashMap<>();
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args)throws Exception {
        int choice;
        do{
            System.out.println("Choice Option");
            System.out.println("(1)New User \n(2)Already User \n(3)Quit");
            choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1 : addUser();
                         break;
                case 2 : System.out.print("Enter userid : ");
                         int userid=sc.nextInt();
                         if(allUser.containsKey(userid)){
                            sc.nextLine();
                            System.out.print("Enter Password : ");
                            String pass=sc.nextLine();
                            if(allUser.get(userid).password.equals(pass)){
                                userMain(userid);
                            }else{
                                System.out.println("invalid password try again...");
                            }
                         }else{
                            System.out.println("sorry... This UserID Not Exist");
                         }
                         break;
                case 3 : System.exit(-1);
                         break;
                default : System.out.println("Enter Valid Option");
                         break;
            }
        }while(choice!=3);
        
    }

    public static void userMain(int userId)throws Exception{
        int ch;

        do{
            System.out.println("(1)Genrate Bill (2)PayBill (3)Quit");
            ch=sc.nextInt();
            switch(ch){
                case 1 : System.out.println();
                         generateBill(userId);
                         break;
                case 2 : System.out.println();
                         payBill(userId);
                         break;
                case 3 : return;
                default : System.out.println("Choice valid Option");
                          System.out.println();
            }

        }while(ch!=3);

    }

    public static class User{
        int userid;
        String name;
        String password;
        String address;
        String mobileNumber;

        User(int userid,String name,String password,String address,String mobileNumber){
            this.userid=userid;
            this.name=name;
            this.password=password;
            this.address=address;
            this.mobileNumber=mobileNumber;
        }
    }

    public static class Bill{
        int userid;
        double unit;
        double billPrice;

        Bill(int userid,double unit,double billPrice){
            this.userid=userid;
            this.unit=unit;
            this.billPrice=billPrice;
        }
    }

    public static void addUser(){
        int userid=(int) (Math.random()*5000);
        System.out.print("Enter Name : ");
        String name=sc.nextLine();
        System.out.print("Enter Password : ");
        String password=sc.nextLine();
        String mobileNumber;
        boolean flag=true;
        do{
            System.out.print("Enter Mobile Number : ");
            mobileNumber=sc.nextLine();
            if(mobileNumber.length()==10){
                for(int i=0;i<mobileNumber.length();i++){
                    if(mobileNumber.charAt(i)>='0' && mobileNumber.charAt(i)<='9'){
                        flag=false;
                    }
                }
            }
            if(flag){
                System.out.println("Enter Valid Mobile Number!!");
            }
        }while(flag);

        System.out.print("Enter Address : ");
        String address=sc.nextLine();
        System.out.println("Registration Success");
        System.out.println();
        System.out.println("userid : "+userid +"\nPassword : "+password);
        System.out.println();

        allUser.put(userid,new User(userid, name, password, address, mobileNumber));
        billDetails.put(userid,new Bill(userid,0.0,0.0));      
    }

    public static void generateBill(int userid)throws Exception{
        double unit;
        do{
            System.out.println("Enter UNIT");
            unit=sc.nextDouble();
            if(unit>billDetails.get(userid).unit){
                break;
            }
            System.out.println("This Not Valid Unit");
        }while(true);

        double billPrice=calculations(unit);
        billDetails.put(userid,new Bill(userid, unit, billPrice));
        createFile(userid);
        System.out.println("Your Bill Generated View on "+userid+".txt file");
        System.out.println();
    }

    public static void payBill(int userId){
        Bill b=billDetails.get(userId);
        System.out.println("Your Bill is : "+b.billPrice);
        System.out.println();
        System.out.println("Enter Amount : ");
        int amout=sc.nextInt();
        b.billPrice-=amout;
        System.out.println("Your Current Bill Due : "+ b.billPrice);
    }


    public static double calculations(double unit){
        double billPrice=0.0;
        if(unit==50){
            billPrice=0;
        }
        if(unit>50 && unit<=150){
            unit-=50;
            billPrice=unit*0.8;
        }
        if(unit>150 && unit<=300){
            unit-=150;
            billPrice=unit*0.9+150*0.8;
        }
        if(unit>300){
            unit-=300;
            billPrice=unit*1+200*0.9+50*0.8;
        }

        //if bill more than 400 extra 15% surcharge
        if(billPrice>400){
            billPrice*=0.25;
        }
        return billPrice;
    }

    public static void createFile(int userid)throws IOException{
        User u=allUser.get(userid);
        Bill b=billDetails.get(userid);
        FileWriter fw=new FileWriter(userid+".txt");
        BufferedWriter bw=new BufferedWriter(fw);
        
        bw.write("               *** Electricity Bill ***");
        bw.newLine();
        bw.newLine();
        bw.write("UserID : "+userid);
        bw.newLine();
        bw.write("Name : "+u.name);
        bw.newLine();
        bw.write("Mobile Number : "+u.mobileNumber);
        bw.newLine();
        bw.write("Address : "+u.address);
        bw.newLine();
        bw.newLine();
        bw.write("unit : "+b.unit);
        bw.newLine();
        bw.write("Amount : "+b.billPrice);

        bw.close();
    }
}