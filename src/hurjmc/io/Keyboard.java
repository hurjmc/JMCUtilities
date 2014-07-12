package hurjmc.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */


/**
 * @author beavenj
 */
public class Keyboard 
{
	/**
	 * This method waits for the user to type a string into the console window. Once the user
	 * presses return, the string value is returned to the caller. This version of the
	 * method does not display a prompt to the user.
	 * 
	 * @return String The string value entered by the user on the keyboard
	 */
	public static String getString()
	{
		return getString(null);
	}
	
	/**
	 * This method waits for the user to type a string into the console window. Once the user
	 * presses return, the string value is returned to the caller. This version of the
	 * method displays a prompt to the user that will appear on the console.
	 * 
	 * @return String The string value entered by the user on the keyboard
	 * @param prompt The prompt string that will be displayed to the user
	 * @return
	 */
	public static String getString(String prompt)
	{
		String result;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try
		{
			if (prompt != null)
			{
				System.out.print(prompt + ": ");	
			}
			
			result = in.readLine();
		}
		catch (Exception exc)
		{
			result = "";
		}
		
		return result;	
	}
	
	/**
	 * This method waits for the user to type a number into the console window. Once the user
	 * presses return, the int value is returned to the caller. This version of the
	 * method does not display a prompt to the user. If the user enters non-numeric characters
	 * the value 0 is returned.
	 * 
	 * @return int The int value entered by the user on the keyboard
	 */
	public static int getInt()
	{
		return getInt(null);
	}
	
	/**
	 * This method waits for the user to type a number into the console window. Once the user
	 * presses return, the int value is returned to the caller. This version of the
	 * method displays a prompt to the user on the console. If the user enters non-numeric
	 * characters the value 0 is returned.
	 * 
	 * @param prompt The prompt string that will be displayed to the user
	 * @return int The int value entered by the user on the keyboard
	 */
	public static int getInt(String prompt)
	{
		int result;
		
		try
		{
			String stringNumber = getString(prompt);			
			result = Integer.parseInt(stringNumber);
		}
		catch (Exception exc)
		{
			result = 0;
		}

		return result;
	}
	
	/**
	 * This method waits for the user to type a boolean value into the console window. Once the user
	 * presses return, the boolean value is returned to the caller. This version of the
	 * method does not display a prompt to the user. The values 'true' 't' 'yes' 'y' and '1' are
	 * .considered to be true. All other values are considered to be false
	 * 
	 * @return int The boolean value entered by the user on the keyboard
	 */
	public static boolean getBoolean()
	{
		return getBoolean(null);
	}
	
	/**
	 * This method waits for the user to type a boolean value into the console window. Once the user
	 * presses return, the boolean value is returned to the caller. This version of the
	 * method displays a prompt to the user on the console. The values 'true' 't' 'yes'
	 * 'y' and '1' are considered to be true. All other values are considered to be false
	 * 
	 * @param prompt The prompt string that will be displayed to the user
	 * @return int The boolean value entered by the user on the keyboard
	 */
	public static boolean getBoolean(String prompt)
	{
		boolean result = false;
		
		String stringBoolean = getString(prompt);
			
		if (stringBoolean.equalsIgnoreCase("true") ||
			stringBoolean.equalsIgnoreCase("t") ||
			stringBoolean.equalsIgnoreCase("yes") ||
			stringBoolean.equalsIgnoreCase("y") ||
			stringBoolean.equalsIgnoreCase("1"))
		{
			result = true;
		}	
		
		return result;
	}
}
