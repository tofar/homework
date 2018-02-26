#include "fullscreen.h"
#include "widget.h"
FullScreen::FullScreen(QLabel *parent) : QLabel(parent)
{

}

//初始化界面
void FullScreen::intiaView()
{
    //Widget* shoot;
    //shoot = new Widget;
    //shoot->show();
    //shoot->refun2(minimizeFulls);
    //初始化鼠标的状态为左键未点下

    setWindowFlags(Qt::WindowStaysOnTopHint|Qt::FramelessWindowHint);
    QDesktopWidget* desktopWidget = QApplication::desktop();
    //获取可用桌面大小
    QRect screenRect = desktopWidget->screenGeometry();
    g_nActScreenX = screenRect.width();
    g_nActScreenY = screenRect.height();
    this->resize(g_nActScreenX,g_nActScreenY);

    this->setFocusPolicy(Qt::StrongFocus);

    pixmap = new QPixmap;

    *pixmap = pixmap->grabWindow(QApplication::desktop()->winId());

    //设置雾层
    pix_flog = new QPixmap(*pixmap);
    fog = new QPixmap(g_nActScreenX,g_nActScreenY);
    //QPixmap fog(g_nActScreenX,g_nActScreenY);
    //背景白雾颜色及透明度

    if(issuicide==true)
    {
        fog->fill(QColor(28,116,166,100));
    }
    else if(index!=3)
    {
        if(index!=8)
            fog->fill(QColor(255,255,255,100));

        else
        {
            fog->fill(QColor(255,255,255,0));
            painter = new QPainter(pix_flog);
            QPixmap pixss;
            pixss.load(":/trayicon/images/bulescreen.png");
            painter->drawPixmap(0,0,g_nActScreenX,g_nActScreenY,pixss);
            cur->setPos(g_nActScreenX,g_nActScreenY);
            this->setPixmap(pixss);
            //小提示,蓝屏假象按两边
        }
    }
    else
        fog->fill(QColor(255,255,255,0));


    painter = new QPainter(pix_flog);
    painter->setFont(QFont("华文行楷",23));
    QPen pen(QColor(0,0,0));
    pen.setWidth(20);
    painter->drawPixmap(0,0,*fog);
    painter->setPen(QPen(QColor(0,0,0)));

    //pixz = *pixmap;
}


//初始化截屏方式及截屏相关数据
FullScreen::FullScreen(int ind)
{

    this->setMouseTracking(true);
    isPressed = false;
    isRelease=0;
    ifpressed=false;  //new
    ifzoom=0;
    fun1=fun2=0;
    this->index = ind;

    if(ind==9)
    {
        index=0;
        issuicide=true;
    }

    intiaView();
    this->setPixmap(*pix_flog);
    setCursor(Qt::CrossCursor);
    this->show();
}

//矩形截图框架实现
void FullScreen::setSelectedLabel(){

    int width = abs(endPoint.x() - origin.x());
    int height = abs(endPoint.y() - origin.y());
    int x = origin.x()  < endPoint.x() ? origin.x():endPoint.x();
    int y = origin.y() < endPoint.y() ? origin.y():endPoint.y();

    QPixmap pix = *pixmap;
    QPainter painter(&pix);
    painter.drawPixmap(0,0,*fog);

    if(width!=0 && height != 0)
    {
        painter.drawPixmap(x,y,pixmap->copy(x,y,width,height));
        painter.setPen(QPen(QColor(0,120,215),1));
        painter.drawRect(QRect(x,y,width,height));
        QPoint temp(endPoint.x()-60,endPoint.y()+15);

        painter.setPen(QPen(QColor(0,0,0),2));
        if(isPressed)
            painter.drawText(temp,tr("%1 x %2").arg(width).arg(height));
        this->setPixmap(pix);

    }

}

//椭圆截图框架实现
void FullScreen::setSelectedRound()
{

    int x = origin.x()  < endPoint.x() ? origin.x():endPoint.x();
    int y = origin.y() < endPoint.y() ? origin.y():endPoint.y();

    int width = abs(endPoint.x() - origin.x());
    int height = abs(endPoint.y() - origin.y());
    if(width > 5 &&height >5)
    {

        QPixmap pix = *pixmap;
        QPainter painter(&pix);
        painter.setPen(QPen(QColor(120,0,205),2));
        painter.drawPixmap(0,0,*fog);

        QSize size(width,height);

        QBitmap mask(size);
        QPainter painter2(&mask);

        painter2.setRenderHint(QPainter::Antialiasing);
        painter2.setRenderHint(QPainter::SmoothPixmapTransform);
        painter2.fillRect(0,0,width,height,Qt::white);
        painter2.setBrush(QColor(0,0,0));
        painter2.drawRoundedRect(0,0,width,height,width / 2,height / 2);

        image = pixmap->copy(x,y,width,height);
        image.setMask(mask);

        painter.drawPixmap(x,y,image);
        painter.drawRoundedRect(x,y,width,height,width / 2,height / 2);

        QPoint temp(endPoint.x()-60,endPoint.y()+15);

        painter.setPen(QPen(QColor(0,0,0),2));
        painter.drawText(temp,tr("%1 x %2").arg(width).arg(height));

        this->setPixmap(pix);
    }

}

//多边形及任意截图框架实现
void FullScreen::setSelectedArbitrary()
{
    QPixmap pix = *pixmap;
    QPainter painter(&pix);
    painter.setRenderHint(QPainter::Antialiasing,true);
    painter.setPen(QPen(QColor(205,20,0),1));

    //painterz.drawLine(Polygonpoints[Polygonpoints.size()-2],
    //Polygonpoints[Polygonpoints.size()-1]);
    //如果会卡就不进行重新绘图
    painter.setRenderHint(QPainter::Antialiasing,true);
    painter.drawPath(path);
    painter.fillPath (path, QBrush(QColor(205,100,0,50)));
    painter.setPen(QPen(QColor(155,0,255),2));
    painter.drawEllipse(originP, 6, 6);
    painter.drawEllipse(originP, 3, 3);
    painter.setPen(QPen(QColor(205,20,0),1));
    for(int j=0;j<Roundfocuspoints.size();j++)
    {
        QBrush bruch(Qt::SolidPattern);
        bruch.setColor(QColor(205,100,0,50));
        painter.setBrush(bruch);
        painter.drawEllipse(Roundfocuspoints[j],Roundradius[j],Roundradius[j]);
    }


    if(IsArbitraryEnd==true)
    {   if(Polygonpoints.size()>1)
        {
            for(int i=0;i<Polygonpoints.size();i++)
            {
                if(originP.x()>Polygonpoints[i].x())
                    originP.setX(Polygonpoints[i].x());

                if(originP.y()>Polygonpoints[i].y())
                    originP.setY(Polygonpoints[i].y());

            }
            for(int j=0;j<Polygonpoints.size();j++)
            {
                if(endP.x()<Polygonpoints[j].x())
                    endP.setX(Polygonpoints[j].x());

                if(endP.y()<Polygonpoints[j].y())
                    endP.setY(Polygonpoints[j].y());
            }
        }
        else if(Roundfocuspoints.size()>0)
        {
            //如果没有左键的点,初始化右键椭圆的点
            originP.rx()=Roundfocuspoints[0].x()-Roundradius[0];
            originP.ry()=Roundfocuspoints[0].y()-Roundradius[0];
            endP.rx()=Roundfocuspoints[0].x()+Roundradius[0];
            endP.ry()=Roundfocuspoints[0].y()+Roundradius[0];
        }

        if(Roundfocuspoints.size()>0)
        {
            int i,j;
            for(i=0;i<Roundfocuspoints.size();i++)
            {
                if((Roundfocuspoints[i].x()-Roundradius[i])<=0)
                {
                    originP.setX(0);break;
                }
                //X超过范围直接设置为0
                else
                {

                    if(originP.x()>Roundfocuspoints[i].x()-Roundradius[i])
                        originP.setX(Roundfocuspoints[i].x()-Roundradius[i]);


                }

                if((Roundfocuspoints[i].y()-Roundradius[i])<=0)
                {
                    originP.setY(0);
                    break;
                }//Y超过范围直接设置为0
                else
                {
                    if(originP.y()>Roundfocuspoints[i].y()-Roundradius[i])
                        originP.setY(Roundfocuspoints[i].y()-Roundradius[i]);
                }
            }
            for(j=0;j<Roundfocuspoints.size();j++)
            {
                if((Roundfocuspoints[j].y()+Roundradius[j])>=g_nActScreenY)
                {
                    endP.setY(g_nActScreenY);
                    break;
                    //超过范围直接设置为最大
                }
                else
                {

                    if(endP.y()<Roundfocuspoints[j].y()+Roundradius[j])
                        endP.setY(Roundfocuspoints[j].y()+Roundradius[j]);
                }
                if((Roundfocuspoints[j].x()+Roundradius[j])>=g_nActScreenX)
                {
                    endP.setX(g_nActScreenX);
                    break;

                    //超过范围直接设置为最大
                }
                else

                {
                    if(endP.x()<Roundfocuspoints[j].x()+Roundradius[j])
                        endP.setX(Roundfocuspoints[j].x()+Roundradius[j]);


                }
            }
        }

        //两个if找出边界

        int width =endP.x() - originP.x();
        int height =endP.y() - originP.y();
        int x=originP.x();
        int y=originP.y();

        image = pixmap->copy(x,y,width,height);

        QBitmap mask(width,height);
        mask.fill(Qt::white);
        QPainter painter2(&mask);
        painter2.setRenderHint(QPainter::Antialiasing,true);
        painter2.setRenderHint(QPainter::SmoothPixmapTransform);
        painter2.setBrush(QColor(0,0,0));

        if(Polygonpoints.size()>0)
        {
            QPainterPath pathz;
            pathz.moveTo(Polygonpoints[0]-originP);

            for(int i=1;i<Polygonpoints.size();i++)
            {
                pathz.lineTo(Polygonpoints[i]-originP);
            }
            painter2.drawPath(pathz);
        }
        if(Roundfocuspoints.size()>0)
        {
            for(int j=0;j<Roundfocuspoints.size();j++)
            {
                painter2.drawEllipse(Roundfocuspoints[j]-originP,Roundradius[j],Roundradius[j]);
            }
        }
        image.setMask(mask);
    }

    this->setPixmap(pix);
}


//鼠标按下
void FullScreen::mousePressEvent(QMouseEvent *event)
{
    ifzoom=1;
    isPressed = true;
    ifpressed=true;//new
    if(Qt::LeftButton == event->button())
    {
        if(index!=3)
        {
            if(isRelease==0)
            {
                origin = origin = event->pos();
            }
            else
            {
                origin2=event->pos();
            }
        }
        else
        {

            if(Isfirstpress == true)
            {
                path.moveTo(event->pos());
                originP= event->pos();
                endP= event->pos();
                Polygonpoints<<event->pos();
                setSelectedArbitrary();
                Isfirstpress =false;
            }
            else
            {
                endP=event->pos();
                Polygonpoints<<event->pos();
                path.lineTo(event->pos());
                if((abs(Polygonpoints[Polygonpoints.size()-1].x()-originP.x())<=6)
                        &&(abs(Polygonpoints[Polygonpoints.size()-1].y()-originP.y())<=6))
                {
                    IsArbitraryEnd=true;
                    setSelectedArbitrary();
                    function();
                    this->close();
                }
                setSelectedArbitrary();
            }


        }
    }


    if(Qt::RightButton==event->button())
    {
        if(index!=3){this->close();
        }
        else{
            isrightbuttonpressed=true;
            endP=event->pos();
            Roundfocuspoints<<event->pos();
            time.start();
        }
        //Roundradius<<animation_n;
        //qsrand(QTime(0,0,0).secsTo(QTime::currentTime()));
        //animation_n=qrand()%40+2;
    }

}
//鼠标移动
void FullScreen::mouseMoveEvent(QMouseEvent *event)
{
    setMouseTracking(true);

    if(index!=3)
    {

        if(isRelease==0 && isPressed)
        {

            endPoint = event->pos();
            //矩形截图
            if(index == 0)
            {
                setSelectedLabel();
            }
            //动态获取裁剪图片

            //圆形截图
            if(index == 2)
            {
                setSelectedRound();
            }
        }


        //在按下之前的移动时的放大镜
        if(isRelease==0&&!isPressed)
        {
            ifzoom=1;
            endPoint = event->pos();

            origin.setX(endPoint.x()-1);
            origin.setY(endPoint.y()-1);

            setSelectedLabel();


        }

        if(isRelease==1&&ifdrag)
        {
            endPoint2=event->pos();
            int ox=saveorigin.x();
            int oy=saveorigin.y();
            int ex=saveendPoint.x();
            int ey=saveendPoint.y();

            int ox2=ox+endPoint2.x()-origin2.x();
            int oy2=oy+endPoint2.y()-origin2.y();
            int ex2=ex+endPoint2.x()-origin2.x();
            int ey2=ey+endPoint2.y()-origin2.y();


            if(ox2>0&&ox2<g_nActScreenX&&ex2>0&&ex2<g_nActScreenX)
                if(oy2>0&&oy2<g_nActScreenY&&ey2>0&&ey2<g_nActScreenY)
                {
                    origin.setX(ox2);
                    origin.setY(oy2);
                    endPoint.setX(ex2);
                    endPoint.setY(ey2);

                    //矩形截图
                    if(index == 0)
                    {
                        setSelectedLabel();
                    }
                    //圆形截图
                    if(index == 2)
                    {
                        setSelectedRound();
                    }
                }
        }

    }
    else
    {
        endP= event->pos();
        ifzoom=1;
        setSelectedArbitrary();
        if(isrightbuttonpressed==false && isPressed)
        {
            //默认为ispressed=true才有event

            if(abs(originP.x()-(event->pos().x()))<7&& abs(originP.y()-(event->pos().y()))<7 )
            {
                if(Polygonpoints.size()>10)
                {
                    cur->setPos(originP.x(),originP.y());
                }

            }//吸附,先吸附再入点
            endP=event->pos();
            Polygonpoints<<event->pos();
            path.lineTo(event->pos());
            setSelectedArbitrary();
        }

    }


}



//鼠标释放
void FullScreen::mouseReleaseEvent(QMouseEvent *event)
{ Q_UNUSED( event )
            isPressed = false;
    if(index!=3)
    {
        this->setMouseTracking(false);
        ifzoom=0;
        if(ifdrag)
        {
            //矩形截图
            if(index == 0)
            {
                setSelectedLabel();
            }


            //圆形截图
            if(index == 2)
            {
                setSelectedRound();
            }
            saveorigin=origin;
            saveendPoint=endPoint;
            isRelease=1;
        }
        else
        {
            this->close();
            function();
        }
    }
    else if(Qt::RightButton==event->button())
    {
        isrightbuttonpressed=false;
        int time_Diff = time.elapsed();
        float f = time_Diff/1000.0;//单位秒
        Roundradius<<2+50*f+105*f*f+25*f*f*f;
        setSelectedArbitrary();
    }
}


//鼠标双击
void FullScreen::mouseDoubleClickEvent(QMouseEvent *ev)
{ Q_UNUSED( ev )

    if(index!=3)
    {
        this->close();

        if(ifdrag==true)
            function();

    }

    if(index==3)
    {


        IsArbitraryEnd=true;
        setSelectedArbitrary();
        function();
        this->close();

    }


}



//键盘按下
void FullScreen::keyPressEvent(QKeyEvent *e)
{

    //退出
    if(e->key() == Qt::Key_Escape)
    {
        this->close();
    }




    //实现功能
    if(e->key()==Qt::Key_Return)
    {
        if(index!=3)this->close();
        if(index==3)
        {
            IsArbitraryEnd=true;
            setSelectedArbitrary();
            this->close();
        }
        function();
    }



    //键盘微调
    if(isRelease==1)
    {
        if(e->key()== Qt::Key_A)
        {
            origin.setX(origin.x()-1);
            endPoint.setX(endPoint.x()-1);
        }
        if(e->key()==Qt::Key_D)
        {
            origin.setX(origin.x()+1);
            endPoint.setX(endPoint.x()+1);
        }
        if(e->key()==Qt::Key_W)
        {
            origin.setY(origin.y()-1);
            endPoint.setY(endPoint.y()-1);
        }
        if(e->key()==Qt::Key_S)
        {
            origin.setY(origin.y()+1);
            endPoint.setY(endPoint.y()+1);
        }
    }

    //矩形截图
    if(index == 0)
    {
        setSelectedLabel();
    }


    //圆形截图
    if(index == 2)
    {
        setSelectedRound();
    }

}


//功能实现
void FullScreen::function()
{
    //矩形截图
    if(index == 0 )
    {


        int width = abs(endPoint.x() - origin.x());
        int height = abs(endPoint.y() - origin.y());
        int x = origin.x()  < endPoint.x() ? origin.x():endPoint.x();
        int y = origin.y() < endPoint.y() ? origin.y():endPoint.y();
        // QRect *rect = new QRect(rubberBand->pos().x(),rubberBand->pos.y(),rubberBand->pos().x() + rubberBand->width(),rubberBand->pos().y()+rubberBand->height());

        QPixmap pix = pixmap->copy(x,y,width,height);

        shot=pix;



        //直接保存到文件管理
        if(fun2==1)
        {
            //文件路径示例   /home/Qt_picture/new.png
            QDateTime time = QDateTime::currentDateTime();
            QString str = time.toString("MM.dd-hh.mm.ss");

            // 保存文件全路径
            str = dirpath + "/" + str+ ".png";
            // 保存
            pixmap->save(str, "png");


        }
        //连接到图片程序
        if(fun2==3)
        {

            //文件路径示例   /home/Qt_picture/new.png
            QDateTime time = QDateTime::currentDateTime();
            QString str = time.toString("MM.dd-hh.mm.ss");

            // 保存文件全路径
            str = dirpath + "/" + str+ ".png";

            // 保存
            pixmap->save(str, "png");
            QString picPath = "file:///" + str;
            QDesktopServices::openUrl(QUrl(picPath));
        }



        //把文件放到剪切板上
        if(fun1==2)
            QApplication::clipboard()->setImage(pix.toImage());


    }
    //圆形截图
    if(index == 2||index==3)
    {

        shot=image;


        //直接保存到文件管理
        if(fun2==1)
        {
            //文件路径示例   /home/Qt_picture/new.png
            QDateTime time = QDateTime::currentDateTime();
            QString str = time.toString("MM.dd-hh.mm.ss");

            // 保存文件全路径
            str = dirpath + "/" + str+ ".png";
            // 保存
            image.save(str, "png");

        }
        //连接到图片程序
        if(fun2==3)
        {


            //文件路径示例   /home/Qt_picture/new.png
            QDateTime time = QDateTime::currentDateTime();
            QString str = time.toString("MM.dd-hh.mm.ss");

            // 保存文件全路径
            str = dirpath + "/" + str+ ".png";
            // 保存
            image.save(str, "png");
            //获取当前路径
            QString picPath = "file:///" + str;
            QDesktopServices::openUrl(QUrl(picPath));
        }



        //把文件放到剪切板上
        if(fun1==2)
            QApplication::clipboard()->setImage(image.toImage());


    }

}



//放大镜实现
void FullScreen::paintEvent(QPaintEvent *event){

    QLabel::paintEvent(event);
    if(index==8)
    {
        QPainter painter(this);
        QPixmap pixxx;
        pixxx.load(":/trayicon/images/bulescreen.png");
        painter.drawPixmap(0,0,g_nActScreenX,g_nActScreenY,pixxx);
    }
    if(ifzoom&&index == 0)
    {
        //放大框
        QRect zoomRect=QRect(endPoint.x()-10,endPoint.y()-10,20,20);

        QPixmap Map=pixmap->copy(zoomRect);
        QPainter zoompainter(this);
        QPen pen;
        QBrush brush(QColor(0, 0, 0));
        QFont font("Microsoft YaHei",8);

        int x=endPoint.x();
        int y=endPoint.y();
        if(y+140>=g_nActScreenY)
        {
            y=y-100;
        }
        if(x+110>=g_nActScreenX)
        {
            x=x-100;
        }
        //显示大小
        int width = abs(endPoint.x() - origin.x());
        int height = abs(endPoint.y() - origin.y());
        //显示放大点的RGB
        int red,green,blue;

        red = qRed(pixmap->toImage().pixel(endPoint.x(),endPoint.y()));
        green = qGreen(pixmap->toImage().pixel(endPoint.x(),endPoint.y()));
        blue = qBlue(pixmap->toImage().pixel(endPoint.x(),endPoint.y()));

        zoompainter.drawPixmap(x+10, y+10, 100, 100, Map);
        pen.setColor(QColor(0, 255, 0, 125));
        pen.setWidthF(3);
        zoompainter.setPen(pen);
        zoompainter.drawLine(x+10+50, y+15, x+10+50, y+10+95);
        zoompainter.drawLine(x+15, y+10+50, x+10+95, y+10+50);
        pen.setColor(QColor(0,0,0));
        pen.setWidth(1);
        zoompainter.setPen(pen);
        zoompainter.drawRect(x+10, y+10, 100, 100);
        //信息框
        brush.setColor(QColor(0,0,0,200));
        zoompainter.setBrush(brush);
        zoompainter.drawRect(x+10, y+10+100,100,30);
        QRect infoRect_1 = QRect(x+10,y+10+100,100,15);
        QRect infoRect_2 = QRect(x+10,y+10+100+15,100,15);
        pen.setColor(QColor(255,255,255));
        zoompainter.setPen(pen);
        zoompainter.setFont(font);
        if(isPressed)
        {
            zoompainter.drawText(infoRect_1,Qt::AlignHCenter|Qt::AlignVCenter,QString("%1 x %2").arg(width).arg(height));
        }
        zoompainter.drawText(infoRect_2,Qt::AlignHCenter|Qt::AlignVCenter,QString("RGB(%1,%2,%3)").arg(red).arg(green).arg(blue));
    }
    if(ifzoom&&index == 3)
    {
        //放大框
        QRect zoomRect=QRect(endP.x()-10,endP.y()-10,20,20);

        QPixmap Map=pixmap->copy(zoomRect);
        QPainter zoompainter(this);
        QPen pen;
        QBrush brush(QColor(0, 0, 0));
        QFont font("Microsoft YaHei",8);
        int red,green,blue;    //显示放大点的RGB

        red = qRed(pixmap->toImage().pixel(endP.x(),endP.y()));
        green = qGreen(pixmap->toImage().pixel(endP.x(),endP.y()));
        blue = qBlue(pixmap->toImage().pixel(endP.x(),endP.y()));
        int x=endP.x();int y=endP.y();
        if(y+140>=g_nActScreenY)
        {
            y=y-200;
        }
        if(x+110>=g_nActScreenX)
        {
            x=x-200;
        }
        if(y-140<=0)
        {
            y=y+100;
        }
        if(x-110<=0)
        {
            x=x+100;
        }

        zoompainter.drawPixmap(x+10,y+10, 100, 100, Map);
        pen.setColor(QColor(0, 255, 0, 125));
        pen.setWidthF(3);
        zoompainter.setPen(pen);
        zoompainter.drawLine(x+10+50, y+15, x+10+50, y+10+95);
        zoompainter.drawLine(x+15, y+10+50, x+10+95, y+10+50);
        pen.setColor(QColor(0,0,0));
        pen.setWidth(1);
        zoompainter.setPen(pen);
        zoompainter.drawRect(x+10, y+10, 100, 100);
        //信息框
        brush.setColor(QColor(0,0,0,200));
        zoompainter.setBrush(brush);
        zoompainter.drawRect(x+10, y+10+100,100,30);
        QRect infoRect_1 = QRect(x+10,y+10+100,100,15);
        pen.setColor(QColor(255,255,255));
        zoompainter.setPen(pen);
        zoompainter.setFont(font);
        zoompainter.drawText(infoRect_1,Qt::AlignHCenter|Qt::AlignVCenter,QString("RGB(%1,%2,%3)").arg(red).arg(green).arg(blue));
    }


}

FullScreen::~FullScreen()
{


}


//功能修改
void FullScreen::refun(int f1,int f2,bool dr,QString p,bool mini)
{
    minimizeFulls=mini;
    fun1=f1;
    fun2=f2;
    ifdrag=dr;
    dirpath=p;
}


