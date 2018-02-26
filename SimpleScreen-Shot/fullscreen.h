#ifndef FULLSCREEN_H
#define FULLSCREEN_H

#include <QWidget>
#include <QLabel>
#include <QDesktopWidget>
#include<QDesktopServices>
#include <QApplication>
#include <QPixmap>
#include <QBitmap>
#include <QApplication>
#include <QClipboard>
#include <QPainter>
#include <QPaintEvent>
#include <QMouseEvent>
#include <QKeyEvent>
#include <QPoint>
//文件操作
#include <QDir>
#include <QTime>
#include<QFileDialog>
#include<QFile>
#include<QDebug>
#include <QFont>
#include <math.h>
#include<QPushButton>
#include <QGraphicsDropShadowEffect>
#include <QGraphicsScene>
#include <QGraphicsView>
#include <QAction>
#include <QRegion>
#include "windows.h"
#include<QList>
#include<QCursor>

struct MyRect
{
    QRect myRect_;  //矩形
    int distance;   //鼠标当前点到 所有边的距离之和，用于比较
};

class FullScreen : public QLabel
{
    Q_OBJECT
public:

    //void calculateRectDistance(QRect rect);
    explicit FullScreen(QLabel *QLabel = 0);
    //初始化截屏方式以及相应数据
    FullScreen(int ind);
    ~FullScreen();
    //内置放大镜,为鼠标移动显示截屏之关键
    void paintEvent(QPaintEvent *event);
    //键盘鼠标响应
    void mousePressEvent(QMouseEvent *ev);
    void mouseMoveEvent(QMouseEvent *ev);
    void mouseReleaseEvent(QMouseEvent *ev);
    void mouseDoubleClickEvent(QMouseEvent *ev);
    void keyPressEvent(QKeyEvent *e);
    void intiaView(); //初始化
    void function();

    void setSelectedLabel();  //动态显示矩形框架，和矩形框架内部未被雾化的截图
    void setSelectedRound();
    void setSelectedArbitrary();
    //动态显示椭圆框架，和椭圆框架内部未被雾化的截图//多边形截图



    QPixmap reshot()
    {
        return shot;
    }

    void refun(int f1,int f2,bool dr,QString p,bool mini); //剪切板，直接保存，连接到图片程序功能选择

private:

    int g_nActScreenX;   //屏幕尺寸
    int g_nActScreenY;
    QTimer *timer; //用时间做文件名
    bool isPressed;//
    int index;   //判断截图方式，0为矩形截图，1为全屏截图，2为椭圆截图，3为多边形截图
    QPainter *painter;     //不同截图方式的核心
    QPixmap *pixmap,*pix_flog,*fog; //全屏截图，雾层等
    QPoint origin,endPoint; //开始点和结束点
    QPoint origin2,endPoint2; //Release之后  用于移动窗口的
    QPointF originP;
    QPointF endP;
    QPoint saveorigin,saveendPoint;

    QPixmap image;

    QPixmap shot;    //最终截图
    QString dirpath;   //保存路径
    int fun1,fun2;//1为直接保存，3为连接到图片程序，2为复制到剪切板
    bool ifdrag;
    //bool ismini
    int isRelease;  //鼠标是否已经释放
    bool ifpressed;
    int ifzoom;//是否放大
    bool IsArbitraryEnd=false;
    bool Isfirstpress =true;
    bool isrightbuttonpressed=false;
    QList<QPointF> Polygonpoints;
    QList<QPointF> Roundfocuspoints;
    QList<int> Roundradius;
    QPolygonF polygon;
    QPainterPath path;
    bool issuicide=false;
    int animation_n;//随机数
    QTime time;
    QCursor *cur = new QCursor;
    bool minimizeFulls;//Widget到fullscreen 间的传递 是否最小化
};

#endif
