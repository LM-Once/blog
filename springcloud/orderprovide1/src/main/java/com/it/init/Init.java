package com.it.init;

import com.it.common.CommonUtils;
import com.it.common.constant.Constant;
import com.it.common.constant.GlobalConstant;
import com.it.common.enums.ProType;
import com.it.common.thread.ThreadPoolManager;
import com.it.common.thread.task.ThreadNettyTask;
import com.it.utils.PropertyUtils;
import org.apache.ibatis.annotations.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName Init
 * @Description 初始化扫描属性文件
 * @Date 2019-12-12 11:45:32
 **/
@Component
public class Init implements ServletContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);


    @Override
    public void setServletContext(ServletContext servletContext) {
        LOGGER.info("log -> init ServletContextAware");
        GlobalConstant.proKeyValues = PropertyUtils.getProKeyValue(CommonUtils.getClassPath() + Constant.INIT_PARAM_URL);
        Iterator<Map.Entry<String, Object>> it = GlobalConstant.proKeyValues.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> next = it.next();
            if (next.getKey().equals(ProType.PORT.getKey())){
                ThreadNettyTask nettyTask = new ThreadNettyTask(Integer.parseInt(next.getValue().toString()));
                ThreadPoolManager.getInstance().execute(nettyTask);
            }
            if (next.getKey().equals(ProType.ACTIVEMQ_QUEUE_SPECIAL.getKey())){
                StartClass.startActiveMq(next.getValue().toString());
            }
        }
    }
}
