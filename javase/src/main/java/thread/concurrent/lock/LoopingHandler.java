package thread.concurrent.lock;

import java.util.List;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月02日 10:25:00
 */
public interface LoopingHandler {

    void handler(List<String> list);
}
