package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see	nachos.threads.Condition
 */
public class Condition2 {
    /**
     * Allocate a new condition variable.
     *
     * @param	conditionLock	the lock associated with this condition
     *				variable. The current thread must hold this
     *				lock whenever it uses <tt>sleep()</tt>,
     *				<tt>wake()</tt>, or <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
	this.conditionLock = conditionLock;
    }

    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The
     * current thread must hold the associated lock. The thread will
     * automatically reacquire the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());

	conditionLock.release();

  //Implementing condition variables///////////////////////////////////

  boolean intStatus = Machine.interrupt().disable();

	waitQueue.add(KThread.currentThread());
	KThread.sleep();

	Machine.interrupt().restore(intStatus);

  ////////////////////////////////////////////////////////////////////

	conditionLock.acquire();
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The
     * current thread must hold the associated lock.
     */
    public void wake() {

  	Lib.assertTrue(conditionLock.isHeldByCurrentThread());

    //implementing condition variables///////////////////////////////

    boolean intStatus = Machine.interrupt().disable();
  	((KThread)waitQueue.removeFirst()).ready();
  	Machine.interrupt().restore(intStatus);

    ///////////////////////////////////////////////////////////////

    }

    /**
     * Wake up all threads sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wakeAll() {
  	Lib.assertTrue(conditionLock.isHeldByCurrentThread());

    //Implementing condition variables///////////////////////////////

    while (!waitQueue.isEmpty())
  	    wake();
      }

    /////////////////////////////////////////////////////////////////  

    private Lock conditionLock;
}