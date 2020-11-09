package com.dbs.service;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    /**
     * @EnableScheduling //允许定时任务
     * @Scheduled(cron = "0 * * * * 0-6")
     * 1. 逗号列举
     * 2. 减号区间
     * 3. /nums 步长 每nums
     * 4. ? 日/星期冲突匹配
     * 5. 星期 0-7 0，7都是周日
     * 4#2 第2个星期四
     * W 工作日 C 和calender联系后计算过的值
     * L 最后一个
     * 6. 日期 W 工作日 C 和calender联系后计算过的值 L 最后一个
     */
    //@Scheduled(cron = "0 * * * * 0-6")
    public void hello() {
        System.out.println("hello--------");
    }
}
