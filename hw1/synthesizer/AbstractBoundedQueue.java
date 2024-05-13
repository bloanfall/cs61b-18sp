package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
//    @override
    public int capacity() {
        return capacity;
    }

//    @override
    public int fillCount() {
        return fillCount;
    }

}
