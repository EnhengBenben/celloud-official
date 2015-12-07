package com.nova.core;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.struts2.Struts2GuicePluginModule;
import com.mongo.core.SystemContext;
import com.nova.filter.MyStrutsFilter;
import com.nova.kaptcha.Kaptcha;
import com.nova.utils.ConnectManager;
import com.nova.utils.SQLUtils;
import com.nova.utils.XmlUtil;

/**
 * guice全局控制
 * 
 * @author <a href="mailto:wuzhiqiang@novacloud.com">wuzq</a>
 * @date 2012-6-1下午01:20:02
 * @version Revision: 1.0
 */
public class GuiceInjectorContext extends GuiceServletContextListener {
    private static Injector injector;

    @Override
    public Injector getInjector() {
	if (injector == null) {
	    injector = Guice.createInjector(new Struts2GuicePluginModule(),
		    new ServletModule() {
			@Override
			protected void configureServlets() {
			    // 数据源初始化
			    ConnectManager.initSystem();
			    // Mongo初始化
			    SystemContext.initContext();
			    // 集群配置初始化
			    XmlUtil.initMachines();
			    // App Info 初始化
			    SQLUtils.initAppInfo();
			    // 初始化struts2配置,这里MyStrutsFilter已经继承StrutsPrepareAndExecuteFilter
			    bind(MyStrutsFilter.class).in(Singleton.class);
			    filter("/*").through(MyStrutsFilter.class);

			    // 配置验证码的实现
			    Map<String, String> map = new HashMap<String, String>();
			    map.put("kaptcha.border", "no");
			    map.put("kaptcha.image.width", "80");
			    map.put("kaptcha.image.height", "32");
			    map.put("kaptcha.textproducer.char.length", "4");
			    map.put("kaptcha.textproducer.char.space", "3");
			    map.put("kaptcha.textproducer.font.names",
				    "Consolas");
			    map.put("kaptcha.textproducer.font.size", "22");
			    map.put("kaptcha.background.clear.from",
				    "220,220,220");
			    map.put("kaptcha.background.clear.to",
				    "220,220,220");
			    map.put("kaptcha.obscurificator.impl",
				    "com.nova.kaptcha.NoNoise");
			    map.put("kaptcha.textproducer.font.color",
				    "0,153,51");
			    map.put("kaptcha.textproducer.char.string",
				    "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ");
			    this.serve("/kaptcha.jpg").with(Kaptcha.class, map);
			}
		    });
	}

	return injector;
    }

    /**
     * 获取实例
     * 
     * @param <T>
     * @param type
     * @return
     */
    public static <T> T getInstance(Class<T> type) {
	if (injector == null) {
	    injector = new GuiceInjectorContext().getInjector();
	}
	return injector.getInstance(type);
    }
}