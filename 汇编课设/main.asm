COMMENT /**
Copyright <2018> <Zhao Nan>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

USERNAME: Zhao Nan
COMPLETE TIME: 2018-1-4
DESCRIBE: Print the number of daffodils between 100 and n(the number you input)
*/
.386
ASSUME CS:CODES, DS:DATA, SS:STACK
DATA SEGMENT  USE16
    MSG1   DB    0AH, 0DH, 'The shuixianhuashu is : $'           ;0AH, 0DH 代表换行和回车
    MSG2   DB    0AH, 0DH, 'Please input a three-digit number: ', '$'
    MSG3   DB    0AH, 0DH, 'Input Error, Please the number again:', '$'
    MSG4   DB    0AH, 0DH, 'The three-digit number is: ', '$'
    MSG5   DB    0AH, 0DH, 'Do you want to continue(y or Y/n or N): ', '$'
    MSG6   DB    0AH, 0DH, 'Input Error, Please input again:', '$'
    MSG7   DB    0AH, 0DH, 'The number you input is too little.$'
    NUMS   DB    3 DUP(0), 0AH, 0DH, '$'    ; 存储三位数字,后续中可以存储当前的三位数字，如：循环到了456，则分别存储4,5,6
    CUBES  DW    3 DUP(0)                   ; 存储立方数字，分别是 百位，十位，个位数字的立方
DATA ENDS

STACK SEGMENT USE16 STACK
    DB   100 DUP(0)
STACK ENDS


CODES SEGMENT USE16        ;使用 16位
;------------------------main-----------------------------
START:
    CALL  GET_INPUT        ;获取键盘输入
    CALL  JUDGE_lOW_BORDER ;判断输入数字是否是大于100的
    CALL  SWITCH_INPUT     ;输入转换存在 CX 中，CX控制循环
    SUB   CX, 100          ;不能输出100以下，所以这里减100，注意后面使用的时候加100或者用其他操作
LOOP1:
	CALL  SWITCH_NUM_SYS   ;将二进制转化为三个数字到 NUMS[0] NUMS[1] NUMS[2] 中
    CALL  CAL_CUBE         ;求水仙花立方
    JMP   JUDGE_NARC_NUM   ;判断水仙花数，是则显示
NEXT:
    LOOP  LOOP1            ;循环到 0为止
    JMP NEAR PTR IF_CONTINUE
    MOV   AX, 4C00H        ;程序终止
    INT   21H
;---------------------------------------------------------

;---------------------------------------------------------
;判断是否为水仙花数
JUDGE_NARC_NUM:
    MOV   AX, CUBES[0]
    ADD   AX, CUBES[2]
    ADD   AX, CUBES[4]
    SUB   AX, 100          ;由于不能输出100,注意
    CMP   AX, CX
    JNE   NEXT
    JE    DISPLAY          ;相等则显示


;显示水仙花数
DISPLAY:
    LEA   DX, MSG1         ;显示水仙花数
    MOV   AH, 09H
    INT   21H
    MOV   AH, 2

    MOV   DL, NUMS[0]      ;百位数字
    ADD   DL, 30H          ;转换为数字显示, ASCII加上 48 即为字符
    INT   21H
    MOV   DL, NUMS[1]      ;十位数字
    ADD   DL, 30H
    INT   21H
    MOV   DL, NUMS[2]      ;个位数字
    ADD   DL, 30H

    INT   21H
    JMP   NEXT


;键盘输入
GET_INPUT:
    MOV   CX, 3            ;三次输入数字
	MOV   AX, DATA
    MOV   DS, AX
    MOV   BX, 0            ;输入次数

	LEA   DX, MSG2         ;显示提示输入
	MOV   AH, 09H
    INT   21H

LOOP2:
	MOV	  AH, 01H          ;从键盘输入一个字符, 其ASCII存放在 AL 中
  	INT   21H

	JMP   INPUT_CHECK      ;输入检测判断


;输入检测
INPUT_CHECK:
	CMP   AL, '0'
	JB    INPUT_ERROR      ;输入ASCII码小于 0 输入错误
	CMP   AL, '9'
	JA    INPUT_ERROR      ;输入ASCII码大于 9 输入错误
	JNA   INPUT_RIGHT      ;否则输入正确


;输入错误
INPUT_ERROR:
	LEA   DX, MSG3         ;输出错误的信息模板
    MOV   AH, 09H
    INT   21H

    ;INC   CX
    MOV   CX, 4
    MOV   BX, 0
    JMP   CONTINUE


;输入正确
INPUT_RIGHT:
	MOV   NUMS[BX], AL     ;存储输入
  	INC   BX
  	JMP   CONTINUE         ;继续循环


;继续
CONTINUE:
	LOOP  LOOP2

  	LEA   DX, MSG4         ;输出正确的数字
  	MOV   AH, 09H
  	INT   21H
  	LEA   DX, NUMS
  	MOV   AH, 09H
  	INT   21H

	RET                    ;返回主程序，RET从堆栈中取出 IP

;-----------------------------------------------------

; 判断输入数字是否是大于100的
JUDGE_lOW_BORDER:
    CMP   NUMS[0], '0'
    JE    NUM_IS_LIttle
    RET

;输入数字太小
NUM_IS_LIttle:
    LEA   DX, MSG7
    MOV   AH, 09H
    INT   21H
    JMP   START

;输入转换存在  CX 中，CX控制循环
SWITCH_INPUT:
	MOV   CX, 0
	MOV   CL, NUMS[2]
    SUB   CL, '0'
	MOV   AL, 10
    MOV   BL, NUMS[1]
    SUB   BL, '0'
	MUL   BL
	ADD   CX, AX
	MOV   AL, 100
    MOV   BL, NUMS[0]
    SUB   BL, '0'
	MUL   BL
	ADD   CX, AX
	RET


;----------------------------------------------------------
;进制转换存在 NUMS[0], NUMS[1], NUMS[2] 中
SWITCH_NUM_SYS:
	MOV   AX, CX
    ADD   AX, 100
    MOV   BL, 100
    DIV   BL                    ;AL=百位数, AH=余数
    MOV   NUMS[0], AL           ;百位数字
    MOV   AL, AH
    MOV   AH, 0
    MOV   BL, 10
    DIV   BL
    MOV   NUMS[1], AL           ;十位数字
    MOV   NUMS[2], AH           ;个位数字
	RET


;求水仙花立方
CAL_CUBE:
    MOV   AL, NUMS[0]           ;三次连乘即可
    MOV   AH, 0
    MOV   BX, AX
    MUL   BX
    MUL   BX
    MOV   CUBES[0], AX

    MOV   AL, NUMS[1]
    MOV   AH, 0
    MOV   BX, AX
    MUL   BX
    MUL   BX
    MOV   CUBES[2], AX

    MOV   AL, NUMS[2]
    MOV   AH, 0
    MOV   BX, AX
    MUL   BX
    MUL   BX
    MOV   CUBES[4], AX
    RET

;-----------------------------------------------------------------

;-----------------------------------------------------------------
;输出水仙花数之后的判断是否继续
IF_CONTINUE:
    LEA   DX, MSG5        ;显示提示输入
    MOV   AH, 09H
    INT   21H
    MOV   AH, 01H         ;从键盘输入一个字符, 其ASCII存放在 AL 中
    INT   21H

    JMP CONTINUE_CHECK

;是否继续输入判断
CONTINUE_CHECK:           ;判断是否输入的是 y, Y, n, N
    CMP   AL, 'y'
    JE    CONTINUE_NEXT_ROUND
    CMP   AL, 'Y'
    JE    CONTINUE_NEXT_ROUND
    CMP   AL, 'n'
    JE    STOP
    CMP   AL, 'N'
    JE    STOP
    JNE   CONTINUE_ERROR

;继续
CONTINUE_NEXT_ROUND:
    JMP  NEAR PTR START


;退出
STOP:
    MOV   AX, 4C00H       ;程序终止
    INT   21H


;继续输入错误
CONTINUE_ERROR:
    LEA   DX, MSG6        ;输出错误的信息模板
    MOV   AH, 09H
    INT   21H
    JMP   IF_CONTINUE

;----------------------------------------------------

CODES ENDS
    END START
