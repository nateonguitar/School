TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	

.code
main PROC
	CALL Clrscr

	MOV eax, 0
	MOV ecx, 10

L1:
	MOV eax, 100
	CALL RandomRange
	CALL WriteInt
	CALL Crlf
	LOOP L1

	CALL WaitMsg
	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here

;;;;;;;;;;;;;
END main ;last line, put PROCs before this