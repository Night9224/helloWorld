package com.itheima.scale.helloworld

/**
  * author:HJJ
  * date:2018/10/15 11:07 
  * description:1.00
  */
class Student(val name:String,var age:Int){
  //组构造器会执行类定义的所有语句
  println("执行主构造器"+" "+name+" "+age+" "+gender)
  private var gender="male"
  def this(name:String,age:Int,gender:String){
    //每个辅助构造器执行必须主构造器或者其他辅助构造器的调用开始
    this(name,age)
  println("执行辅助构造器"+" "+name+" "+age+" "+gender)
  this.gender=gender
  }

}
object Student {
  def main(args:Array[String]):Unit = {
    val s1=new Student("zhangsan",20)
    val s2=new Student("lisi",22,"female")
  }
}
