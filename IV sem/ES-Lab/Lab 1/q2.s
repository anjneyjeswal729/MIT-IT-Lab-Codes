	AREA    RESET, DATA, READONLY
    EXPORT  __Vectors

__Vectors 
	DCD  0x100000FF     ; stack pointer value when stack is empty
    DCD  Reset_Handler  ; reset vector
  
    ALIGN

	AREA mycode, CODE, READONLY
	EXPORT Reset_Handler
	ENTRY
	
Reset_Handler
	LDR R0, =SRC
	ADD R0, #36
	LDR R1, =DEST
	MOV R2, #10
LOOP
	LDR R3,[R0],#-4
	STR R3,[R1],#4
	SUBS R2, #01
	BNE LOOP

STOP
	B STOP
SRC DCD 1,2,3,4,5,6,7,8,9,10
	AREA mycode1, DATA, READWRITE
DEST DCD 0
	END