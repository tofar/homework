#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <sys/ipc.h>

void P(int semid, int index);
void V(int semid, int index);
void *subp1();
void *subp2();

int semid;
int sum = 0;

// 初始化时，val可看出执行数和等待数
union semun {
    int              val;    /* Value for SETVAL */
    struct semid_ds *buf;    /* Buffer for IPC_STAT, IPC_SET */
    unsigned short  *array;  /* Array for GETALL, SETALL */
};

void P(int semid, int index)     //用于请求资源
{
    struct sembuf sem;
    sem.sem_num = index;    //表示是第几个灯
    sem.sem_op = -1;        //加到当前信号灯的值
    sem.sem_flg = 0;       //：操作标记：0或IPC_NOWAIT等
    semop(semid, &sem, 1);               //1:表示执行命令的个数
}

void V(int semid, int index)     //用于释放资源
{
    struct sembuf sem;
    sem.sem_num = index;
    sem.sem_op =  1;
    sem.sem_flg = 0;
    semop(semid, &sem, 1);
}

void *subp1()        //用于打印结果的线程
{
    int i;
    for (i = 1; i <= 100; i++)
    {
        P(semid, 1); // 请求资源，必须等待线程2使用V操作之后，这里之后才能进入执行，其他类似
        printf("sum = %d\n", sum);
        V(semid, 0); // 释放资源，使线程2能进行计算
    }
}

void *subp2()           //用于累加结果的线程
{
    int i;
    for (i = 0; i < 100; i++)
    {
        P(semid, 0);  // 请求资源，必须等线程1释放之后才能拿到资源
        sum +=  1;
        V(semid, 1);  // 释放资源，使线程1能拿到资源
    }
}

int main()
{
    pthread_t p1, p2;
    union semun semopts;

    //获得两个信号灯
    semid = semget(11, 2, IPC_CREAT | 0666);
    // 这里 semopts.val = 0 与后面的 = 1互换一下，输出就是 1开始，不是0开始
    semopts.val = 0;
    semctl(semid, 0, SETVAL, semopts);
    semopts.val = 1;
    semctl(semid, 1, SETVAL, semopts);

    pthread_create(&p1, NULL, subp1, NULL);
    pthread_create(&p2, NULL, subp2, NULL);

    // 挂起当前线程，直到函数终止为止
    pthread_join(p1, NULL);
    pthread_join(p2, NULL);

    //删除信号灯
    semctl(semid, 2, IPC_RMID, 0);

    return 0;
}
