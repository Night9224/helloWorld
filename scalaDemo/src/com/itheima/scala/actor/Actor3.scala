package com.itheima.scala.actor

import scala.actors.Actor

/**
  * author:HJJ
  * date:2018/10/16 11:21 
  * description:1.00
  *
  * 怎么实现actor发送、接受消息
  * 1、定义一个class或者是object继承Actor特质，注意导包import scala.actors.Actor
  * 2、重写对应的act方法
  * 3、调用Actor的start方法执行Actor
  * 4、通过不同发送消息的方式对actor发送消息
  * 5、act方法中通过receive方法接受消息(只能接受到一条消息)并进行相应的处理
  * 6、act方法执行完成之后，程序退出
  */
class Actor3 extends Actor{
  override def act(): Unit = {
    receive {
      case "start" => {
        println("starting...")
      }
      case _ => {
          println("NO match...")
        }
    }
  }
}

object Actor3 extends App{
  val actor = new Actor3
  actor.start()
  actor ! "stop"
  actor ! "start"

  println("消息发送完成！")
}
