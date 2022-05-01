package com.ATMprj;
import java.sql.ResultSet;
import java.util.Scanner;

import ATMmain.ATMemp;
import ATMmain.AtmUser;


public class App 
{
    public static void main( String[] args )
    {
    	boolean status=true;
    	try
    	{
    		do
    		{
        Scanner scan=new Scanner(System.in);
        System.out.println("*******************************");
        System.out.println("    Welcome to Myntra ATM  ");
        System.out.println("*******************************");
        System.out.println("1 -> Help OR Update Account");
        System.out.println("2 -> SAVING ACCOUNT");
        System.out.println("Enter your account type:");
        int userType=scan.nextInt();
        
        if(userType==1)
        {
        	
        	System.out.println("Enter your Account ID:");
        	int eid=scan.nextInt();
        	System.out.println("Enter your 4 digit PIN :");
        	String password=scan.next();
        	
        	ATMemp ob=new ATMemp();
        	//System.out.println(ob.login(eid, password));
        	if(ob.login(eid, password))
        	{
                System.out.println("***********************");
        		System.out.println("Welcome to your ACC ");
                System.out.println("************************");
        		System.out.println("1.Open Account\r\n"
        				+ "2.Close Account\r\n"
        				+ "3.Check User Information\r\n"
        				+ "4.Password Change\r\n"
        				+ "5.Logout");
        	int op=scan.nextInt();
        	if(op==1)
        	{
        		System.out.println("Enter account Id for the New Acc:");
        		long acid=scan.nextLong();
        		System.out.println("Enter account Holder name:");
        		String accname=scan.next();
        		System.out.println("Enter account Holder address:");
        		String accaddress=scan.next();
        		System.out.println("Enter phone number:");
        		long phone=scan.nextLong();
        		System.out.println("Enter valid email id:");
        		String email=scan.next();
        		System.out.println("Enter initial balance :");
        		double balance=scan.nextDouble();
        		System.out.println("Set Password for the new user :");
        		String pass=scan.next();
        		if(ob.openAccount(acid,accname,accaddress,phone,email,balance,pass))
        		{
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        			System.out.println("   Account created successfully   ");
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        		}
        		else
        		{   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        			System.out.println(" Sorry there is Problem in account creation");
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        		}
        	}
        	else if(op==2)
        	{
        		System.out.println("Enter the Account Id for account closing:");
        		long accId=scan.nextLong();
        		if(ob.accountClose(accId))
        		{
        			System.out.println("Account closed successfully!!");
        		}
        		else
        		{
        			System.out.println("Facing a Problem in Closing Acc");
        		}
        	}
        	
        	else if(op==3)
        	{
        		System.out.println("Enter User account Id to check the Information:");
        		long accId=scan.nextLong();
        		ResultSet result=ob.accountInfoCheck(accId);
        		while(result.next())
        		{
        			System.out.println("============================");
        			System.out.println("--- Account Details ----");
                    System.out.println("============================");

                    System.out.println("Account Holder Name :"+result.getString(2));
        			System.out.println("Account Holder Address :"+result.getString(3));
        			System.out.println("Account Holder Phone number :"+result.getLong(4));
        			System.out.println("Account Holder Email :"+result.getString(5));
        			System.out.println("Available balance:"+result.getDouble(6));
        			
        		}
        	}
        	
        	else if(op==4)
        	{
        		System.out.println("=============================");
    			System.out.println("--- PIN Change ----");
                System.out.println("==============================");

        		System.out.println("Enter your Acc ID:");
        		long empId=scan.nextLong();
        		System.out.println("Enter your New 4 Digit PIN:");
        		String newPassword=scan.next();
        		if(ob.changePassword(empId, newPassword))
        		{
        			System.out.println("PIN Changed Successfully");
        		}
        		else
        		{
        			System.out.println("Problem in PIN Changed!!");
        		}
        	}
        	
        	else if(op==5)
        	{
        		if(ob.logout())
        		{   
        			status=false;
        			System.out.println("==================================");
                 
        			System.out.println("You are successfully logged out!!");
        			System.out.println("Thank You.");
        			System.out.println("===================================");

        		}
        	}
        		
        		
        	}
        	else
        	{
        		System.out.println("Login unsuccessfull!!");
        	}
        	
        	
        }
        else if(userType==2)
        {
        	System.out.println("Enter your :");
        	long uid=scan.nextLong();
        	System.out.println("Enter Password:");
        	String password=scan.next();
        	
        	AtmUser ob=new AtmUser();
        	
        	if(ob.login(uid, password))
        	{
        		System.out.println("=================================");
        		System.out.println("Welcome");
        		System.out.println("=================================");

        		System.out.println("1.Withdraw\r\n"
        				+ "2.Deposit\r\n"
        				+ "3.Check Account Balance\r\n"
                        + "4.Change Password\r\n"
        				+ "5.Logout");
        		int operation=scan.nextInt();
        		
        		
        		if(operation==1)
        		{
        					
        			System.out.println("Enter the withdrawal amounnt:");
        			double withdrawAmount=scan.nextDouble();      			
        			ob.withdraw(uid, withdrawAmount);
       			
        		}
        		else if(operation==2)
        		{
        			System.out.println("Enter deposit amount:");
        			double depositAmount=scan.nextDouble();
        			ob.deposit(uid, depositAmount);
        		}
        		else if(operation==3)
        		{
        			
        			ob.balanceCheck(uid);
        		}
        		
        		
        		else if(operation==5)
        		{
        			
        			System.out.println("Enter your New PIN:");
            		String newPassword=scan.next();
            		ob.changePassword(uid, newPassword);
            		System.out.println("===================================");
            		System.out.println("PIN Changed Successfully");
            		System.out.println("===================================");

        		}
        		
        		else if(operation==5)
        		{
        			
        			
            		System.out.println("=================================");
            		ResultSet r=ob.transaction(uid);
            		System.out.println("Transaction Id"+ "\t\t"+"Amount"+"\t"+"Date"+"\t"+"Receiver Id"+"\t"+"Sender Id");
            		System.out.println("==================================");

            		while(r.next())
            		{
            			System.out.println(r.getLong(1)+ "\t"+r.getDouble(2)+"\t"+r.getDate(3)+"\t"+r.getLong(4)+"\t"+r.getLong(5));
            		}
            		System.out.println("===************===");

        		}

        		
        		else if(operation==6)
        		{
        			ob.logout();
        			status=false;
            		System.out.println("===================");
        			System.out.println("Logged Out!!");
            		System.out.println("===================");

        		}
        	}
        	else
        	{
        		System.out.println("=========================");
        		System.out.println("Wrong Id or PIN");
        		System.out.println("==========================");

        	}
        }
        else
        {
        	System.out.println("Please provide a valid input..!!");
        }
      }
    		while(status);		
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
        
        
        
    }
}