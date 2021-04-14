package com.xxx.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogToolForPerformance {

    public Long lastTime = 0l;
    public boolean logon = true;

    public void logtime(Integer step) {
        if (!logon) {
            return;
        }
        Long time = System.currentTimeMillis();
        if (lastTime == 0l) {
            lastTime = time;
        }
        log.error(step + "time spliter" + (time - lastTime));
        lastTime = time;
    }

    public void logtimeString(org.slf4j.Logger logx, String step) {
        if (!logon) {
            return;
        }
        Long time = System.currentTimeMillis();
        if (lastTime == 0l) {
            lastTime = time;
        }
        logx.error(step + "time spliter" + (time - lastTime));
        lastTime = time;
    }
}
