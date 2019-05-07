#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

extern int create_process (char* program, char** arg_list);

/**
 * 创建新的进程
 */
int create_process (char* program, char** arg_list) {
    pid_t child_pid;
    child_pid = fork ();
    // 如果是父进程，则返回子进程的进程号
    if (child_pid != 0)
        return child_pid;
    else {
        // 如果是子进程则通过execvp来运行一个新的程序
        execvp (program, arg_list);
        abort ();
    }
}