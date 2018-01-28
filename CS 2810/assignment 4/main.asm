TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	declairClass				BYTE "CS2810 Fall Semester 2015",0
	declairAssignment			BYTE "Assembler Assignment #4",0
	declairName					BYTE "Nathan Brooks, Nathan Mason",0

	questionRepeatString		BYTE "Enter another?(y/n)",0
	dateString					BYTE "Enter a FAT16 file date: ",0

	vUserDate					WORD ?

	; all months are length 10
	month	BYTE "January ",0,"  " ; 0000
			BYTE "February ",0," " ; 0001
			BYTE "March ",0,"    " ; 0010
			BYTE "April ",0,"    " ; 0011
			BYTE "May ",0,"      " ; 0100
			BYTE "June ",0,"     " ; 0101
			BYTE "July ",0,"     " ; 0110
			BYTE "August ",0,"   " ; 0111
			BYTE "September ",0	   ; 1000
			BYTE "October ",0,"  " ; 1001
			BYTE "November ",0," " ; 1010
			BYTE "December ",0," " ; 1011
			BYTE "Not Valid ",0    ; 1100
			BYTE "Not Valid ",0    ; 1101
			BYTE "Not Valid ",0    ; 1110
			BYTE "Not Valid ",0    ; 1111

	vDay BYTE ?
	vDayBinary WORD ?
	vYear BYTE "----",0

	daySuffix	BYTE "st, ",0 ;5
				BYTE "nd, ",0
				BYTE "rd, ",0
				BYTE "th, ",0


.code
main PROC

L1:
	CALL ClearRegisters
	CALL SetMyColor
	CALL WriteIntroClass
	CALL WriteIntroAssignment
	CALL WriteIntroName

	CALL DisplayQuestion

	;Hour = left 5 bits, Minutes = Middle 6, Seconds = Right 5 ( x 2)
	;Year = 1980+Left 7, Month = Middle 4, Day = Right 5



	CALL DisplayMonth
	CALL DisplayDay
	CALL DisplayPostfix
	CALL DisplayYear

	;ask question to repeat
	CALL AskRepeat
	CMP al, 'y'
	JZ L1

	exit
main ENDP

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;PROCS

DisplayQuestion PROC
	MOV edx, OFFSET dateString
	CALL WriteString
	CALL ReadHex
	MOV vUserDate, ax
	ret
DisplayQuestion ENDP

;--------------------------------------

AskRepeat PROC
	MOV edx, OFFSET questionRepeatString
	CALL WriteString
	CALL ReadChar
	ret
AskRepeat ENDP

;--------------------------------------

SetMyColor PROC
	MOV eax, yellow + (blue * 16)
	CALL SetTextColor
	CALL Clrscr
	ret
SetMyColor ENDP

;--------------------------------------

WriteIntroClass PROC
	; move the cursor and write "CS2810 Fall Semester 2015"
	MOV dh, 4
	MOV dl, 0
	CALL Gotoxy
	MOV edx, OFFSET declairClass
	CALL WriteString
	ret
WriteIntroClass ENDP

;--------------------------------------

WriteIntroAssignment PROC
	; move the cursor and write "Assembler Assignment #3"
	MOV dh, 5
	MOV dl, 0
	CALL Gotoxy
	MOV edx, OFFSET declairAssignment
	CALL WriteString
	ret
WriteIntroAssignment ENDP

;--------------------------------------

WriteIntroName PROC
	; move cursor and write "Nathan Brooks, Nathan Mason"
	MOV dh, 6
	MOV dl, 0
	CALL Gotoxy
	MOV edx, OFFSET declairName
	CALL WriteString
	CALL Crlf
	CALL Crlf
	ret
WriteIntroName ENDP

;--------------------------------------

ClearRegisters PROC
	; clear the registers
	XOR eax, eax
	XOR ebx, ebx
	XOR ecx, ecx
	XOR edx, edx
	XOR esi, esi
	XOR edi, edi
	XOR ebp, ebp
	ret
ClearRegisters ENDP

;---------------------------
;Year = 1980+Left 7, Month = Middle 4, Day = Right 5
;0000000 0000 00000
;
;AND 0000000111100000
DisplayMonth PROC
	CALL Crlf
	XOR eax, eax
	MOV ax, vUserDate

	; little endian
	ROR ax, 8
		
	AND ax, 0000000111100000b
	SHR ax, 5

	; all 0's for the month should break it
	CMP ax, 0
	JZ notValid
	JNZ good

notValid:
	NOT ax
	AND ax, 1111b

good:
	SUB ax, 1

	MOV edx, offset [month]
	MOV bl, 11
	MUL bl

	ADD edx,eax
	CALL WriteString
	ret
DisplayMonth ENDP

;---------------------------

DisplayDay PROC
	XOR eax, eax
	XOR edx, edx
	MOV ax, vUserDate

	; little endian
	ROR ax, 8

	AND ax, 0000000000011111b
	MOV vDayBinary, ax
	MOV bh, 10
	DIV bh

	; AL contains quotient
	; AH contains remainder
	ADD ax, 30h

	MOV byte ptr[vDay], al

	ADD ah, 30h
	MOV byte ptr[vDay + 1], ah


	MOV edx, OFFSET vDay
	CALL WriteString
	ret
DisplayDay ENDP
;---------------------------

DisplayPostfix PROC
	
	; check for values 1st, 21st, 31st for st suffix
	
	XOR eax, eax
	MOV ax, vUserDate

	; little endian
	ROR ax, 8
		
	AND ax, 0000000000011111b

	CMP ax, 1d
	JZ stSuffix
	CMP ax, 21d
	JZ stSuffix
	CMP ax, 31d
	JZ stSuffix

	; check for 2nd, 22nd for nd suffix

	CMP ax, 2d
	JZ ndSuffix
	CMP ax, 22d
	JZ ndSuffix

	; check for 3rd, 23rd for rd suffix
	CMP ax, 3d
	JZ rdSuffix
	CMP ax, 23d
	JZ rdSuffix

	; else th suffix
	JMP thSuffix


	stSuffix:
		MOV edx, OFFSET daySuffix
		CALL Writestring
		JMP endSuffix
	
	ndSuffix:
		MOV edx, OFFSET [daySuffix + 5]
		CALL Writestring
		JMP endSuffix

	rdSuffix:
		MOV edx, OFFSET [daySuffix + 10]
		CALL Writestring
		JMP endSuffix

	thSuffix:
		MOV edx, OFFSET [daySuffix + 15]
		CALL Writestring
		JMP endSuffix

	endSuffix:
		


	ret
DisplayPostfix ENDP

;--------------------------

DisplayYear PROC

;Year = 1980+Left 7, Month = Middle 4, Day = Right 5
;1111111 0000 00000b


	XOR eax, eax
	XOR dx, dx

	MOV ax, vUserDate

	; little endian
	ROR ax, 8
		
	AND ax, 1111111000000000b
	SHR ax, 9

	ADD ax, 1980d

	; divide by 1000
	MOV bx, 1000d
	DIV bx

	;convert to ascii
	ADD al, 30h

	; put 1000 year into first position
	MOV BYTE ptr [vYear], al

	; put remainder into ax
	MOV ax, dx

	; make sure dx is ready to accept another remainder
	XOR dx, dx

	; divide by 100
	MOV bx, 100
	div bx

	; convert to ascii
	ADD al, 30h

	; put 100 year into 2nd position
	MOV BYTE ptr [vYear+1], al

	; put remainder into ax
	MOV ax, dx

	; divide by 10
	MOV bl, 10
	DIV bl

	; convert to ascii
	ADD ah, 30h
	ADD al, 30h

	; put 10 and 1 year into 3rd and 4th position
	MOV BYTE ptr[vYear+2], al
	MOV BYTE ptr[vYear+3], ah

	MOV edx, OFFSET [vYear]
	CALL WriteString
	CALL Crlf
	CALL Crlf

	ret
DisplayYear ENDP

;---------------------------


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;END PROCS

END main