import java.io.*;
import java.util.*; 

public class Assemble
{
    public static String pad(int n, int w)
    {
	 String zeros = "00000000000000000000000000000000000000000000000000000000000000";
	 int n2 = String.valueOf(n).length();
	 //System.out.println("n2 = " + n2);
	 int num = w - n2;
	 //System.out.println("num = " + num);
	 String paded = zeros.substring(0, num) + n;
	 //System.out.println("paded = " + paded);
	 return paded;
    }


    static void readSrc(String fname) throws IOException
    {
        String operatori[] = {"HALT", "ADD", "SUB", "MLT", "DIV", "ILOAD", "LOAD",
	 "STOR", "READ", "WRITE", "BR", "BZ", "BN", "DUMP"};
        int operandi[] = new int[100];
	String buffer = null;	
	//StringTokenizer st1 = new StringTokenizer(fname);
	Assemble as = new Assemble();	

	FileReader fr     = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);

        while ((buffer = br.readLine()) != null)
	{
	int upcode = -1;
	StringTokenizer st1 = new StringTokenizer(buffer);
	//System.out.println("buffer = " + buffer);
            // skip any lines of length zero or starting with '#'
	    if(buffer.length() == 0)
		{
		  //System.out.println("skiped a line of length zero");
		  continue;
		}
	    if(buffer.indexOf("#") == 0)
		{
		  //System.out.println("skiped a line of #");
                  continue;
                }
            
            // tokenize string.  1st token is operator, 2nd is operand
	    String operator = st1.nextToken();
	    String operand = st1.nextToken();
	    int ioperand = Integer.parseInt(operand);

            // If operand is invalid, display error and abort.
	    if(ioperand > 99  || ioperand < 0)
		{
		  System.out.println("Invalid operand!!!");
		  System.exit(0);
		}
            // If operator is invalid, display error and abort.
	    for (int i = 0; i < operatori.length; i++)
	 {
         if (operator.equals(operatori[i]))
         {
           upcode = i;
         }
        }
	if(upcode == -1)
	  {
		System.out.println("Unknown operator: " + operator);
		System.exit(0);
	  }
	
            // convert string operator to numeric form.
	    //System.out.println("operator = " + as.pad(upcode, 2));
	    //System.out.println("operand = " + as.pad(ioperand, 2));
	    

            // output opcode and operand, each padded to two characters.
   System.out.println(as.pad(upcode, 2) + as.pad(ioperand, 2));
                        
        }
    }

    public static void main(String argv[]) throws IOException
    {
	//Assemble as = new Assemble();
	//System.out.println(as.pad(2, 4));
        if (argv.length != 1)
        {
            System.out.println("usage:  java Assemble INPUTFILE");
            System.exit(0);
        }

        readSrc(argv[0]);
    }
}
