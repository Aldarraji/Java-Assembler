#########################################################################
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
#########################################################################

#
#  VARIABLE MAP:
#
#  61:  stores a '1' for increment
#  90:  counting/looping variable
#
#  for (i=5; i>0; i--) println(i)

ILOAD 1    #0  
STOR  61   #1  store 61 to 1 (used by subtract)

ILOAD 5    #2
STOR 90    #3  store 5 (or result from subtract) in 90 (countdown variable)

WRITE 90   #4  display count
SUB 61     #5  subtract value at 61 (1) from GPREG.  result stored in GPREG

#DUMP 99
BN 3       #   if GPREG is nonzero, branch to STOR 90 above

HALT 99
