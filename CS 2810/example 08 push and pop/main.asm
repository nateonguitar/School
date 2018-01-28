TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	dwordVal DWORD ?

.code
main PROC
	CALL Clrscr

	; push values to stack
	PUSH esi
	PUSH ecx
	PUSH ebx
	
	; display some memory
	MOV esi, OFFSET dwordVal
	MOV ecx, LENGTHOF dwordVal
	MOV ebx, TYPE dwordVal

	CALL DumpRegs

	; pop all the values
	POP ebx
	POP ecx
	POP esi

	CALL DumpRegs


	exit
main ENDP

END main