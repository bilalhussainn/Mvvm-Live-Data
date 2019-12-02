package com.bilal.mvvmsample

import kotlinx.coroutines.*
import kotlin.concurrent.thread

object TestClass {

    @JvmStatic
    fun main(args: Array<String>) {
        // your block here

        exampleRun()
    }

    fun example1() {
        //1
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1000)// non-blocking delay for 1 second (default time unit is ms)
            print("World")
        }

        print("Hello")  // main thread continues while coroutine is delayed
        Thread.sleep(1025L) // block main thread for 2 seconds to keep JVM alive
    }

    fun example2() {
        //2
        thread {
            // from kotlin.cuncurrent package
            //delay(1000)  //It is a suspend function.   // from kotlinx.coroutines package
            Thread.sleep(1000) //from java.lang package
            print("World")
        }

        print("Hello")
        Thread.sleep(2000)
    }

    //Bridging blocking and non-blocking worlds
    //The result is the same, but this code uses only non-blocking delay. The main thread invoking runBlocking blocks until the coroutine inside runBlocking completes.
    fun example3() {
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,") // main thread continues here immediately
        runBlocking {
            // but this expression blocks the main thread
            delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
        }
    }

    //Here runBlocking<Unit> { ... } works as an adaptor that is used to start the top-level main coroutine.
    fun example4() = runBlocking {
        // start main coroutine
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,") // main coroutine continues here immediately
        delay(2000L)      // delaying for 2 seconds to keep JVM alive
    }

    fun example5() = runBlocking {
        val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
            delay(2000L)
            println("World!" + "${Thread.currentThread()}")
        }

        val job2 = GlobalScope.launch {
            delay(3000L)
         job.join() // wait until child coroutine completes
            println("Bilal" + "${Thread.currentThread()}")

        }
        println("Hello," + "${Thread.currentThread()}")

        job2.join()

    }

   // fun exampleRun() {

 //   }


    // Structured Cuncurrency
    fun example6() = runBlocking { // this: CoroutineScope
        launch { // launch a new coroutine in the scope of runBlocking   //so runs on Main Thread.
            delay(1000L)
            println("World!" + "${Thread.currentThread()}")
        }
        println("Hello," + "${Thread.currentThread()}") //runs on Main Thread.
    }

    //Scope builder
    fun example7() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking" + "${Thread.currentThread()}")
        }

        //Scope builder
        // does not complete until all launched children complete. The main difference between
        // runBlocking and coroutineScope is that the latter does not block the current thread while waiting for all children to complete.
        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch" + "${Thread.currentThread()}")
            }

            delay(100L)
            println("Task from coroutine scope" + "${Thread.currentThread()}") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over" + "${Thread.currentThread()}") // This line is not printed until the nested launch completes
    }
//exampleRun


    //Extract function refactoring
    fun exampleRun() = runBlocking {
        launch { doWorld() }
        println("Hello,")
    }

    // this is your first suspending function
    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }




}