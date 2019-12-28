import java.io.*;
import java.util.*;

public class VPCrte
{
    static final int MEMSIZ = 100;

    static final int HALT  = 0;
    static final int ADD   = 1;
    static final int SUB   = 2;
    static final int MLT   = 3;
    static final int DIV   = 4;
    static final int ILOAD = 5;
    static final int LOAD  = 6;
    static final int STOR  = 7;
    static final int READ  = 8;
    static final int WRITE = 9;
    static final int BR    = 10;
    static final int BZ    = 11;
    static final int BN    = 12;
    static final int DUMP  = 13;
 
    static int MEMORY[] = new int[MEMSIZ];

    static int PCREG;
    static int IRREG;
    static int GPREG;
    
    static boolean debug = false;

    static void readToMemory(String fname) throws IOException
    {
      String buffer = null;
      FileReader fr     = new FileReader(fname);
      BufferedReader br = new BufferedReader(fr);
      //StringTokenizer st1 = new StringTokenizer(buffer);  
      
	// for each line of "machine code":  while loop(like IO.java)
       int i = 0;
	while ((buffer = br.readLine()) != null)
        {
	//System.out.println("buffer " + buffer);
        String operator = buffer.substring(0, 2);
        String operand = buffer.substring(2); 
	int ioperand = Integer.parseInt(operand);
        int ioperator = Integer.parseInt(operator);
            if (debug)
            {      
                // display info about code as it is loaded into memory (use pad methood)
		System.out.println("readToMemory: " + "[" 
+ pad(i, 2) + "]" + " = (" + pad(ioperator, 2) + pad(ioperand, 2) + ")");
            }
            
            // copy machine code instruction into memory Array (convert the instructions to int)
	    MEMORY[i] = Integer.parseInt(buffer);
	    //System.out.println("MEMORY [" + i + "] = " + MEMORY[i]);
         i++;
	}

    }

    public static String pad(int n, int w)  //(copy pad to here)
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
    
    public static void dumpMemory()
    {
        System.out.println("===================================================================");
        int c=0;

        System.out.println("PCREG = " + pad(PCREG, 4));
        System.out.println("IRREG = " + pad(IRREG,4));
        System.out.println("GPREG = " + pad(GPREG, 4) + "\n");
         
        System.out.println("MEMORY:     0     1     2     3     4     5     6     7     8     9");
        System.out.println("    ---------------------------------------------------------------");
        
        for (int i=0; i<MEMSIZ; i++)
        {
            if ((i%10) == 0)
            {
                System.out.print("     " + c + "|");
                c++;
            }
            
            System.out.print("  " + pad(MEMORY[i], 4));
            
            if (((i+1)%10) == 0)
                System.out.println();
        }
        System.out.println();
        System.out.println("===================================================================");
    }
       
    public static void runProg() throws IOException
    {
	//copy the string operators[] = {"HALT", "ADD", ....}
	String operators[] = {"HALT", "ADD", "SUB", "MLT", "DIV", "ILOAD", "LOAD",
         "STOR", "READ", "WRITE", "BR", "BZ", "BN", "DUMP"};
	BufferedReader input = new BufferedReader (new InputStreamReader(System.in ));	
	VPCrte p = new VPCrte();

        // initialize PCREG to zero
	PCREG = 0;
        
        //loop:  //(while PCREG)
	while(PCREG <= MEMORY.length)
        {
            // fetch current instruction from memory and copy to IRREG
	    IRREG = MEMORY[PCREG];
        
            // extract opcode and operand from IRREG 
	     //IEEWG = MWMORY[???]   //51:01
	     int upcode = IRREG/100;
	     //operand =IRREG;
	     int operand = IRREG%100;
         
            if (debug)   
            {
                // display info about code as it is executed   IRREG
                System.out.println("runProg:  MEMORY[" + p.pad(PCREG, 2) + "] = " + pad(MEMORY[PCREG], 4) + 
", opcode = " + p.pad(upcode, 2) + ", operand = " + p.pad(operand, 2) + ", GPREG = " 
+ p.pad(GPREG, 4) + "(" + operators[upcode] + ")");
	    }

            // handle all of the opcodes with a large if/else if/else if/...
	    //(if upcode == "HALT"{System.exit[0]}, else if(upcode..)  use the assemble instructions to write the code)  1:05

	    if(upcode == HALT)
	    {
		System.exit(0);
	    }            

            else if(upcode == ADD)
            {
		GPREG = GPREG + MEMORY[operand]; 
            }
            else if(upcode == SUB)
            {
		GPREG = GPREG - MEMORY[operand];
            }

            else if(upcode == MLT)
            {
		GPREG = GPREG * MEMORY[operand];
            }

            else if(upcode == DIV)
            {
		GPREG = GPREG / MEMORY[operand];
            }

            else if(upcode == ILOAD)
            {
		GPREG = operand;
            }

            else if(upcode == LOAD)
            {
		GPREG = MEMORY[operand];
            }

            else if(upcode == STOR)
            {
		MEMORY[operand] = GPREG;
            }

            else if(upcode == READ)
            {
		System.out.println("[" + p.pad(operand, 2) + "]? ");
		String s = input.readLine();
		MEMORY[operand] = Integer.parseInt(s);
            }

            else if(upcode == WRITE)
            {
		System.out.println("[" + MEMORY[operand] + "] -> " + operand);  //maby wrong
            }

            else if(upcode == BR)
            {
		PCREG = PCREG - 3;
            }

            else if(upcode == BZ)
            {
		if(GPREG == 0)
		{
		  PCREG = PCREG - 3;
		}
            }

            else if(upcode == BN)
            {
		if(GPREG != 0)
                {
		  PCREG = PCREG - 3;
                }
            }

            else if(upcode == DUMP)
            {
		dumpMemory();
            }

            // increment PCREG to prepare for next instruction
	    PCREG++;
        }
    }
    
    public static void main(String argv[]) throws IOException
    {
        if (argv.length == 0) 
        {
            System.out.println("usage: java VPCrte FILENAME.exe [ debug ]");
            System.exit(0);
        } 
        
        if ((argv.length == 2) && (argv[1].equals("debug"))) debug = true;

        readToMemory(argv[0]);

        if (debug) dumpMemory();
        
        runProg();
    }
}
