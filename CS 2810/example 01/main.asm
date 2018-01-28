TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
myMessage BYTE "HELLO WORLD",0
classDeclair BYTE "This is CS2810",0

.code
main PROC
	CALL Clrscr

	MOV dh, 0
	MOV dl, 2
	CALL Gotoxy

	MOV	 edx,OFFSET myMessage
	CALL WriteString
	
	MOV dh, 1
	MOV dl, 3
	CALL Gotoxy

	MOV edx,OFFSET classDeclair
	CALL WriteString

	MOV dh, 5
	MOV dl, 5
	CALL Gotoxy

	CALL WaitMsg


	exit
main ENDP

END main