TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	count DWORD ?

.code
main PROC
	CALL Clrscr
	MOV edx, 0
	MOV ecx, 100		; set outer loop count

L1:
	MOV count, ecx		; save outer loop count
	MOV ecx, 20			; set inner loop count

L2:
	INC edx

	loop L2				; repeat the inner loop
	MOV ecx, count		; restore outer loop count
	loop L1				; repeat the outer loop

	CALL DumpRegs		; 2000d = 7D0h


	exit
main ENDP

END main