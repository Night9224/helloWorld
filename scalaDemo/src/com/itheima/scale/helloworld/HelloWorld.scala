package com.itheima.scale.helloworld

/**
  * author:HJJ
  * date:2018/10/12 20:15 
  * description:1.00
  */
//todo:scala的第一个小案例
object HelloWorld {
  def main(args: Array[String]) = {
    val s = "Hello Scala"
    println("s")
    val x = 1
    val n = if (x > 2) 1
    println(n)
    for (i <- 1 to 10)//[1,10]
      println(i)
    println("------")
    for(i <- 1 to 3; j <- 1 to 3 if i != j)
      print((10 * i + j) + " ")
    println()
    val v = for (i <- 1 to 10) yield i * 10
    println(v)




  }
}

