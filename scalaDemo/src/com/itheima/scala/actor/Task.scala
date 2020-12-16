package com.itheima.scala.actor

import scala.actors.Actor

/**
  * author:HJJ111111
  * date:2018/10/15 21:05 
  * description:1.00
  */
//todo:利用actor并发编程来实现单机版的wordCount程序
//todo:将多个文件作为输入，先进行
import java.io.File
import scala.actors.{Actor, Future}
import scala.collection.mutable
import scala.io.Source

case class SubmitTask(fileName: String)
case class ResultTask(result: Map[String, Int])
class Task extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case SubmitTask(fileName) => {
          //1.利用Source读取文件内容
          val contents = Source.fromFile(new File(fileName)).mkString
          //2.按照换行符切分：window下的文件换行符为\r\n ，
          val arr = contents.split("\r\n")
          val result = arr.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1).mapValues(_.length)
          //val result = arr.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1).mapValues(_.foldLeft(0)(_ + _._2))
          sender ! ResultTask(result)
        }
      }
    }
  }
}

object WorkCount {
  def main(args: Array[String]) {
    val files = Array("D:\\wordcount\\input\\word1.txt", "D:\\wordcount\\input\\word2.txt","D:\\wordcount\\input\\word3.txt")
    val replaySet = new mutable.HashSet[Future[Any]]
    val resultList = new mutable.ListBuffer[ResultTask]
    for(f <- files) {
      val t = new Task
      val replay = t.start() !! SubmitTask(f)
      replaySet += replay
    }
    while(replaySet.size > 0){
      val toCumpute = replaySet.filter(_.isSet)
      for(r <- toCumpute){
        val result = r.apply()
        resultList += result.asInstanceOf[ResultTask]
        replaySet.remove(r)
      }

    }
    //val finalResult = resultList.map(_.result).flatten.groupBy(_._1).mapValues(x => x.foldLeft(0)(_ + _._2))
    val finalResult = resultList.map(_.result).flatten.groupBy(_._1).mapValues(x => x.foldLeft(0)(_ + _._2))
    //ListBuffer(ResultTask(Map(again -> 1, world -> 1, hello -> 1)), ResultTask(Map(you -> 1, miss -> 1, i -> 1)), ResultTask(Map(world -> 1, hello -> 1)))
    //ListBuffer(Map(world -> 1, hello -> 1), Map(again -> 1, world -> 1, hello -> 1), Map(you -> 1, miss -> 1, i -> 1))
    //ListBuffer((world,1), (hello,1), (again,1), (world,1), (hello,1), (you,1), (miss,1), (i,1))
    //Map(world -> ListBuffer((world,1), (world,1)), i -> ListBuffer((i,1)), miss -> ListBuffer((miss,1)), you -> ListBuffer((you,1)), again -> ListBuffer((again,1)), hello -> ListBuffer((hello,1), (hello,1)))
    //Map(world -> 2, i -> 1, miss -> 1, you -> 1, again -> 1, hello -> 2)
    println(finalResult)
  }
}

