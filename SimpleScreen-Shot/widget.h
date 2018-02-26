﻿#ifndef WIDGET_H
#define WIDGET_H

#include<QMenu>

#include<QTimer>
#include <QTextCodec>
#include<QPixmap>
#include<QMessageBox>
#include<QFileDialog>
#include<QFile>
#include<QObject>
#include<QClipboard>
#include<QWaitCondition>
#include<QMainWindow>
#include<QSystemTrayIcon>

#include<QAction>
#include<QBitmap>
#include<QPainter>
#include<QDesktopServices>
#include <QtGui>
#include<QString>

#include <QtGui>
#include<QLabel>
#include<QApplication>
#include <QGraphicsPixmapItem>
#include <QPropertyAnimation>
#include <QDesktopWidget>
#include <QSystemTrayIcon>
#include <QFileDialog>
#include <QDateTime>
#include <QProcess>
#include<QTime>
#include<QScreen>
#include<QMouseEvent>
#include<QCursor>

#include"qwidget.h"
#include"qxtglobalshortcut.h"
#include"fullscreen.h"


namespace Ui {
class Widget;
}

class Widget : public QWidget
{
    Q_OBJECT

public:
    explicit Widget(QWidget *parent = 0);

    ~Widget();
    QSystemTrayIcon *mSysTrayIcon;
    QMenu *mMenu;
    QAction *mShowMainAction;
    QAction *mRecAction;
    QAction *mExitAppAction;
    QAction *mEllipseAction;
    QAction *mFullAction;
    QAction *mArbitraryAction;
    QAction *mminimizeAction;

    void createActions();
    void createMenu();
    void rollOutDownHelp(int height = 691);
    void rollOutDown(int height = 456);
    void rollOutDownhide(int height = 713);
    void rollInDown();
    void unfoldleftwards();
    void unfoldrightwards();
    void unfoldupwards();
    void foldleftwards(int width=10);
    void foldrightwards(int width=10);
    void foldupwards(int height=10);
    void refun2(bool minimizeFulls);
private slots:

    void alltoggle();
    void shotScreenSlot();
    void on_savepushButton_clicked();

    void on_activatedSysTrayIcon(QSystemTrayIcon::ActivationReason reason);
    void on_showMainAction();
    void on_exitAppAction();


    void on_pushButton_clicked();
    void on_RectpushButton_clicked();
    void on_FullpushButton_clicked();
    void on_RoundpushButton_clicked();

    void on_closepushButton_clicked();

    void on_pushButton_2_clicked();
    void on_pushButton_3_clicked();

    //去除边框之后移动使用
    void mousePressEvent(QMouseEvent *e);
    void mouseReleaseEvent(QMouseEvent *e);
    void mouseMoveEvent(QMouseEvent *e);
    //引出二级
    void toggleDown();


    void on_pushButton_5_clicked();

    void on_saveDirpushButton_clicked();

    void on_pushButton_6_clicked();

    void on_pushButton_4_clicked();

    void on_pushButton_8_clicked();

    void on_pushButton_9_clicked();

protected:
private:
    Ui::Widget *ui;
    QTimer *timer;
    QPixmap *pixmap;
    QPixmap * bg;                               // 记录全屏图片
    int g_width;                              // 屏幕宽度
    int g_height;                           // 屏幕高度
    int defaultHeight;
    int defaultWidth;

    int mSysTrayIcon_open=0;
    int delay=0;
    QPoint press,origin;
    bool isdraging;
    //   int isnewshot;   //是否使用截图类
    int shotway;//0为矩形截图，1为全屏截图，2为椭圆截图,3为多边形截图
    FullScreen* shot;  //截图实现类
    //设置界面类
    bool rolledOutDown=false; //向下滑槽
    bool rolledOutDownHelp=false;
    int ishidemode=0;
    int issuicide=0;
    bool isminimize=false;
    bool isattractwidget=false;//是否吸附
    int g_nActScreenX;   //屏幕尺寸
    int g_nActScreenY;



};

#endif // WIDGET_H
