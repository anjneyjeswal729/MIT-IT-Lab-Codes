	AREA RESET, DATA, READONLY
	EXPORT __Vectors
__Vectors
    DCD 0x10001000 ; stack pointer value when stack is empty
    DCD Reset_Handler ; reset vector
    ALIGN
    AREA MYCODE, CODE, READONLY
    ENTRY
	EXPORT Reset_Handler
Reset_Handler
	LDR R0,=SRC
	LDR R1,=DEST
	MOV R2,#10
LOOP
	LDR R4,[R0],#4
	STR R4,[R1],#4
	SUBS R2,R2,#1
	BNE LOOP
	STR R4,[R1],#4
STOP
	B STOP

SRC DCD 1,2,3,4,5,6,7,8,9,10
	AREA DATA, DATA, READWRITE
DEST DCD 0,0,0,0,0,0,0,0,0,0
	END
