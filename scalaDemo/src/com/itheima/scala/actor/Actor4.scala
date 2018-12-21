package com.itheima.scala.actor

import scala.actors.Actor

/**
  * author:HJJ
  * date:2018/10/15 20:10 
  * description:1.00
  */
//todo:怎么实现actor可以不断地接受消息：
//todo:在act方法中可以使用while(true)的方式，不断的接受消息。
//while (true) {}方法存在一个线程的创建和销毁，比较费时，建议使用 loop { react {}}

class Actor4 extends Actor {
  override def act(): Unit = {
    while (true) {
      receive {
        case "start" => {
          Thread.sleep(2000)
          println("starting...")
        }
        case "stop" => {
          Thread.sleep(2000)
          println("stoping...")
        }
      }
    }
  }
}

object Actor4 {
  def main(args: Array[String]): Unit = {
    val actor = new Actor4
    actor.start()
    actor ! "start"
    actor ! "stop"
    println("消息发送结束")
  }
}
