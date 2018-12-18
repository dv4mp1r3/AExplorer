package actions;

public abstract class AbstractAction implements IAction, Runnable {

    protected Object result;

    protected abstract void beforeAction();

    protected abstract void afterAction();

    public void run() {
        beforeAction();
        result = doEvent();
        afterAction();
    }

    public Object getResult()
    {
        return result;
    }


}
