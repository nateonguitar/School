TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	helloWorld BYTE "Hello World",0
	fromProc BYTE " from procedure",0

.code
main PROC
	MOV eax, yellow + (brown * 16)
	CALL SetTextColor
	CALL Clrscr

	MOV edx, OFFSET helloWorld
	CALL WriteString
	CALL Crlf
	CALL WaitMsg

	CALL myFunc
	

	CALL WaitMsg

	

	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here
myFunc PROC
	CALL Crlf
	MOV edx, OFFSET helloWorld
	CALL WriteString

	MOV edx, OFFSET fromProc
	CALL WriteString
	CALL Crlf

	ret
myFunc ENDP
;;;;;;;;;;;;;
END main ;last line, put PROCs before this