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

# program purpuse
# enter an unknown number of integers (until the user enters a zero) and then calculates the average.

# variable map:
# 81 : store first number
# 83 : store avarage for display

READ 81
LOAD 81

BZ 9
ADD 82
STOR 82

ILOAD 1

ADD 83
STOR 83
BR 0

LOAD 82
DIV 83
STOR 84

WRITE 40
HALT 99
