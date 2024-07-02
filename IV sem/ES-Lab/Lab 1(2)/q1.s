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
	LDR R4,=VALUE
	LDR R5,=RESULT
	MOV R3,#10
	MOV R2,#0
LOOP
	LDR R0,[R4],#4
	ADD R2,R2,R0
	SUBS R3,R3,#1
	BNE LOOP
	STR R2,[R5]
	
VALUE DCD 1,2,3,4,5,6,7,8,9,10
RESULT DCD 0
	END