TITLE 					(main.asm)

INCLUDE Irvine32.inc
CHAR_VALUE = 'X' ; set global constant value

.data
	declairClass BYTE "CS2810 Fall Semester 2015",0
	declairAssignment BYTE "Assembler Assignment #3",0
	declairName BYTE "Nathan Brooks",0
	promptMessage BYTE "Enter an MP3 header in hex format: ",0

	userInputStored DWORD ?

	version					BYTE ?
	versionString			BYTE "MPEG Version ",0
	version2_5String		BYTE "2.5",0
	versionReservedString	BYTE "reserved",0
	version2_0String		BYTE "2.0",0
	version1_0String		BYTE "1.0",0

	layerString				BYTE "Layer ",0
	layerReservedString		BYTE "Reserved",0
	layerIIIString			BYTE "III",0
	layerIIString			BYTE "II",0
	layerIString			BYTE "I",0

	sampleString			BYTE "Sampling Rate: ",0
	sampleStartAmount		WORD 0
	sampleReservedString	BYTE "Reserved Frequency Index",0
	hz                      BYTE " Hz",0

.code
main PROC
	
	CALL ClearRegisters
	CALL SetMyColor
	CALL WriteIntroClass
	CALL WriteIntroAssignment
	CALL WriteIntroName
	CALL PromptUser				; what user enters is stored in eax
	CALL StripOffNotNeededBits
	CALL VersionTest
	CALL LayerTest

	; SampleRateTest also handles if you enter a 
	; reserved Sampling rate frequency index of 11
	; like typing in ffffffff
	CALL SampleRateTest			

	CALL WaitMsg

	exit
main ENDP

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;PROCS

VersionTest PROC
	; BB
	; MPEG Audio version ID
	; 00 - MPEG Version 2.5 (later extension of MPEG 2)
	; 01 - reserved 
	; 10 - MPEG Version 2 (ISO/IEC 13818-3) 
	; 11 - MPEG Version 1 (ISO/IEC 11172-3)

	SHR eax, 19
	; now the only possible on bits are the least significant 2 bits

	; write the start of the version message
	MOV edx, OFFSET versionString
	CALL WriteString

	; save version for later use with sample rating
	MOV version, al

	; if 00 version 2.5
	CMP al, 00000000b
	JZ Version2_5

	; if 01 reserved
	CMP al, 00000001b
	JZ VersionReserved

	; if 10 version 2.0
	CMP al, 00000010b
	JZ Version2_0

	; if 11 version 1.0
	CMP al, 00000011b
	JZ Version1_0

	CALL WaitMsg

Version2_5:
	MOV edx, OFFSET version2_5String
	MOV bl, 00000000b
	JMP EndVersionTest

VersionReserved:
	MOV edx, OFFSET versionReservedString
	MOV bl, 00000001b
	JMP EndVersionTest

Version2_0:
	MOV edx, OFFSET version2_0String
	MOV bl, 00000010b
	JMP EndVersionTest

Version1_0:
	MOV edx, OFFSET version1_0String
	MOV bl, 00000011b
	JMP EndVersionTest

EndVersionTest:
	; bl has the version stored
	; save it in version for use in SampleRateTest
	MOV version, bl

	; correct string set in previous tests
	CALL WriteString
	CALL Crlf

	ret
VersionTest ENDP

;--------------------------------------

LayerTest PROC
	; CC
	; 00 - reserved 
	; 01 - Layer III
	; 10 - Layer II
	; 11 - Layer I

	; userInputStored has the original
	MOV eax, userInputStored
	
	SHR eax, 17
	AND al, 00000011b ; only care about the lowest 2 bits

	; write the start of the version message
	MOV edx, OFFSET layerString
	CALL WriteString

	; if 00 LayerReserved
	CMP al, 00000000b
	JZ LayerReserved

	; if 01 LayerIII
	CMP al, 00000001b
	JZ LayerIII

	; if 10 LayerII
	CMP al, 00000010b
	JZ LayerII

	; if 11 LayerI
	CMP al, 00000011b
	JZ LayerI

	CALL WaitMsg

LayerReserved:
	MOV edx, OFFSET layerReservedString
	JMP EndVersionTest

LayerIII:
	MOV edx, OFFSET layerIIIString
	JMP EndVersionTest

LayerII:
	MOV edx, OFFSET layerIIString
	JMP EndVersionTest

LayerI:
	MOV edx, OFFSET layerIString
	JMP EndVersionTest

EndVersionTest:
	; correct string set in previous tests
	CALL WriteString
	CALL Crlf


	ret
LayerTest ENDP

;--------------------------------------

SampleRateTest PROC
	; FF
	;    MPEG1    MPEG2    MPEG2.5
	; 00 44100 Hz 22050 Hz 11025 Hz
	; 01 48000 Hz 24000 Hz 12000 Hz
	; 10 32000 Hz 16000 Hz 8000 Hz
	; 11 reserv. reserv. reserv.
	
	; userInputStored has the original
	MOV eax, userInputStored

	; need bits 11 and 10 only
	; shift so those bits are at the right most position
	SHR eax, 10

	; clear any other bits 
	AND ax, 0000000000000011b

	; clear bx so we can use it as a zero for later
	XOR bx, bx
	
	; compare to find out what sample rating to start with
	; 00 start with 11025
	CMP al, 00000000b
	JZ Sample11025

	CMP al, 00000001b
	JZ Sample12000

	CMP al, 00000010b
	JZ Sample8000

	; no sense checking anymore, reserved
	CMP al, 00000011b
	JZ VersionReserved

Sample11025:
	MOV bx, 11025
	JMP SampleStartisSet

Sample12000:
	MOV bx, 12000
	JMP SampleStartisSet

Sample8000:
	MOV bx, 8000

SampleStartIsSet:
	MOV sampleStartAmount, bx

	; now multiply depending on the MPEG Version

	; version 2.5 needs no shifts, already correct number
	CMP version, 00000000b
	JZ ShiftBitsLeftZero

	;veresion 2.0 needs 1 shift, 2x existing number
	CMP version, 00000010b
	JZ ShiftBitsLeftOne

	;version 1.0 needs 2 shifts, 4x existing number
	CMP version, 00000011b
	JZ ShiftBitsLeftTwo

	; going to shift only one space in each
	; but if it needs shifting two
	; it will run through both shifts below
ShiftBitsLeftTwo:
	SHL ebx, 1
ShiftBitsLeftOne:
	SHL ebx, 1
ShiftBitsLeftZero:

	MOV edx, OFFSET sampleString
	CALL WriteString
	MOV eax, ebx
	CALL WriteInt
	MOV edx, OFFSET hz

	; skips over VersionReserved
	JMP Finish 

	; this is called if we need to skip over all that shifting and stuff
VersionReserved:
	MOV edx, OFFSET sampleReservedString

Finish:
	CALL WriteString
	CALL Crlf

	ret
SampleRateTest ENDP

;--------------------------------------

StripOffNotNeededBits PROC

	;        AAAA AAAA AAAB BCCD EEEE FFGH IIJJKLMM
	AND eax, 00000000000111100000110000000000b

	; store original in userInputStored for safe keeping
	MOV userInputStored, eax

	ret
StripOffNotNeededBits ENDP

;--------------------------------------

PromptUser PROC
	MOV dh, 6
	MOV dl, 0
	CALL Gotoxy

	; prompt user
	MOV edx, OFFSET promptMessage
	CALL WriteString
	CALL ReadHex
	
	ret
PromptUser ENDP

;--------------------------------------

SetMyColor PROC
	MOV eax, yellow + (green * 16)
	CALL SetTextColor
	CALL Clrscr
	ret
SetMyColor ENDP

;--------------------------------------

WriteIntroClass PROC
	; move the cursor and write "CS2810 Fall Semester 2015"
	MOV dh, 2
	MOV dl, 0
	CALL Gotoxy
	MOV edx, OFFSET declairClass
	CALL WriteString
	ret
WriteIntroClass ENDP

;--------------------------------------

WriteIntroAssignment PROC
	; move the cursor and write "Assembler Assignment #3"
	MOV dh, 3
	MOV dl, 0
	CALL Gotoxy
	MOV edx, OFFSET declairAssignment
	CALL WriteString
	ret
WriteIntroAssignment ENDP

;--------------------------------------

WriteIntroName PROC
	; move cursor and write "Nathan Brooks"
	MOV dh, 4
	MOV dl, 0
	CALL Gotoxy
	MOV edx, OFFSET declairName
	CALL WriteString
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;END PROCS

END main