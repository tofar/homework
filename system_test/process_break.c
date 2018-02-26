#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<signal.h>
#include<sys/wait.h>

void process_break();
void stop();

// 等待信号
int wait_mark = 1;

int main() {
     process_break();

    return 0;
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
