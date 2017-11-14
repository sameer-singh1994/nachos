package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    /**
     * Allocate a new communicator.
     */
    public Communicator() {
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
      lock.acquire();

      speaker++;

      // while no available listener or word is ready(but listener hasn't fetched it)
      while(isWordReady || listener == 0)
      {
        speakerCond.sleep();
      }

      //speaker says a word
      isWordReady = true;

      //wake up all listeners
      listenerCond.wakeAll();

      speaker--;

      lock.release();
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */
    public int listen() {
//Implimentation of communicator///////////////////////////////

//listener acquires lock
    lock.acquire();

//increasing listeners
    listener++;

//listener to sleep if word is not ready
    while(isWordReady == false)
    {
      speakerCond.wakeAll();
      listenerCond.sleep();
    }

//listener recieves the word

    int word = this.word;

//reset the flag
    isWordReady = false;

//Decreasing listener number
    listener--;

    lock.release();

    return word;


//Implimentation of communicator///////////////////////////////
    }
}
