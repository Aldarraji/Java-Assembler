# Java-Assembler

## Introduction
This project is a combunation of 3 main files: 
1.  Assemble.java
2.  VPCrte.java
3.  averageNums.asm

### The VPCrte. java is a single tasking computer which will compile/assemble and execute a single program.  Virtual memory, multiple processing and task switching will not be supported.

### Assemble.java which will "assemble" a source code program written in VPC assembly language (detailed below) and convert it to an executable format which can be executed by a second program (VPCrte.java) which provides a run time environment for the programs.

### averageNums.asm is a program written in VPC assembly language which will allow the user to enter an unknown number of integers (until  the user enters a zero) and then calculates and displays the average of the numbers.

## Notes and screenshots
Our Virtual PC computer memory consists of 100 ints, addressed 00-99.

The computer memory is used to store both the executable program code and
also any variables which are needed by the program.  The computer memory
will be represented in our program as an array of 100 elements.

The only data our computer/programs can work with are non-negative integers
between 0-99.  Floating point variables, strings, etc are not supported.

The VPC has a grand total of three registers:

=============================================================================
    PCREG - Program Counter Register.  Contains the "address" (index) of the
            currently executing instruction.

    IRREG - Instruction Register.  Contains a copy of the currently executing
            instruction.

    GPREG - General Purpose Register.  This register is used by the user
            program to perform arithmetic operations.  ARITHMETIC OPERATIONS
            CAN ONLY BE PERFORMED ON DATA STORED IN THE GPREG.
=============================================================================

The virtual processor in our VPC will support the following assembly language
instructions:

/#########################################################################
# HALT  = 0;  stop program
# ADD   = 1;  GPREG is updated with itself + MEMORY[arg]
# SUB   = 2;  GPREG is updated with itself - MEMORY[arg]
# MLT   = 3;  GPREG is updated with itself * MEMORY[arg]
# DIV   = 4;  GPREG is updated with itself / MEMORY[arg]
# ILOAD = 5;  arg to GPREG
# LOAD  = 6;  MEMORY[arg] to GPREG
# STOR  = 7;  GPREG to MEMORY[arg]
# READ  = 8;  keyboard to MEMORY[arg]
# WRITE = 9;  MEMORY[arg] to screen
# BR    = 10; unconditional branch to instruction at MEMORY[arg]
# BZ    = 11; branch if GPREG is zero
# BN    = 12; branch if GPREG is nonzero
# DUMP  = 13; dump memory/register contents
/#########################################################################

VPC assembly language instruction format consists of the name of the
instruction and a single argument.  The HALT and DUMP instructions will
not do anything with the argument, but one should be provided anyway to
simplify the Assembler and VPCrte programs.  I recommend using a '99'
as the argument to make them stand out a little when you look at a
memory dump.

An example of a VPC assembly instruction to perform addition:

ADD 5

The above instruction will add the value in memory at address 5 to the value
in stored in GPREG.  The results will be stored in GPREG.

Here is a sample program execution which will show a VPC program being used
to prompt the user for two values, add them together and display the sum.

================================================================================
$ java VPCrte addTwoNumsInteractive.exe
[90]? 5
[91]? 9
[92] -> 14
================================================================================

The program prompted the user for two values which are stored in memory
locations 90 and 91.  The program then computes the sum and stores the result
in memory location 92.  Then the value in memory location 92 is displayed.

The source code for the program is 
================================================================================
$ cat addTwoNumsInteractive.asm
#
# VARIABLE MAP:
#
#  90:  first var
#  91:  second var
#  92:  sum

READ 90  # read 1st value to 90
LOAD 90  # load into GPREG
READ 91  # read 2nd value to 91
ADD  91  # add value from 91 to value in GPREG
STOR 92  # store sum (stored in GPREG) to 92
#DUMP 99
WRITE 92 # display sum
HALT 99
================================================================================

Compile/assemble the source code as follows:
================================================================================
$ java Assemble addTwoNumsInteractive.asm > addTwoNumsInteractive.exe
================================================================================

Note that the assembler simply displays the executable code as output. 
Redirecting the output to a file as shown above creates the executable format.

The assembler converts the string opcodes (ADD, SUB, etc) with the
corresponding numeric integer code and appends the numeric operand.  It
should ignore blank lines or anything that follows a '#' sign.

The "executable" format is as follows:
================================================================================
$ cat addTwoNumsInteractive.exe
0890
0690
0891
0191
0792
0992
0099
================================================================================

If the user forgets to supply a command line parameter (source file name),
print an error and exit.

================================================================================
$ java Assemble
usage:  java Assemble INPUTFILE
================================================================================

To help you debug your VPCrte program (and also the programs that it will
eventually execute), your RTE should support an instruction to dump the
contents of the registers and memory.  As you are writing the code for the
RTE, you will want to embed calls to your memory dumping method to verify
what is going on with your program.  To help debug VPC assembly programs,
the user of your RTE should be able to invoke "debug mode" which will provide
information as your program is being loaded into memory, an initial memory
dump (prior to your program beginning) and an instruction by instruction
trace as the code executes.

================================================================================
$ java VPCrte addTwoNumsInteractive.exe debug
readToMemory: [00] = (0890)
readToMemory: [01] = (0690)
readToMemory: [02] = (0891)
readToMemory: [03] = (0191)
readToMemory: [04] = (0792)
readToMemory: [05] = (0992)
readToMemory: [06] = (0099)
===================================================================
PCREG = 0000
IRREG = 0000
GPREG = 0000

MEMORY:     0     1     2     3     4     5     6     7     8     9
    ---------------------------------------------------------------
     0|  0890  0690  0891  0191  0792  0992  0099  0000  0000  0000
     1|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     2|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     3|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     4|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     5|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     6|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     7|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     8|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     9|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000

===================================================================
runProg:  MEMORY[00] = 0890, opcode = 08, operand = 90, GPREG = 0000 (READ)
[90]? 3
runProg:  MEMORY[01] = 0690, opcode = 06, operand = 90, GPREG = 0000 (LOAD)
runProg:  MEMORY[02] = 0891, opcode = 08, operand = 91, GPREG = 0003 (READ)
[91]? 8
runProg:  MEMORY[03] = 0191, opcode = 01, operand = 91, GPREG = 0003 (ADD)
runProg:  MEMORY[04] = 0792, opcode = 07, operand = 92, GPREG = 0011 (STOR)
runProg:  MEMORY[05] = 0992, opcode = 09, operand = 92, GPREG = 0011 (WRITE)
[92] -> 11
runProg:  MEMORY[06] = 0099, opcode = 00, operand = 99, GPREG = 0011 (HALT)
================================================================================

Note that the string "debug" was supplied as a command line parameter
to generate the additional debugging output.

If additional debugging information is needed, the user can include
a DUMP instruction in their program as needed:
================================================================================
$ cat addTwoNumsInteractive.asm
# <snip> (comments have been deleted here)
READ 90  # read 1st value to 90
LOAD 90  # load into GPREG
READ 91  # read 2nd value to 91
ADD 91   # add value from 91 to value in GPREG
STOR 92  # store sum to 92
DUMP 99  # Note:  call to DUMP has been uncommented!
WRITE 92 # display sum
HALT 99
$ java VPCrte addTwoNumsInteractive.exe
[90]? 3
[91]? 5
===================================================================
PCREG = 0005
IRREG = 1399
GPREG = 0008

MEMORY:     0     1     2     3     4     5     6     7     8     9
    ---------------------------------------------------------------
     0|  0890  0690  0891  0191  0792  1399  0992  0099  0000  0000
     1|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     2|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     3|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     4|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     5|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     6|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     7|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     8|  0000  0000  0000  0000  0000  0000  0000  0000  0000  0000
     9|  0003  0005  0008  0000  0000  0000  0000  0000  0000  0000

===================================================================
[92] -> 8
================================================================================
