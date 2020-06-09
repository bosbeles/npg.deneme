package npg.model;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class MyRunner extends BlockJUnit4ClassRunner {

    static {
        System.out.println("Load Library");
    }

    public MyRunner(Class<?> klass) throws InitializationError {
        super(klass);
        System.out.println("Loading " + klass);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        System.out.println("invoking: " + method.getName());
        return super.methodInvoker(method, test);
    }
}