#ifdef __gnu_linux__
     #include <sys/prctl.h>
#endif
#include <signal.h>
//on_load will be executed before the main() is called.
__attribute__((constructor))
static void on_load() {

//below function makes sure when the parent process dies, child process(current) will get SIGTERM signal!
#ifdef __gnu_linux__
     prctl(PR_SET_PDEATHSIG, SIGTERM);
 #endif
}
