package com.biubiu.rpc.core.parallel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *  Fork/Join框架是Java7提供了的一个用于并行执行任务的框架，
 *  是一个把大任务分割成若干个小任务，
 *  最终汇总每个小任务结果后得到大任务结果的框架。
 * @author yule.zhang
 * @date 2019/2/23 16:08
 * @email zhangyule1993@sina.com
 * @description 并发RPC
 */
public class RpcParallel {

    private List<ExecuteWrapper> list = new LinkedList<>();

    public RpcParallel() {
    }

    /**
     * 添加到队列
     * @param call
     */
    public void add(ExecuteWrapper call){
        this.list.add(call);
    }

    public void execute(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Iterator it = this.list.iterator();

        ExecuteWrapper exe;
        while(it.hasNext()){
            exe = (ExecuteWrapper) it.next();
            forkJoinPool.submit(exe);
            //并行执行小任务
            exe.fork();
        }

        it = this.list.iterator();
        while(it.hasNext()){
            exe = (ExecuteWrapper) it.next();
            //合并
            exe.join();
        }
    }
}
