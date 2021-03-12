package com.stackroute.datamunger;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class DataMunger {
	
	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */
		
	public String[] getSplitStrings(String queryString) {
		
		String[] arr= queryString.split(" ");
		for(int i=0; i<arr.length; i++)	
		{
					arr[i]=arr[i].toLowerCase();
		}
	
		return arr;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {
		
		int index=0;
		String[] a= queryString.split(" ");
		for(int i=0; i<a.length; i++)
		{	
			a[i]=a[i].trim();
			if(a[i].equals("from"))
			{
				index=i;
			}
		}

		return a[index+1];
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {
		
		String str=null;
		String[] arr= new String[] {};
		if(queryString.contains("where"))
			{
			arr= queryString.split("where");
			str=arr[0];
			}
		else
		{
			if(queryString.contains("group by"))
			{
			arr= queryString.split("group by");	
			str=arr[0];
			}
			else if(queryString.contains("order by"))
			{
				arr= queryString.split("order by");
				str=arr[0];
			}
			else 
				return str;
		}
		return str.toLowerCase().trim();
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
		
		String[] array= queryString.split(" ");
		String str=array[1];
		String[] arr2= new String[] {};
			if(str.contains(","))
			{
				arr2=str.split(",");
			}
			else
			{
				arr2= str.split("");
			}
			
			for(int i=0; i<arr2.length; i++)	
			{
				arr2[i]=arr2[i].toLowerCase();
			}	
			
		return arr2;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		
		String[] arr= new String[] {};
		String str="";
		
		if(queryString.contains("where"))
		{
			arr= queryString.split("where");
			str= arr[1].trim();
			int index=0;
			if(str.contains(" group by"))
			{
				index=str.indexOf(" group by");
				str=str.substring(0, index);
			}
			else if(str.contains(" order by"))
			{
			index=str.indexOf(" order by");
			str=str.substring(0, index);
			}
			else
				return str.toLowerCase();
		}
		else 
			return null;

		return str.toLowerCase().trim();
	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {
		
		String[] arr1= new String[] {};
		String str="";
		String[] arr2=new String[] {};
		if(queryString.contains("where"))
			{
			arr1=queryString.split("where");
			str=arr1[1].toLowerCase();
			
				if(str.contains(" and") || str.contains(" or") )
				{
					int index=0;
						if(str.contains(" group by"))
							{
							index=str.indexOf(" group by");
							str=str.substring(0, index);
							arr2 = str.split(" and| or ");
							}
						else if(str.contains(" order by"))
							{
							index=str.indexOf(" order by");
							str=str.substring(0, index);
							arr2 = str.split(" and| or ");
							}
						else
							arr2 = str.split(" and| or ");
				}
				else
				{
					if(str.contains(" group by"))
						{
						int index=str.indexOf(" group by");
						str=str.substring(0, index);
						arr2=str.split("\n");
						}
					else if(str.contains(" order by"))
						{
							int index=str.indexOf(" order by");
							str=str.substring(0, index);
							arr2=str.split("\n");
						}
					else
						{
						arr2=str.split("\n");
						}
				}
				
				for(int i=0; i<arr2.length; i++) 
				{
					arr2[i]=arr2[i].toLowerCase().trim();
				}
			}
				
				else
					arr2=null;
		
		
		return arr2;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {
		
		String[] arr1=new String[] {};
		String[] arr2=new String[] {};
		String[] arr3=new String[] {};
		String str="";
		String str1="";
		
		if(queryString.contains("where"))
		{	int index=0;
			arr1=queryString.split("where");
			str=arr1[1].toLowerCase();
			
				if(str.contains("group by"))
				{
					index=str.indexOf("group by");
					str=str.substring(0,index-1);
					arr2=str.split(" ");
				}
				else if(str.contains("order by"))
				{
					index=str.indexOf("order by");
					str=str.substring(0,index-1);
					arr2=str.split(" ");
				}
				else
					arr2=str.split(" ");
			
				
				for(int i=0; i<arr2.length; i++)
				{
					if(arr2[i].equals("and"))
					{
						str1=str1.concat("and ");
					}
					else
						if(arr2[i].equals("or"))
					{
						str1=str1.concat("or ");
					}
					else
						str1=str1.concat("");
				}
				
				
				arr3=str1.split(" ");
				
				}
		
				else
					arr3=null;
		
		return arr3;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString)
	{
		
		String[] arr= new String[] {};
		String str="";
		
		if(queryString.contains("order by"))
			{
			arr=queryString.split("order by");
			str=arr[1].trim();
				if(str.equals(","))
				{
					arr=str.split(",");
				}
				else 
				{
					arr=str.split("\n");
			    }
			}
		else
				arr=null;
			
			
		return arr;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {
		
		String[] arr= new String[] {};
		String[] arr2= new String[] {};
		String str="";
		
		if(queryString.contains("group by"))
			{
			arr=queryString.split("group by");
			str=arr[1].trim();
				if(str.equals(","))
				{
					arr2=str.split(",");
					arr=arr2;
				}
				else 
				{
					arr=str.split("\n");
			    }
			}
		else
				arr=null;
			
		return arr;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {
		
		String[] arr1=new String[] {};
		String[] arr2=new String[] {};
		String[] arr3=new String[] {};
		String str="";
		String str1="";
		
			arr1=queryString.split(" ");
			str=arr1[1].toLowerCase().trim();
			
			if(str.contains(","))
			{
			arr2=str.split(",");
				for(int i=0; i<arr2.length; i++)
				{
					if(arr2[i].contains("count(" ))
					{
						str1=str1.concat(arr2[i]+" ");
					}
					
					else if(arr2[i].contains("max(" ))
					{
						str1=str1.concat(arr2[i]+" ");
					}
					
					else if(arr2[i].contains("min(" ))
					{
						str1=str1.concat(arr2[i]+" ");
					}
					
					else if(arr2[i].contains("avg(" ))
					{
						str1=str1.concat(arr2[i]+" ");
					}
					
					else if(arr2[i].contains("sum(" ))
					{
						str1=str1.concat(arr2[i]+" ");
					}
					
				else
						str1=str1.concat("");
				}
				
			}
			else
			{
				if(str.contains("count("))
				{
					str1=str1.concat(str);
				}
				else if(str.contains("max("))
				{
					str1=str1.concat(str);
				}
				else if(str.contains("min("))
				{
					str1=str1.concat(str);
				}
				else if(str.contains("avg("))
				{
					str1=str1.concat(str);
				}
				else if(str.contains("sum("))
				{
					str1=str1.concat(str);
				}
				else
					str1=str1.concat("");
			}
			
			if(str1.isBlank())
			{
				arr3=null;
			}
			else
				arr3=str1.split(" ");
			
		return arr3;
	}

}