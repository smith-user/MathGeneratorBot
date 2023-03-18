package mathGeneratorBot.appContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

abstract public class AppContext {
    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
