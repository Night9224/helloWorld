package com.itheima.scala.actor

import scala.actors.Actor

/**
  * author:HJJ
  * date:2018/10/16 14:24 
  * description:1.00
  *
  * 结合case class样例类发送消息和接受消息(例五)
  *
  * 1、将消息封装在一个样例类中
  * 2、通过匹配不同的样例类去执行不同的操作
  * 3、Actor可以返回消息给发送方。通过sender方法向当前消息发送方返回消息
  */
class Actor6 extends Actor {
  override def act(): Unit = {
    loop{
      react{
        case "start" => {println("starting...")}
        case SyncMessage(id,msg) => {
          println(s"id:$id,SyncMessage:$msg")
          //Thread.sleep(5000)
          sender ! ReplyMessage(1,"finished...")
        }
        case AsynMseeage(id,msg) => {
          println(s"id:$id,AsynMessage:$msg")
          Thread.sleep(2000)
          sender ! ReplyMessage(3,"finished...")
          //Thread.sleep(2000)
        }
      }
    }
  }
}

object Actor6 extends App{
  val actor = new Actor6
  actor.start()
  //以下发送的四个消息，就是四个子线程并发的执行各自的方法

  //与actor ! AsynMseeage(2,"我是异步消息无返回值")表达的是同一个意思
  actor ! "start"

  /**
    * 在计算机领域，
    * 同步就是指一个进程在执行某个请求的时候，若该请求需要一段时间才能返回信息，那么这个进程将会一直等待下去，直到收到返回信息才继续执行下去；
    * 异步是指进程不需要一直等下去，而是继续执行下面的操作，不管其他进程的状态。当有消息返回时系统会通知进程进行处理，这样可以提高执行的效率。
    */
  //同步消息有返回值
  val replay1 = actor !? SyncMessage(1,"我是同步消息")//---同步消息：得到该方法运行结束的结果，才能往下面执行结果的输出
  println("replay1:"+replay1)
  println("---------")

  //异步消息有返回值
  val replay3 = actor !! AsynMseeage(3,"我是异步消息有返回值")
  val result = replay3.apply()
  println("replay3:"+result)

  //异步消息无返回值
  actor ! AsynMseeage(2,"我是异步消息无返回值")
  println("---------")



}

case class SyncMessage(id: Int, msg: String)//同步消息

case class ReplyMessage(id: Int, msg: String)//异步消息

case class AsynMseeage(id: Int, msg: String)//返回结果消息



