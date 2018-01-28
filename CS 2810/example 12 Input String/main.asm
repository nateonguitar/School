TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	fileName BYTE 80 DUP(0)

.code
main PROC
	CALL Clrscr

	MOV edx, OFFSET fileName
	MOV ecx, SIZEOF fileName - 1		; minus 1 for the ,0
	CALL ReadString

	MOV edx, OFFSET fileName
	CALL WriteString

	CALL Crlf
	CALL WaitMsg
	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here

;;;;;;;;;;;;;
END main ;last line, put PROCs before this