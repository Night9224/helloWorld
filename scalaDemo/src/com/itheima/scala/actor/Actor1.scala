package com.itheima.scala.actor
import scala.actors.Actor
/**
  * author:HJJ
  * date:2018/10/15 19:54 
  * description:1.00
  *
  * 第一个例子
  * 怎么实现actor并发编程：
  * 1、定义一个class或者是object继承Actor特质，注意导包import scala.actors.Actor
  * 2、重写对应的act方法
  * 3、调用Actor的start方法执行Actor
  * 4、当act方法执行完成，整个程序运行结束
  */

object Actor1 extends Actor{
  override def act(): Unit = {
    for(i <- 1 to 10 ){
      println("actor-1  "+i)
      Thread.sleep(10)
    }
  }
}

object Actor2 extends Actor{
  override def act(): Unit = {
    for(i <- 1 to 10 ){
      println("actor-2  "+i)
      Thread.sleep(10)
    }
  }
}
object ActorTest extends App {
  //启动Actor
  Actor1.start()
  Actor2.start()
}

/**
  * 说明：上面ActorTest分别调用了两个单例对象(Actor1，Actor2)的start()方法，他们的act()方法会被执行，相同与在java中开启了两个线程，线程的run()方法会被执行
  * 注意：这两个Actor是并行执行的，act()方法中的for循环执行完成后actor程序就退出了
  */
