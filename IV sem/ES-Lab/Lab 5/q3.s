	AREA    RESET, DATA, READONLY
    EXPORT  __Vectors

__Vectors 
	DCD  0x10001000     ; stack pointer value when stack is empty
    DCD  Reset_Handler  ; reset vector
  
    ALIGN

	AREA mycode, CODE, READONLY
	ENTRY
	EXPORT Reset_Handler
	
Reset_Handler
	LDR R0,=VALUE
	LDR R1,=10
	MOV R2,#10
WHILE 
	LDR R3,[R0],#4
	CMP R3,R1
	BNE LOOP
	MOV R6,#1
	B STOP
LOOP
	SUBS R2,#1
	BNE WHILE
	MOV R6,#-1
STOP
	 B STOP
VALUE DCD 1,2,3,4,5,6,7,8,9,10
