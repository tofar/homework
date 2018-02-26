#include<stdio.h>
#include<stdlib.h>
#include<graphics.h>
#include<conio.h>
#include<string.h>
#include<math.h>
#include<time.h>
#include<Windows.h>

#if _MSC_VER>=1900

_ACRTIMP_ALT FILE* __cdecl  __acrt_iob_func(unsigned);
#ifdef __cplusplus 
extern "C"
#endif 
FILE* __cdecl __iob_func(unsigned i) {
	return __acrt_iob_func(i);
}
#endif /* _MSC_VER>=1900 */

#pragma comment(lib, "WINMM.LIB")    //播放音乐要用（wav格式）

DWORD WINAPI myfun1(LPVOID lpParameter);                   //声明线程函数  
DWORD WINAPI myfun2(LPVOID lpParameter);

void start();
void gamestart();
void help();
void gameout();
void copymap(int a[18][25], int b[18][25]);
void mapchoose(int choosemap);
void mytankmove(int movedir);
void shoot(int kind, int dir, int po_x, int po_y, int speed, int *k);
void gamerepeat();
void pause();
void raking();
void enemy1tankmove(int movedir);
void enemy2tankmove(int movedir);
void enemy3tankmove(int movedir);
void original_map();
void original_tank();
void through();
void autoenemy(int e);
void record(int sore);


int map[18][25] = { 0 };   //使用地图
int sores = 0;  //我的得分
int  kind2 = 0, k = 0;//子弹的状态
int enemy1kind = 0, enemy2kind = 0, enemy3kind = 0, k1 = 0, k2 = 0, k3 = 0;//敌军子弹状态
int tanknum;//剩余坦克数量
int map1[18][25] = { 0 }, map2[18][25] = { 0 }, map3[18][25] = { 0 };   //七个地图
int map4[18][25] = { 0 }, map5[18][25] = { 0 }, map6[18][25] = { 0 };
int empty[18][25] = { 0 };
clock_t enemy1m1, enemy1m2, enemy2m1, enemy2m2, enemy3m1, enemy3m2;   //敌方坦克移动时间
clock_t enemy1shoot1, enemy1shoot2, enemy2shoot1, enemy2shoot2, enemy3shoot1, enemy3shoot2;//敌方坦克射击的射程计算
int enemy1x, enemy1y, enemy2x, enemy2y, enemy3x, enemy3y;   //敌方坦克坐标 
int  enemy1dir, enemy3dir, enemy2dir;   
int e1 = 1, e2 = 2, e3 = 3;     //不同的坦克自动移动设置
int fun;//判断线程2是否进行，其中 0 为不进行，1 为进行

HANDLE h1, h2;     //声明线程变量

struct Tank
{
	int kind;//坦克的阵营，5为我方阵营，6及6以上为地方阵营
	int po_x;//坦克的纵坐标
	int po_y;//坦克的横坐标
	int bulletspeed;//子弹速度
	int dir;//坦克方向,1为上，2为左，3为下，4为右
	int blood;//血量即为1时一枪毙命
	int number;    //坦克数量
	int range;
}mytank = { 5,16,10,250,1,1 ,1,8 }, enemy1 = { 6,1,1,300,3,1,5,8 }, enemy2 = { 8,1,11,200,3,2,6,9 }, enemy3 = { 9,1,23,200,3,3,7,9 };

struct bullet
{
	int kind;//子弹阵营  7
	int dir;//即坦克方向
};
IMAGE temp;           //用于游戏中暂停时记录当时的场景

int main()
{

	initgraph(640, 480);
	PlaySound("tank.wav", NULL, SND_FILENAME | SND_ASYNC | SND_LOOP);//循环播放背景音乐
	IMAGE img2;
	loadimage(&img2, "resource\\menu.bmp", 640, 480);//  坦克大战一代图
	putimage(0, 0, &img2);
	Sleep(1500);
	start();
	closegraph();
	return 0;
}

//游戏开始界面及选择
void start()
{

	IMAGE img1;
	cleardevice();
	loadimage(&img1, "resource\\start2.jpg", 640, 480);  //开始菜单
	putimage(0, 0, &img1);
	char ch=_getch();
	                                                   //防止不是1~4
	while (ch<='0' || ch>'4')     
		ch = _getch();
	switch (ch)
	{
	case '1':
		gamestart();//开始游戏
		break;
	case '2':
		gameout();
		break;   // 退出游戏
	case '3':
		help();//帮助
		break;
	case '4':
		raking();
		break;  //排行榜
	default:
		break;
	}
}

//游戏开始
void gamestart()
{
label:fun = 1;//使得线程2可以进行】
	int i, j;
	//地图初始化数组
	for (i = 0; i < 18; i++)
		for (j = 0; j < 25; j++)
			map[i][j] = 0;

	original_map();                            //初始化地图
	original_tank();                           //初始化坦克
			        //模式选择
	IMAGE img[10];
	IMAGE image1, image2, image3, image4, image5;
	loadimage(&img[0], "resource\\模式选择.jpg", 640, 480);
	putimage(0, 0, &img[0]);
	char choose;
	int num;
	switch (_getch())
	{
	case '1':
		cleardevice();
		loadimage(&img[1], "resource\\自由模式.jpg", 640, 480);
		putimage(0, 0, &img[1]);
		choose = _getch();//地图选择
		num = choose - 48;
		if (num >= 1 && num <= 6)
		{
			cleardevice(); mapchoose(num);
		}
		else goto label;           //返回上一级
		if (num == 1)
		{
			loadimage(&img[4], "resource\\map1.jpg", 500, 360);
			putimage(0, 0, &img[4]);
		}
		if (num == 2)
		{
			loadimage(&img[5], "resource\\map2.jpg", 500, 360);
			putimage(0, 0, &img[5]);
		}
		if (num == 3)
		{
			loadimage(&img[6], "resource\\map3.jpg", 500, 360);
			putimage(0, 0, &img[6]);
		}
		if (num == 4)
		{
			loadimage(&img[7], "resource\\map4.jpg", 500, 360);
			putimage(0, 0, &img[7]);
		}
		if (num == 5)
		{
			loadimage(&img[8], "resource\\map5.jpg", 500, 360);
			putimage(0, 0, &img[8]);
		}
		if (num == 6)
		{
			loadimage(&img[9], "resource\\map6.jpg", 500, 360);
			putimage(0, 0, &img[9]);
		}
		break;
	case '2':
		loadimage(&image1, "resource\\闯关模式.jpg", 640, 480);
		putimage(0, 0, &image1);
		mapchoose(4);
		choose = _getch();
		num = choose - 48;
		if (num <= 0 || num >= 4)
			goto label;       //返回上一级
		if (num == 2)
		{
			enemy1.bulletspeed = 200;
			enemy1.number = 7;
			enemy1.range = 9;
			enemy1.blood = 2;
			enemy2.bulletspeed = 100;
			enemy3.range = 10;
		}
		if (num == 3)
		{
			enemy1.bulletspeed = 100;
			enemy1.number = 7;
			enemy3.number = 10;
			enemy2.number = 10;
			enemy2.bulletspeed = 100;
			enemy3.bulletspeed = 100;
			enemy3.range = 10;
		}

		cleardevice();
		loadimage(&img[7], "resource\\map4.jpg", 500, 360);
		putimage(0, 0, &img[7]);
		break;
	case '3':
		e2 = 4;
		enemy1.bulletspeed = 100;
		enemy1.number = 50;
		enemy3.number = 50;
		enemy2.number = 50;
		enemy2.bulletspeed = 100;
		enemy3.bulletspeed = 100;
		enemy3.range = 10;
		enemy1.range = 10;
		enemy2.range = 10;
		enemy1.blood = 2;
		loadimage(&img[2], "resource\\空白模式.jpg", 640, 480);
		putimage(0, 0, &img[2]);
		copymap(map, empty);
		Sleep(1000);
		cleardevice();
		loadimage(&img[3], "resource\\empty.jpg", 500, 360);
		putimage(0, 0, &img[3]);
		break;
	default:        //返回主菜单界面
		start();
		break;
	}
	//坦克位置初始化
	map[16][10] = 5;
	map[1][1] = 6;
	map[1][11] = 8;
	map[1][23] = 9;
	loadimage(&image2, "resource\\mytop.bmp", 20, 20);
	putimage(200, 320, &image2);
	loadimage(&image3, "resource\\enemy1down.bmp", 20, 20);
	putimage(20, 20, &image3);
	loadimage(&image4, "resource\\enemy2down.bmp", 20, 20);
	putimage(220, 20, &image4);
	loadimage(&image5, "resource\\enemy3down.bmp", 20, 20);
	putimage(460, 20, &image5);



	tanknum = enemy1.number + enemy2.number + enemy3.number;
	char a[10];
	settextcolor(RED);
	settextstyle(16, 0, _T("黑体"));
	_stprintf_s(a, _T("%d"), enemy1.number);
	outtextxy(510, 50, "enemy1.number:");
	outtextxy(560, 80, a);
	_stprintf_s(a, _T("%d"), enemy2.number);
	outtextxy(510, 100, "enemy2.number:");
	outtextxy(560, 130, a);
	_stprintf_s(a, _T("%d"), enemy3.number);
	outtextxy(510, 150, "enemy3.number:");
	outtextxy(560, 180, a);
	_stprintf_s(a, _T("%d"), mytank.blood);
	outtextxy(510, 200, "mytank.blood:");
	outtextxy(560, 230, a);
	outtextxy(510, 270, "  我的得分");
	_stprintf_s(a, _T("%d"), sores);
	outtextxy(560, 300, a);

	int move, dir, x, y;
	enemy3dir = enemy3.dir;
	enemy2dir = enemy2.dir;
	enemy1dir = enemy1.dir;

	clock_t now, then;
	//坦克移动
	enemy1m1 = enemy2m1 = enemy3m1 = clock();


	while (mytank.blood != 0)
	{
		h1 = ::CreateThread(NULL, 0, myfun1, NULL, 0, NULL);   //创建线程  

		if (_kbhit() != 0)
		{
			switch (_getch())
			{
			case 'W':
			case 'w':
				move = 1;
				mytankmove(move);
				break;
			case 'S':
			case 's':
				move = 3;
				mytankmove(move);
				break;
			case 'A':
			case 'a':
				move = 2;
				mytankmove(move);
				break;
			case 'D':
			case 'd':
				move = 4;
				mytankmove(move);
				break;
			case 'I':
			case 'i':
				kind2 = 1;
				now = clock();
				k = 1;
				dir = mytank.dir;
				x = mytank.po_x;
				y = mytank.po_y;
				//判断子弹的方向
				if (dir == 1) x--;
				if (dir == 2) y--;
				if (dir == 3) x++;
				if (dir == 4) y++;
				shoot(mytank.kind, mytank.dir, x, y, mytank.bulletspeed, &kind2);
				break;
			case 'P':
			case 'p':
				fun = 0;
				getimage(&temp, 0, 0, 640, 480);
				pause();
				break;
			case 27:
				goto labelout;//退出游戏
				break;
			default:break;
			}
		}
		else
		{
			//判断子弹的方向
			if (dir == 1) x--;
			if (dir == 2) y--;
			if (dir == 3) x++;
			if (dir == 4) y++;
			if (kind2 == 1 && ((then = clock() - now) >= k*mytank.bulletspeed))
			{
				shoot(mytank.kind, dir, x, y, mytank.bulletspeed, &kind2);
				k++;
			}
			if (k == mytank.range)
			{
				kind2 = 0;
				k = 0;
			}
		}

		h2 = ::CreateThread(NULL, 0, myfun2, NULL, 0, NULL);  //线程2
		if (fun == 1)
		{
			if (enemy1m2 = clock() - enemy1m1 > 2000) autoenemy(e1);
			if (enemy2m2 = clock() - enemy2m1 > 1500) autoenemy(e2);
			if (enemy3m2 = clock() - enemy3m1 > 1500) autoenemy(e3);
			//判断子弹的方向
			if (enemy1kind == 1 && (enemy1shoot2 = clock() - enemy1shoot1 >= k1*enemy1.bulletspeed))
			{
				if (enemy1dir == 1) enemy1x--;
				if (enemy1dir == 2) enemy1y--;
				if (enemy1dir == 3) enemy1x++;
				if (enemy1dir == 4) enemy1y++;
				shoot(enemy1.kind, enemy1dir, enemy1x, enemy1y, enemy1.bulletspeed, &enemy1kind);
				k1++;
			}
			if (k1 == enemy1.range)
			{
				enemy1kind = 0;
				k1 = 0;
			}

			if (enemy2kind == 1 && (enemy2shoot2 = clock() - enemy2shoot1 >= k2*enemy2.bulletspeed))
			{
				if (enemy2dir == 1) enemy2x--;
				if (enemy2dir == 2) enemy2y--;
				if (enemy2dir == 3) enemy2x++;
				if (enemy2dir == 4) enemy2y++;
				shoot(enemy2.kind, enemy2dir, enemy2x, enemy2y, enemy2.bulletspeed, &enemy2kind);
				k2++;
			}
			if (k2 == enemy2.range)
			{
				enemy2kind = 0;
				k2 = 0;
			}
			if (enemy3kind == 1 && (enemy3shoot2 = clock() - enemy3shoot1 >= k3*enemy3.bulletspeed))
			{
				if (enemy3dir == 1) enemy3x--;
				if (enemy3dir == 2) enemy3y--;
				if (enemy3dir == 3) enemy3x++;
				if (enemy3dir == 4) enemy3y++;
				shoot(enemy3.kind, enemy3dir, enemy3x, enemy3y, enemy3.bulletspeed, &enemy3kind);
				k3++;
			}
			if (k3 == enemy3.range)
			{
				enemy3kind = 0;
				k3 = 0;
			}
		}
	}

	::CloseHandle(h1);                                     //关闭线程句柄对象  
	::CloseHandle(h2);
    labelout:start(); 
}

//排行榜
void raking()
{
	cleardevice();
	IMAGE img;
	loadimage(&img, "resource\\raking.jpg", 640, 480);
	putimage(0, 0, &img);

	char a[10];
	FILE *fp;
	int k, top[5];
	settextcolor(LIGHTGREEN);
	fopen_s(&fp, "record.txt", "r");
	for (k = 0; k<5; k++)
		fscanf_s(fp, "%d", &top[k]);
	_stprintf_s(a, _T("%d"), top[0]);
	outtextxy(268, 41, a);
	_stprintf_s(a, _T("%d"), top[1]);
	outtextxy(300, 129, a);
	_stprintf_s(a, _T("%d"), top[2]);
	outtextxy(310, 235, a);
	_stprintf_s(a, _T("%d"), top[3]);
	outtextxy(292, 336, a);
	_stprintf_s(a, _T("%d"), top[4]);
	outtextxy(273, 425, a);

	switch (_getch())
	{
	case '2':
		gameout();
		break; // 按下 2 退出游戏  
	default:
		start();
		break;  //按除 2以外的任意键 回到开始界面
	}
}

//游戏帮助
void help()
{
	cleardevice();
	IMAGE img1;
	loadimage(&img1, "resource\\help.jpg", 640, 480);
	putimage(0, 0, &img1);
	_getch();
	start();
}

//退出游戏
void gameout()
{

	cleardevice();
	IMAGE img;
	loadimage(&img, "resource\\gameout.jpg", 640, 480);
	putimage(0, 0, &img);
	switch (_getch())
	{
	case'2':
		closegraph();
		break;
	default:
		start();
		break;  //按除 2 以外任意键 回到主菜单
	}
}

//坦克初始化
void original_tank()
{
	mytank.po_x = 16;
	mytank.po_y = 10;
	mytank.bulletspeed = 300;
	mytank.dir = 1;
	mytank.blood = 1;
	mytank.number = 1;
	mytank.range = 8;

	enemy1.po_x = 1;
	enemy1.po_y = 1;
	enemy1.bulletspeed = 300;
	enemy1.dir = 3;
	enemy1.blood = 1;
	enemy1.number = 5;
	enemy1.range = 8;


	enemy2.po_x = 1;
	enemy2.po_y = 11;
	enemy2.bulletspeed = 200;
	enemy2.dir = 3;
	enemy2.blood = 2;
	enemy2.number = 6;
	enemy2.range = 9;


	enemy3.po_x = 1;
	enemy3.po_y = 23;
	enemy3.bulletspeed = 200;
	enemy3.dir = 3;
	enemy3.blood = 3;
	enemy3.number = 7;
	enemy3.range = 9;
}

//地图初始化
void original_map()
{
	int i, j;
	//地图1
	for (i = 0; i < 25; i++)             //水边界
		map1[0][i] = map1[17][i] = 2;
	for (j = 1; j < 17; j++)
		map1[j][0] = map1[j][24] = 2;

	//不可击穿的铁墙
	map1[3][1] = map1[3][2] = map1[3][11] = map1[3][12] = map1[3][13] = map1[3][22] = map1[3][23] = 2;
	map1[8][18] = map1[8][19] = map1[8][20] = map1[8][21] = map1[8][3] = map1[8][4] = map1[8][5] = 2;
	map1[6][7] = map1[6][8] = map1[6][9] = map1[6][10] = 2;

	//可击穿的墙
	for (i = 9; i < 13; i++)
		for (j = 1; j < 24; j++)
			map1[i][j] = 1;
	for (i = 13; i < 17; i++)
		for (j = 3; j < 5; j++)
			map1[i][j] = 1;
	map1[10][3] = 11;                     //加血
	map1[10][22] = 13;                    //加子弹速度
	map1[10][13] = 12;              //加子弹射程
									// 老巢
	map1[15][11] = map1[15][12] = map1[15][13] = map1[16][11] = map1[16][13] = 1;
	map1[16][12] = 3;


	//map2 以我的名字为模板的地图
	for (i = 0; i < 25; i++)             //水铁混合边界
		map2[0][i] = map2[17][i] = 2;
	for (j = 1; j < 17; j++)
		map2[j][0] = map2[j][24] = 2;
	//老巢
	for (i = 14; i <= 16; i++)
		for (j = 11; j <= 13; j++)
			map2[i][j] = 1;
	map2[16][12] = 3;
	//可击穿的墙
	for (i = 4; i < 11; i++)
		map2[4][i] = map2[12][i] = 1;
	for (j = 4; j < 13; j++)
		map2[j][14] = map2[j][15] = map2[j][22] = map2[j][23] = 1;
	map2[5][10] = map2[7][8] = map2[9][6] = map2[11][4] = 1;
	map2[6][17] = map2[6][18] = map2[8][19] = map2[9][19] = map2[11][20] = map2[11][21] = 1;
	//奖励
	map2[9][19] = 11;
	map2[6][18] = 12;
	map2[7][8] = 13;
	//不可击穿的铁墙和水
	map2[3][1] = map2[3][2] = map2[3][3] = map2[2][12] = map2[3][12] = map2[9][11] = 2;
	map2[9][12] = map2[9][13] = map2[15][20] = map2[15][4] = map2[14][4] = map2[14][20] = 2;
	map2[8][7] = map2[6][9] = map2[10][5] = map2[5][16] = map2[3][20] = map2[5][17] = 2;
	map2[3][21] = map2[7][18] = map2[7][19] = map2[10][19] = map2[10][20] = 2;


	//map3 以学校简称为模板
	for (i = 0; i < 25; i++)             //水铁混合边界
		map3[0][i] = map3[17][i] = 2;
	for (j = 1; j < 17; j++)
		map3[j][0] = map3[j][24] = 2;
	// 老巢
	map3[15][11] = map3[15][12] = map3[15][13] = map3[16][11] = map3[16][13] = 1;
	map3[16][12] = 3;
	//可击穿的墙
	for (i = 1; i < 17; i++)
		map3[i][2] = map3[i][6] = map3[i][19] = map3[i][20] = 1;
	for (j = 1; j < 14; j++)
		map3[j][8] = map3[j][11] = 1;
	for (j = 16; j < 23; j++)
		map3[1][j] = 1;
	for (i = 3; i < 6; i++)
		map3[7][i] = map3[9][i] = 1;
	map3[11][9] = map3[11][10] = map3[13][9] = map3[13][10] = map3[2][15] = 1;
	map3[2][16] = map3[3][14] = map3[3][15] = map3[4][14] = map3[6][14] = 1;
	map3[7][14] = map3[7][15] = map3[8][15] = map3[8][16] = map3[9][16] = 1;
	map3[11][16] = map3[12][16] = map3[12][15] = map3[12][14] = map3[13][14] = map3[13][13] = 1;
	//奖励
	map3[8][16] = 11;
	map3[4][14] = 12;
	map3[13][13] = 13;

	//不可击穿的铁,水
	map3[13][1] = map3[8][3] = map3[8][4] = map3[8][5] = map3[12][9] = map3[12][10] = 2;
	map3[13][12] = map3[4][13] = map3[5][13] = map3[6][13] = map3[9][17] = map3[10][17] = 2;
	map3[11][17] = map3[2][20] = map3[3][20] = map3[14][19] = map3[15][19] = 2;


	//map4为网格式地图
	for (i = 0; i < 25; i++)             //水铁混合边界
		map4[0][i] = map4[17][i] = 2;
	for (j = 1; j < 17; j++)
		map4[j][0] = map4[j][24] = 2;
	//老巢
	for (i = 14; i <= 16; i++)
		for (j = 11; j <= 13; j++)
			map4[i][j] = 1;
	map4[16][12] = 3;
	//可击穿的墙
	for (i = 1; i < 15; i++)
		map4[i][4] = 1;
	for (j = 1; j < 17; j++)
		map4[j][6] = map4[j][8] = map4[j][15] = map4[j][22] = 1;
	for (i = 1; i < 16; i++)
		map4[i][10] = 1;
	for (i = 1; i < 7; i++)
		map4[i][19] = 1;
	for (j = 6; j < 10; j++)
		map4[j][20] = 1;
	for (i = 9; i < 17; i++)
		map4[i][21] = 1;
	for (i = 1; i < 24; i++)
		map4[4][i] = map4[7][i] = map4[13][i] = 1;
	//奖励
	map4[12][4] = 11;
	map4[15][15] = 12;
	map4[6][19] = 13;
	//不可击穿的铁墙
	map4[3][1] = map4[3][2] = map4[3][3] = map4[2][12] = map4[3][12] = map4[9][11] = 2;
	map4[9][12] = map4[9][13] = map4[15][20] = map4[15][4] = map4[14][4] = map4[14][20] = 2;
	map4[8][7] = map4[6][9] = map4[10][5] = map4[5][16] = map4[3][20] = map4[5][17] = 2;
	map4[3][21] = map4[7][18] = map4[7][19] = map4[10][19] = map4[10][20] = map4[12][14] = map4[12][16] = 2;



	//map5 铁墙专区
	map5[16][12] = 3;
	for (i = 0; i < 25; i++)             //边界
		map5[0][i] = map5[17][i] = 2;
	for (j = 1; j < 17; j++)
		map5[j][0] = map5[j][24] = 2;

	//隐藏的奖励
	map5[16][1] = 11; map5[15][1] = 12; map5[14][1] = 13;

	//铁墙
	for (i = 1; i < 6; i++)
		map5[2][i] = map5[4][i + 1] = map5[i + 8][6] = map5[9][i + 16] = map5[i + 2][11] = 2;
	for (j = 1; j < 9; j++)
		map5[6][j] = 2;
	for (j = 1; j < 5; j++)
		map5[j][7] = map5[j + 5][9] = map5[j + 4][21] = map5[j + 2][19] = 2;
	for (i = 1; i < 4; i++)
		map5[i][13] = map5[i + 12][9] = map5[i + 6][16] = map5[i + 10][20] = map5[i][21] = 2;
	map5[9][7] = map5[9][8] = map5[13][7] = map5[13][8] = map5[3][12] = 2;
	map5[5][22] = map5[5][23] = map5[4][23] = map5[2][20] = map5[3][20] = 2;
	map5[13][17] = map5[13][19] = map5[13][18] = map5[14][17] = map5[15][17] = 2;
	for (i = 10; i < 17; i++)
		map5[11][i] = 2;

	//map6  水专区
	copymap(map6, map5);


	//空白模式地图
	for (i = 0; i < 25; i++)             //边界
		empty[0][i] = empty[17][i] = 2;
	for (j = 1; j < 17; j++)
		empty[j][0] = empty[j][24] = 2;
}

//地图选择
void mapchoose(int choosemap)
{
	switch (choosemap)
	{
	case 1:copymap(map, map1); break;
	case 2:copymap(map, map2); break;
	case 3:copymap(map, map3); break;
	case 4:copymap(map, map4); break;
	case 5:copymap(map, map5); break;
	case 6:copymap(map, map6); break;
	default:break;
	}
}

//复制地图（把b图复制到a图上）
void copymap(int a[18][25], int b[18][25])
{

	int i, j;
	for (i = 0; i < 18; i++)
		for (j = 0; j < 25; j++)
			a[i][j] = b[i][j];
}

//我方坦克移动
void mytankmove(int movedir)
{
	int  i, j;
	char a[10];
	IMAGE img[2];
	i = mytank.po_x;
	j = mytank.po_y;
	if (movedir == 1)
	{
		if (movedir == mytank.dir && (map[i - 1][j] == 0 || map[i - 1][j] == 14 || map[i - 1][j] == 15 || map[i - 1][j] == 16))
		{
			if (map[i - 1][j] == 14)
			{
				mytank.blood++;
				_stprintf_s(a, _T("%d"), mytank.blood);
				outtextxy(560, 230, a);
			}
			if (map[i - 1][j] == 15)mytank.range++;
			if (map[i - 1][j] == 16)mytank.bulletspeed++;
			map[i][j] = 0;
			mytank.po_x--;
			map[i - 1][j] = 5;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\mytop.bmp", 20, 20);
			putimage(j * 20, (i - 1) * 20, &img[1]);
		}
		if (mytank.dir != 1)
		{
			mytank.dir = movedir;
			loadimage(&img[1], "resource\\mytop.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 2)
	{
		if (movedir == mytank.dir && (map[i][j - 1] == 0 || map[i][j - 1] == 14 || map[i][j - 1] == 15 || map[i][j - 1] == 16))
		{
			if (map[i][j - 1] == 14)
			{
				mytank.blood++;
				_stprintf_s(a, _T("%d"), mytank.blood);
				outtextxy(560, 230, a);
			}
			if (map[i][j - 1] == 15)mytank.range++;
			if (map[i][j - 1] == 16)mytank.bulletspeed++;
			mytank.po_y--;
			map[i][j] = 0;
			map[i][j - 1] = 5;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\myleft.bmp", 20, 20);
			putimage((j - 1) * 20, i * 20, &img[1]);
		}
		if (mytank.dir != 2)
		{
			mytank.dir = movedir;
			loadimage(&img[1], "resource\\myleft.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 3)
	{
		if (movedir == mytank.dir && (map[i + 1][j] == 0 || map[i + 1][j] == 14 || map[i + 1][j] == 15 || map[i + 1][j] == 16))
		{
			if (map[i + 1][j] == 14)
			{
				mytank.blood++;
				_stprintf_s(a, _T("%d"), mytank.blood);
				outtextxy(560, 230, a);
			}
			if (map[i + 1][j] == 15)mytank.range++;
			if (map[i + 1][j] == 16)mytank.bulletspeed++;
			mytank.po_x++;
			map[i][j] = 0;
			map[i + 1][j] = 5;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\mydown.bmp", 20, 20);
			putimage(j * 20, (i + 1) * 20, &img[1]);
		}
		if (mytank.dir != 3)
		{
			mytank.dir = movedir;
			loadimage(&img[1], "resource\\mydown.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 4)
	{
		if (movedir == mytank.dir && (map[i][j + 1] == 0 || map[i][j + 1] == 14 || map[i][j + 1] == 15 || map[i][j + 1] == 16))
		{
			if (map[i][j + 1] == 14)
			{
				mytank.blood++;
				_stprintf_s(a, _T("%d"), mytank.blood);
				outtextxy(560, 230, a);
			}
			if (map[i][j + 1] == 15)mytank.range++;
			if (map[i][j + 1] == 16)mytank.bulletspeed++;
			mytank.po_y++;
			map[i][j] = 0;
			map[i][j + 1] = 5;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\myright.bmp", 20, 20);
			putimage((j + 1) * 20, i * 20, &img[1]);
		}
		if (mytank.dir != 4)
		{
			mytank.dir = movedir;
			loadimage(&img[1], "resource\\myright.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}
}

//敌方坦克移动
void enemy1tankmove(int movedir)             //敌方坦克  1  移动
{
	int  i, j;
	IMAGE img[2];
	i = enemy1.po_x;
	j = enemy1.po_y;
	if (movedir == 1)
	{
		if (movedir == enemy1.dir && (map[i - 1][j] == 0 || map[i - 1][j] == 14 || map[i - 1][j] == 15 || map[i - 1][j] == 16))
		{
			if (map[i - 1][j] == 14)enemy1.blood++;
			if (map[i - 1][j] == 15)enemy1.range++;
			if (map[i - 1][j] == 16)enemy1.bulletspeed++;
			map[i][j] = 0;
			enemy1.po_x--;
			map[i - 1][j] = 6;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy1up.bmp", 20, 20);
			putimage(j * 20, (i - 1) * 20, &img[1]);
		}
		if (enemy1.dir != 1)
		{
			enemy1.dir = movedir;
			loadimage(&img[1], "resource\\enemy1up.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 2)
	{
		if (movedir == enemy1.dir && (map[i][j - 1] == 0 || map[i][j - 1] == 14 || map[i][j - 1] == 15 || map[i][j - 1] == 16))
		{
			if (map[i][j - 1] == 14)enemy1.blood++;
			if (map[i][j - 1] == 15)enemy1.range++;
			if (map[i][j - 1] == 16)enemy1.bulletspeed++;
			enemy1.po_y--;
			map[i][j] = 0;
			map[i][j - 1] = 6;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy1left.bmp", 20, 20);
			putimage((j - 1) * 20, i * 20, &img[1]);
		}
		if (enemy1.dir != 2)
		{
			enemy1.dir = movedir;
			loadimage(&img[1], "resource\\enemy1left.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 3)
	{
		if (movedir == enemy1.dir && (map[i + 1][j] == 0 || map[i + 1][j] == 14 || map[i + 1][j] == 15 || map[i + 1][j] == 16))
		{
			if (map[i + 1][j] == 14)enemy1.blood++;
			if (map[i + 1][j] == 15)enemy1.range++;
			if (map[i + 1][j] == 16)enemy1.bulletspeed++;
			enemy1.po_x++;
			map[i][j] = 0;
			map[i + 1][j] = 6;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy1down.bmp", 20, 20);
			putimage(j * 20, (i + 1) * 20, &img[1]);
		}
		if (enemy1.dir != 3)
		{
			enemy1.dir = movedir;
			loadimage(&img[1], "resource\\enemy1down.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 4)
	{
		if (movedir == enemy1.dir && (map[i][j + 1] == 0 || map[i][j + 1] == 14 || map[i][j + 1] == 15 || map[i][j + 1] == 16))
		{
			if (map[i][j + 1] == 14)enemy1.blood++;
			if (map[i][j + 1] == 15)enemy1.range++;
			if (map[i][j + 1] == 16)enemy1.bulletspeed++;
			enemy1.po_y++;
			map[i][j] = 0;
			map[i][j + 1] = 6;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy1right.bmp", 20, 20);
			putimage((j + 1) * 20, i * 20, &img[1]);
		}
		if (enemy1.dir != 4)
		{
			enemy1.dir = movedir;
			loadimage(&img[1], "resource\\enemy1right.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}
}
void enemy2tankmove(int movedir)                       //敌方坦克  2  移动
{
	int  i, j;
	IMAGE img[2];
	i = enemy2.po_x;
	j = enemy2.po_y;
	if (movedir == 1)
	{
		if (movedir == enemy2.dir && (map[i - 1][j] == 0 || map[i - 1][j] == 14 || map[i - 1][j] == 15 || map[i - 1][j] == 16))
		{
			if (map[i - 1][j] == 14)enemy2.blood++;
			if (map[i - 1][j] == 15)enemy2.range++;
			if (map[i - 1][j] == 16)enemy2.bulletspeed++;
			map[i][j] = 0;
			enemy2.po_x--;
			map[i - 1][j] = 8;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy2up.bmp", 20, 20);
			putimage(j * 20, (i - 1) * 20, &img[1]);
		}
		if (enemy2.dir != 1)
		{
			enemy2.dir = movedir;
			loadimage(&img[1], "resource\\enemy2up.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 2)
	{
		if (movedir == enemy2.dir && (map[i][j - 1] == 0 || map[i][j - 1] == 14 || map[i][j - 1] == 15 || map[i][j - 1] == 16))
		{
			if (map[i][j - 1] == 14)enemy2.blood++;
			if (map[i][j - 1] == 15)enemy2.range++;
			if (map[i][j - 1] == 16)enemy2.bulletspeed++;
			enemy2.po_y--;
			map[i][j] = 0;
			map[i][j - 1] = 8;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy2left.bmp", 20, 20);
			putimage((j - 1) * 20, i * 20, &img[1]);
		}
		if (enemy2.dir != 2)
		{
			enemy2.dir = movedir;
			loadimage(&img[1], "resource\\enemy2left.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 3)
	{
		if (movedir == enemy2.dir && (map[i + 1][j] == 0 || map[i + 1][j] == 14 || map[i + 1][j] == 15 || map[i + 1][j] == 16))
		{
			if (map[i + 1][j] == 14)enemy2.blood++;
			if (map[i + 1][j] == 15)enemy2.range++;
			if (map[i + 1][j] == 16)enemy2.bulletspeed++;
			enemy2.po_x++;
			map[i][j] = 0;
			map[i + 1][j] = 8;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy2down.bmp", 20, 20);
			putimage(j * 20, (i + 1) * 20, &img[1]);
		}
		if (enemy2.dir != 3)
		{
			enemy2.dir = movedir;
			loadimage(&img[1], "resource\\enemy2down.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 4)
	{
		if (movedir == enemy2.dir && (map[i][j + 1] == 0 || map[i][j + 1] == 14 || map[i][j + 1] == 15 || map[i][j + 1] == 16))
		{
			if (map[i][j + 1] == 14)enemy2.blood++;
			if (map[i][j + 1] == 15)enemy2.range++;
			if (map[i][j + 1] == 16)enemy2.bulletspeed++;
			enemy2.po_y++;
			map[i][j] = 0;
			map[i][j + 1] = 8;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy2right.bmp", 20, 20);
			putimage((j + 1) * 20, i * 20, &img[1]);
		}
		if (enemy2.dir != 4)
		{
			enemy2.dir = movedir;
			loadimage(&img[1], "resource\\enemy2right.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}
}
void enemy3tankmove(int movedir)                         //敌方坦克  3  移动
{
	int  i, j;
	IMAGE img[2];
	i = enemy3.po_x;
	j = enemy3.po_y;
	if (movedir == 1)
	{
		if (movedir == enemy3.dir && (map[i - 1][j] == 0 || map[i - 1][j] == 14 || map[i - 1][j] == 15 || map[i - 1][j] == 16))
		{
			if (map[i - 1][j] == 14)enemy3.blood++;
			if (map[i - 1][j] == 15)enemy3.range++;
			if (map[i - 1][j] == 16)enemy3.bulletspeed++;
			map[i][j] = 0;
			enemy3.po_x--;
			map[i - 1][j] = 9;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy3up.bmp", 20, 20);
			putimage(j * 20, (i - 1) * 20, &img[1]);
		}
		if (enemy3.dir != 1)
		{
			enemy3.dir = movedir;
			loadimage(&img[1], "resource\\enemy3up.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 2)
	{
		if (movedir == enemy3.dir && (map[i][j - 1] == 0 || map[i][j - 1] == 14 || map[i][j - 1] == 15 || map[i][j - 1] == 16))
		{
			if (map[i][j - 1] == 14)enemy3.blood++;
			if (map[i][j - 1] == 15)enemy3.range++;
			if (map[i][j - 1] == 16)enemy3.bulletspeed++;
			enemy3.po_y--;
			map[i][j] = 0;
			map[i][j - 1] = 9;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy3left.bmp", 20, 20);
			putimage((j - 1) * 20, i * 20, &img[1]);
		}
		if (enemy3.dir != 2)
		{
			enemy3.dir = movedir;
			loadimage(&img[1], "resource\\enemy3left.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 3)
	{
		if (movedir == enemy3.dir && (map[i + 1][j] == 0 || map[i + 1][j] == 14 || map[i + 1][j] == 15 || map[i + 1][j] == 16))
		{
			if (map[i + 1][j] == 14)enemy3.blood++;
			if (map[i + 1][j] == 15)enemy3.range++;
			if (map[i + 1][j] == 16)enemy3.bulletspeed++;
			enemy3.po_x++;
			map[i][j] = 0;
			map[i + 1][j] = 9;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy3down.bmp", 20, 20);
			putimage(j * 20, (i + 1) * 20, &img[1]);
		}
		if (enemy3.dir != 3)
		{
			enemy3.dir = movedir;
			loadimage(&img[1], "resource\\enemy3down.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}

	if (movedir == 4)
	{
		if (movedir == enemy3.dir && (map[i][j + 1] == 0 || map[i][j + 1] == 14 || map[i][j + 1] == 15 || map[i][j + 1] == 16))
		{
			if (map[i][j + 1] == 14)enemy3.blood++;
			if (map[i][j + 1] == 15)enemy3.range++;
			if (map[i][j + 1] == 16)enemy3.bulletspeed++;
			enemy3.po_y++;
			map[i][j] = 0;
			map[i][j + 1] = 9;
			loadimage(&img[0], "resource\\blackground.jpg", 20, 20);
			putimage(j * 20, i * 20, &img[0]);
			loadimage(&img[1], "resource\\enemy3right.bmp", 20, 20);
			putimage((j + 1) * 20, i * 20, &img[1]);
		}
		if (enemy3.dir != 4)
		{
			enemy3.dir = movedir;
			loadimage(&img[1], "resource\\enemy3right.bmp", 20, 20);
			putimage(j * 20, i * 20, &img[1]);
		}
	}
}

//射击
void shoot(int kind, int dir, int po_x, int po_y, int speed, int *k)
{
	IMAGE img1, img2, img3, img4, img5;
	int blood;
	int x, y;
	x = po_x;
	y = po_y;
	char a[10];
	int top5[5] = { 0 };
	if (map[x][y] == 1 || map[x][y] == 14 || map[x][y] == 15 || map[x][y] == 16)  //撞到 可击穿的砖
	{
		loadimage(&img1, "resource\\explode.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		Sleep(100);
		loadimage(&img2, "resource\\blackground.jpg", 20, 20);
		putimage(y * 20, x * 20, &img2);
		map[x][y] = 0;
		*k = 0;
	}
	if (map[x][y] == 11)
	{
		loadimage(&img1, "resource\\explode.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		Sleep(100);
		loadimage(&img2, "resource\\blood.bmp", 20, 20);
		putimage(y * 20, x * 20, &img2);
		*k = 0;
		map[x][y] = 14;
	}
	if (map[x][y] == 12)
	{
		loadimage(&img1, "resource\\explode.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		Sleep(100);
		loadimage(&img2, "resource\\range.bmp", 20, 20);
		putimage(y * 20, x * 20, &img2);
		*k = 0;
		map[x][y] = 15;
	}
	if (map[x][y] == 13)
	{
		loadimage(&img1, "resource\\explode.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		Sleep(100);
		loadimage(&img2, "resource\\speed.jpg", 20, 20);
		putimage(y * 20, x * 20, &img2);
		*k = 0;
		map[x][y] = 16;
	}
	if (map[x][y] == 7)  //子弹相遇
	{
		loadimage(&img1, "resource\\explode2.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		Sleep(100);
		loadimage(&img2, "resource\\blackground.jpg", 20, 20);
		putimage(y * 20, x * 20, &img2);
		map[x][y] = 0;
		*k = 0;
	}
	if ((map[x][y] == 6 || map[x][y] == 8 || map[x][y] == 9) && kind == 5)//打中敌方坦克
	{
		sores += 200;
		_stprintf_s(a, _T("%d"), sores);
		outtextxy(560, 300, a);
		*k = 0;
		if (map[x][y] == 6)
		{
			enemy1.blood--;
			blood = enemy1.blood;
			if (blood == 0)
			{
				map[x][y] = 0;
				loadimage(&img1, "resource\\explode2.bmp", 20, 20);
				putimage(y * 20, x * 20, &img1);
				Sleep(100);
				loadimage(&img2, "resource\\blackground.jpg", 20, 20);
				putimage(y * 20, x * 20, &img2);
				enemy1.number--;
				tanknum--;
				_stprintf_s(a, _T("%d"), enemy1.number);
				outtextxy(560, 80, a);
				if (enemy1.number > 0)
				{
					enemy1.blood = 1;
					enemy1.bulletspeed = 300;
					enemy1.dir = 3;
					enemy1.po_x = 1;
					enemy1.po_y = 1;
					enemy1.range = 8;
					loadimage(&img3, "resource\\enemy1down.bmp", 20, 20);
					putimage(20, 20, &img3);
					map[1][1] = 6;
				}
			}
		}
		if (map[x][y] == 8)
		{
			enemy2.blood--;
			blood = enemy2.blood;
			if (blood == 0)
			{
				map[x][y] = 0;
				loadimage(&img1, "resource\\explode2.bmp", 20, 20);
				putimage(y * 20, x * 20, &img1);
				Sleep(100);
				loadimage(&img2, "resource\\blackground.jpg", 20, 20);
				putimage(y * 20, x * 20, &img2);
				enemy2.number--;
				tanknum--;
				_stprintf_s(a, _T("%d"), tanknum);
				outtextxy(560, 130, a);
				if (enemy2.number>0)
				{
					enemy2.blood = 2;
					enemy2.bulletspeed = 200;
					enemy2.dir = 3;
					enemy2.po_x = 1;
					enemy2.po_y = 11;
					enemy2.range = 9;
					loadimage(&img4, "resource\\enemy2down.bmp", 20, 20);
					putimage(220, 20, &img4);
					map[1][11] = 8;
				}
			}
		}
		if (map[x][y] == 9)
		{
			enemy3.blood--;
			blood = enemy3.blood;
			if (blood == 0)
			{
				map[x][y] = 0;
				loadimage(&img1, "resource\\explode2.bmp", 20, 20);
				putimage(y * 20, x * 20, &img1);
				Sleep(100);
				loadimage(&img2, "resource\\blackground.jpg", 20, 20);
				putimage(y * 20, x * 20, &img2);
				enemy3.number--;
				tanknum--;
				_stprintf_s(a, _T("%d"), tanknum);
				outtextxy(560, 180, a);
				if (enemy3.number > 0)
				{
					enemy3.blood = 3;
					enemy3.bulletspeed = 200;
					enemy3.dir = 3;
					enemy3.po_x = 1;
					enemy3.po_y = 23;
					enemy3.range = 9;
					loadimage(&img5, "resource\\enemy3down.bmp", 20, 20);
					putimage(460, 20, &img5);
					map[1][23] = 9;
				}
			}
		}

	}
	if (map[x][y] == 3)          //游戏失败
	{
		record(sores);
		*k = 0;
		loadimage(&img1, "resource\\explode2.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		Sleep(1000);
		gamerepeat();
	}
	if (map[x][y] == 5)
	{
		mytank.blood--;
		_stprintf_s(a, _T("%d"), mytank.blood);
		outtextxy(560, 230, a);
		if (mytank.blood == 0)
		{
			fun = 0;
			record(sores);
			loadimage(&img1, "resource\\explode2.bmp", 20, 20);//游戏失败
			putimage(y * 20, x * 20, &img1);
			Sleep(1000);
			gamerepeat();
		}

	}
	if (map[x][y] == 2 || ((map[x][y] == 6 || map[x][y] == 8 || map[x][y] == 9) && kind != 5))
	{
		*k = 0;
	}
	if (map[x][y] == 0)
	{
		loadimage(&img1, "resource\\bullet.bmp", 20, 20);
		putimage(y * 20, x * 20, &img1);
		map[x][y] = 7;
		Sleep(speed);
		loadimage(&img2, "resource\\blackground.jpg", 20, 20);
		putimage(y * 20, x * 20, &img2);
		map[x][y] = 0;
	}
	if (enemy1.number == 0 && enemy2.number == 0 && enemy3.number == 0)
		through();

}

//游戏通过
void through()
{
	IMAGE img;
	fun = 0;
	mytank.blood = 0;
	cleardevice();
	loadimage(&img, "resource\\through.jpg", 640, 480);
	putimage(0, 0, &img);
	Sleep(1500);                //sleep1.5秒回到菜单界面
	start();
}

//游戏失败后是否重新游戏
void gamerepeat()
{
	fun = 0;
	mytank.blood = 0;
	IMAGE img1, img2;
	loadimage(&img2, "resource\\gameover.bmp", 640, 480);
	putimage(0, 0, &img2);
	Sleep(500);
	cleardevice();
	loadimage(&img1, "resource\\repeat.jpg", 640, 480);
	putimage(0, 0, &img1);

	switch (_getch())
	{
	case '1':gamestart(); //按下  1 游戏重新开始
		break;
	case '2':gameout();// 按下 2  游戏结束
		break;
	case '3':start();// 按下  3 游戏回到初始界面
		break;
	default:
		gameout();
		break;
	}
	sores = 0;
}

//暂停
void pause()
{
	IMAGE img1, img2;
	cleardevice();
	loadimage(&img1, "resource\\pause.jpg", 640, 480);
	putimage(0, 0, &img1);
	switch (_getch())
	{
	case '1':       //继续游戏
		fun = 1;
		cleardevice();
		putimage(0, 0, &temp);
		break;
	case '2':      //退出游戏
		fun = 0;//结束线程2
		mytank.blood = 0;//结束while循环
		loadimage(&img1, "resource\\out.jpg", 640, 480);
		putimage(0, 0, &img1);
		if (_getch() == '1')
			record(sores);
		else
			gameout();
		break;
	case '3':     
		fun = 0;//结束线程2
		mytank.blood = 0;//结束while循环
		record(sores);
		gamestart();
		break;
	case '4':
		fun = 0;//结束线程2
		mytank.blood = 0;
		record(sores);
		start();
		break;
	default:
		break;
	}
}

//坦克智能化移动和智能化射击
void autoenemy(int e)
{
	int dir;
	int x, y;
	if (e == 1)
	{
		//enemy1的 移动和射击
		if (enemy1.po_x > mytank.po_x && (map[enemy1.po_x - 1][enemy1.po_y] == 0 || map[enemy1.po_x - 1][enemy1.po_y] > 13))
		{
			enemy1tankmove(1);
			Sleep(100);
			enemy1tankmove(1);
			if (map[enemy1.po_x - 1][enemy1.po_y] == 1 || (map[enemy1.po_x - 1][enemy1.po_y] >= 11 && map[enemy1.po_x - 1][enemy1.po_y] <= 13))
			{
				enemy1kind = 1;
				k1 = 1;
				shoot(enemy1.kind, enemy1.dir, enemy1.po_x - 1, enemy1.po_y, enemy1.bulletspeed, &enemy1kind);
			}

		}
		if (enemy1.po_x < mytank.po_x && (map[enemy1.po_x + 1][enemy1.po_y] == 0 || map[enemy1.po_x + 1][enemy1.po_y] > 13))
		{
			enemy1tankmove(3);
			Sleep(100);
			enemy1tankmove(3);
			if (map[enemy1.po_x + 1][enemy1.po_y] == 1 || (map[enemy1.po_x + 1][enemy1.po_y] >= 11 && map[enemy1.po_x + 1][enemy1.po_y] <= 13))
			{
				enemy1kind = 1;
				k1 = 1;
				shoot(enemy1.kind, enemy1.dir, enemy1.po_x + 1, enemy1.po_y, enemy1.bulletspeed, &enemy1kind);
			}

		}
		if (enemy1.po_x == mytank.po_x)
		{
			x = enemy1.po_x;
			y = enemy1.po_y;
			if (enemy1.po_y > mytank.po_y)
			{
				y--;
				enemy1tankmove(2);
			}
			if (enemy1.po_y < mytank.po_y)
			{
				y++;
				enemy1tankmove(4);
			}
			enemy1kind = 1;
			k1 = 1;
			enemy1dir = dir = enemy1.dir;
			enemy1x = enemy1.po_x;
			enemy1y = enemy1.po_y;
			shoot(enemy1.kind, dir, x, y, enemy1.bulletspeed, &enemy1kind);
			enemy1shoot1 = clock();
		}
		if (enemy1.po_y > mytank.po_y && (map[enemy1.po_x][enemy1.po_y - 1] == 0 || map[enemy1.po_x][enemy1.po_y - 1] > 13))
		{
			enemy1tankmove(2);
			Sleep(100);
			enemy1tankmove(2);
			if (map[enemy1.po_x][enemy1.po_y - 1] == 1 || (map[enemy1.po_x][enemy1.po_y - 1] >= 11 && map[enemy1.po_x][enemy1.po_y - 1] <= 13))
			{
				enemy1kind = 1;
				k1 = 1;
				shoot(enemy1.kind, enemy1.dir, enemy1.po_x, enemy1.po_y - 1, enemy1.bulletspeed, &enemy1kind);
			}
		}
		if (enemy1.po_y < mytank.po_y && (map[enemy1.po_x][enemy1.po_y + 1] == 0 || map[enemy1.po_x][enemy1.po_y + 1] > 13))
		{
			enemy1tankmove(4);
			Sleep(100);
			enemy1tankmove(4);
			if (map[enemy1.po_x][enemy1.po_y + 1] == 1 || (map[enemy1.po_x][enemy1.po_y + 1] >= 11 && map[enemy1.po_x][enemy1.po_y + 1] <= 13))
			{
				enemy1kind = 1;
				k1 = 1;
				shoot(enemy1.kind, enemy1.dir, enemy1.po_x, enemy1.po_y + 1, enemy1.bulletspeed, &enemy1kind);
			}
		}
		if (enemy1.po_y == mytank.po_y)
		{
			x = enemy1.po_x;
			y = enemy1.po_y;
			if (enemy1.po_x > mytank.po_x)
			{
				x--;
				enemy1tankmove(1);
			}
			if (enemy1.po_x < mytank.po_x)
			{
				x++;
				enemy1tankmove(3);
			}
			enemy1kind = 1;
			k1 = 1;
			enemy1dir = dir = enemy1.dir;
			enemy1x = enemy1.po_x;
			enemy1y = enemy1.po_y;
			shoot(enemy1.kind, dir, x, y, enemy1.bulletspeed, &enemy1kind);
			enemy1shoot1 = clock();
		}
		enemy1m1 = clock();
	}
	if (e == 2)
	{
		int ran;//任意1~4的数
		srand(time(0));
		ran = rand() % 4 + 1;
		enemy2tankmove(ran);
		enemy2tankmove(ran);    //两次移动以免很难动
		ran = rand() % 3;         //只有三分之一的机会能射击
		if (ran == 1)
		{
			enemy2kind = 1;
			k1 = 1;
			shoot(enemy2.kind, enemy2.dir, enemy2.po_x - 1, enemy2.po_y, enemy2.bulletspeed, &enemy2kind);
			enemy2shoot1 = clock();
		}
		enemy2m1 = clock();
	}
	if (e == 3)
	{
		//enemy3的 移动和射击
		if (enemy3.po_x > mytank.po_x && (map[enemy3.po_x - 1][enemy3.po_y] == 0 || map[enemy3.po_x - 1][enemy3.po_y] > 13))
		{
			enemy3tankmove(1);
			Sleep(100);
			enemy3tankmove(1);
			if (map[enemy3.po_x - 1][enemy3.po_y] == 1 || (map[enemy3.po_x - 1][enemy3.po_y] >= 11 && map[enemy3.po_x - 1][enemy3.po_y] <= 13))
			{
				enemy3kind = 1;
				k1 = 1;
				shoot(enemy3.kind, enemy3.dir, enemy3.po_x - 1, enemy3.po_y, enemy3.bulletspeed, &enemy3kind);
			}

		}
		if (enemy3.po_x < mytank.po_x && (map[enemy3.po_x + 1][enemy3.po_y] == 0 || map[enemy3.po_x + 1][enemy3.po_y] > 13))
		{
			enemy3tankmove(3);
			Sleep(100);
			enemy3tankmove(3);
			if (map[enemy3.po_x + 1][enemy3.po_y] == 1 || (map[enemy3.po_x + 1][enemy3.po_y] >= 11 && map[enemy3.po_x + 1][enemy3.po_y] <= 13))
			{
				enemy3kind = 1;
				k1 = 1;
				shoot(enemy3.kind, enemy3.dir, enemy3.po_x + 1, enemy3.po_y, enemy3.bulletspeed, &enemy3kind);
			}

		}
		if (enemy3.po_x == mytank.po_x)
		{
			x = enemy3.po_x;
			y = enemy3.po_y;
			if (enemy3.po_y > mytank.po_y)
			{
				y--;
				enemy3tankmove(2);
			}
			if (enemy3.po_y < mytank.po_y)
			{
				y++;
				enemy3tankmove(4);
			}
			enemy3kind = 1;
			k1 = 1;
			enemy3dir = dir = enemy3.dir;
			enemy3x = enemy3.po_x;
			enemy3y = enemy3.po_y;
			shoot(enemy3.kind, dir, x, y, enemy3.bulletspeed, &enemy3kind);
			enemy3shoot1 = clock();
		}
		if (enemy3.po_y > mytank.po_y && (map[enemy3.po_x][enemy3.po_y - 1] == 0 || map[enemy3.po_x][enemy3.po_y - 1] > 13))
		{
			enemy3tankmove(2);
			Sleep(100);
			enemy3tankmove(2);
			if (map[enemy3.po_x][enemy3.po_y - 1] == 1 || (map[enemy3.po_x][enemy3.po_y - 1] >= 11 && map[enemy3.po_x][enemy3.po_y - 1] <= 13))
			{
				enemy3kind = 1;
				k1 = 1;
				shoot(enemy3.kind, enemy3.dir, enemy3.po_x, enemy3.po_y - 1, enemy3.bulletspeed, &enemy3kind);
			}
		}
		if (enemy3.po_y < mytank.po_y && (map[enemy3.po_x][enemy3.po_y + 1] == 0 || map[enemy3.po_x][enemy3.po_y + 1] > 13))
		{
			enemy3tankmove(4);
			Sleep(100);
			enemy3tankmove(4);
			if (map[enemy3.po_x][enemy3.po_y + 1] == 1 || (map[enemy3.po_x][enemy3.po_y + 1] >= 11 && map[enemy3.po_x][enemy3.po_y + 1] <= 13))
			{
				enemy3kind = 1;
				k1 = 1;
				shoot(enemy3.kind, enemy3.dir, enemy3.po_x, enemy3.po_y + 1, enemy3.bulletspeed, &enemy3kind);
			}
		}
		if (enemy3.po_y == mytank.po_y)
		{
			x = enemy3.po_x;
			y = enemy3.po_y;
			if (enemy3.po_x > mytank.po_x)
			{
				x--;
				enemy3tankmove(1);
			}
			if (enemy3.po_x < mytank.po_x)
			{
				x++;
				enemy3tankmove(3);
			}
			enemy3kind = 1;
			k1 = 1;
			enemy3dir = dir = enemy3.dir;
			enemy3x = enemy3.po_x;
			enemy3y = enemy3.po_y;
			shoot(enemy3.kind, dir, x, y, enemy3.bulletspeed, &enemy3kind);
			enemy3shoot1 = clock();
		}
		enemy3m1 = clock();
	}
	if (e == 4)
	{
		//enemy2的 移动和射击
		if (enemy2.po_x > mytank.po_x && (map[enemy2.po_x - 1][enemy2.po_y] == 0 || map[enemy2.po_x - 1][enemy2.po_y] > 13))
		{
			enemy2tankmove(1);
			Sleep(100);
			enemy2tankmove(1);

		}
		if (enemy2.po_x < mytank.po_x && (map[enemy2.po_x + 1][enemy2.po_y] == 0 || map[enemy2.po_x + 1][enemy2.po_y] > 13))
		{
			enemy2tankmove(3);
			Sleep(100);
			enemy2tankmove(3);

		}
		if (enemy2.po_x == mytank.po_x)
		{
			x = enemy2.po_x;
			y = enemy2.po_y;
			if (enemy2.po_y > mytank.po_y)
			{
				y--;
				enemy2tankmove(2);
			}
			if (enemy2.po_y < mytank.po_y)
			{
				y++;
				enemy2tankmove(4);
			}
			enemy2kind = 1;
			k1 = 1;
			enemy2dir = dir = enemy2.dir;
			enemy2x = enemy2.po_x;
			enemy2y = enemy2.po_y;
			shoot(enemy2.kind, dir, x, y, enemy2.bulletspeed, &enemy2kind);
			enemy2shoot1 = clock();
		}
		if (enemy2.po_y > mytank.po_y && (map[enemy2.po_x][enemy2.po_y - 1] == 0 || map[enemy2.po_x][enemy2.po_y - 1] > 13))
		{
			enemy2tankmove(2);
			Sleep(100);
			enemy2tankmove(2);
		}
		if (enemy2.po_y < mytank.po_y && (map[enemy2.po_x][enemy2.po_y + 1] == 0 || map[enemy2.po_x][enemy2.po_y + 1] > 13))
		{
			enemy2tankmove(4);
			Sleep(100);
			enemy2tankmove(4);
		}
		if (enemy2.po_y == mytank.po_y)
		{
			x = enemy2.po_x;
			y = enemy2.po_y;
			if (enemy2.po_x > mytank.po_x)
			{
				x--;
				enemy2tankmove(1);
			}
			if (enemy2.po_x < mytank.po_x)
			{
				x++;
				enemy2tankmove(3);
			}
			enemy2kind = 1;
			k1 = 1;
			enemy2dir = dir = enemy2.dir;
			enemy2x = enemy2.po_x;
			enemy2y = enemy2.po_y;
			shoot(enemy2.kind, dir, x, y, enemy2.bulletspeed, &enemy2kind);
			enemy2shoot1 = clock();
		}
		enemy2m1 = clock();
	}
}

//线程函数
DWORD WINAPI myfun1(LPVOID lpParameter)                     //分别实现线程函数  
{
	return 0;
}
DWORD WINAPI myfun2(LPVOID lpParameter)
{
	return 0;
}

//记录排行榜（只记前五名）
void record(int sore)
{

	FILE *fp;
	int top[5], record[5] = { 0 };
	int k = 0, t = 6;
	fopen_s(&fp, "record.txt", "r");
	for (k = 0; k < 5; k++)
	{
		fscanf_s(fp, "%d", &top[k]);
		if (sore > top[k])
		{
			t = k;
			break;
		}
	}
	if (t < 5)
	{
		for (k = t; k < 4; k++)
			record[k] = top[k];
		top[t] = sore;
		for (k = t + 1; k < 5; k++)
			top[k] = record[k - 1];
	}
	fclose(fp);
	if (t < 5)
	{
		fopen_s(&fp, "record.txt", "w");
		for (k = 0; k < 5; k++)
		{
			fprintf(fp, "%d", top[k]);
			fprintf(fp, "%c", ' ');
		}
		fclose(fp);
	}
}