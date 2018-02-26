/*
编写程序，演示多进程并发执行和进程软中断、管道通信

- 父进程使用系统调用pipe( )建立一个管道,然后使用系统调用 fork()创建两个子进程，子进程1和子进程2
- 子进程1每隔1秒通过管道向子进程2发送数据: I send you x times (x初值为1，每次发送后做加一操作)，子进程2从管道读出信息，并显示在屏幕上。
- 父进程用系统调用signal()捕捉来自键盘的中断信号（即按Ctrl+C 键）；当捕捉到中断信号后，父进程用系统调用kill()向两个子进 程发出信号，子进程捕捉到信号后分别输出下列信息后终止： Child Process l is Killed by Parent! Child Process 2 is Killed by Parent!
- 父进程等待两个子进程终止后，释放管道并输出如下的信息后终止： Parent Process is Killed!
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<signal.h>
#include<sys/wait.h>

void create_process();
void pipe_interaction();
void process_break();
void stop();

int wait_mark = 1;

int main() {
    // create_process();
    // pipe_interaction();
     process_break();

    return 0;
}

/*
父进程使用系统调用pipe( )建立一个管道,然后使用系统调用 fork()创建两个子进程，子进程1和子进程2
*/
void create_process() {

    int p1, p2;
    while((p1 = fork()) == -1);

    if (p1 == 0) {
        // child process one
        printf("process 1\n");
        wait(0);

        printf("1");
    } else {  // main process

        while((p2 = fork()) == -1);

        if (p2 == 0) {
            // child process two
            printf("process 2\n");
            wait(0);
            printf("2");
        } else {   // main process
            printf("process parent \n");
        }

    }
}

/*
子进程1每隔1秒通过管道向子进程2发送数据: I send you x times (x初值为1，每次发送后做加一操作)，子进程2从管道读出信息，并显示在屏幕上。
*/
void pipe_interaction() {
    int i, r;
    int p1, p2;
    int fd[2];

    char buf[50], s[50];

    pipe(fd);

    while((p1 = fork()) == -1);

    if (p1 == 0) {
        int x = 1;
        while (x < 10) {
            // lockf(fd[1], 1, 0);
            sprintf(buf, "I send you %d times\n", x);
            printf("process p1.\n");

            write(fd[1], buf, 50);
            x++;

            sleep(1);
            wait(0);
            // lockf(fd[1], 0, 0);
        }
        exit(0);

    } else {
        while((p2 = fork()) == -1);

        if (p2 == 0) {
            int y = 1;
            // lockf(fd[1], 1, 0);

            while ((r = read(fd[0], s, 50)) != -1) {
                printf(" I receive %d times\n", y);
                printf("%s\n", s);
                y++;
                wait(0);
            }

            sleep(1);
            // lockf(fd[1], 0, 0);
            exit(0);
        }


    }
}

/*
父进程用系统调用signal()捕捉来自键盘的中断信号（即按Ctrl+C 键）；当捕捉到中断信号后向两个子进程发送信号1和2 ，结束子进程生命周期父进程用系统调用kill()向两个子进 程发出信号，子进程捕捉到信号后分别输出下列信息后终止： Child Process l is Killed by Parent! Child Process 2 is Killed by Parent!
*/
void process_break() {
    int p1, p2;
    while((p1=fork())==-1);

    printf("p1=%d\n",p1);

    if(p1==0) {
        signal(SIGINT, stop);
        wait_mark = 1;
        printf("p1 created!\n");

        while(wait_mark);
        printf("\nChild Process 1 is Killed by Parent!\n");
    } else {
        while((p2=fork())==-1);

        printf("p2=%d\n",p2);

        if(p2==0) {
            signal(SIGINT, stop);
            wait_mark = 1;
            printf("p2 created!\n");
            signal(2, stop);

            while(wait_mark);

            printf("\nChild Process 2 is Killed by Parent!\n");
        } else {
            signal(SIGINT, stop);
            while(wait_mark);
            wait_mark = 1;
            kill(p1, 1);
            kill(p2, 2);
            wait(0);
            wait(0);
            wait_mark = 1;
            while(wait_mark) {
                signal(SIGINT, stop);
            }
            printf("\nparent killed!\n");
        }
    }
}

void stop() {
    wait_mark = 0;

}