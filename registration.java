import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void methodA() {
        lock1.lock();
        System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock1");
        try {
            // Introduce a delay to increase the chance of deadlock
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            lock2.lock();
            System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock2");
            try {
                System.out.println("Thread " + Thread.currentThread().getName() + " executing methodA critical section");
            } finally {
                lock2.unlock();
                System.out.println("Thread " + Thread.currentThread().getName() + " released lock2");
            }
        } finally {
            lock1.unlock();
            System.out.println("Thread " + Thread.currentThread().getName() + " released lock1");
        }
    }

    public void methodB() {
        lock2.lock();
        System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock2");
        try {
            // Introduce a delay to increase the chance of deadlock
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            lock1.lock();
            System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock1");
            try {
                System.out.println("Thread " + Thread.currentThread().getName() + " executing methodB critical section");
            } finally {
                lock1.unlock();
                System.out.println("Thread " + Thread.currentThread().getName() + " released lock1");
            }
        } finally {
            lock2.unlock();
            System.out.println("Thread " + Thread.currentThread().getName() + " released lock2");
        }
    }

    public static void main(String[] args) {
        DeadlockExample example = new DeadlockExample();

        new Thread(() -> example.methodA(), "Thread-A").start();
        new Thread(() -> example.methodB(), "Thread-B").start();
    }
}
