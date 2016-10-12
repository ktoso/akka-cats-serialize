package com.romix.akka.serialization.kryo

import akka.actor.ActorSystem
import akka.serialization.{Serialization, SerializationExtension}
import cats.data.{Validated, ValidatedNel}
import com.esotericsoftware.minlog.Log
import com.romix.akka.serialization.kryo.KryoSerializer

object Example extends App {

  implicit val system = ActorSystem()

  val serialization: Serialization = SerializationExtension(system)
  val sample = List(Person("John", "Doe"), Person("Bruce", "Wayne"), Person("Tony", "Stark"))
  val sampleHead: Person = sample.head
  assert(serialization.findSerializerFor(sample).getClass == classOf[KryoSerializer])
  assert(serialization.findSerializerFor(sampleHead).getClass == classOf[KryoSerializer])

  val valid: ValidatedNel[String, Int] = Validated.valid(1)
  val invalid: ValidatedNel[String, Int] = Validated.invalidNel("errrr")

  Log.TRACE()
  
  // These lines make the JVM crash
  val v1 = ValidatedWrapper(valid)
  println("v1 = " + v1)
  serialization.serialize(v1)
  println("serialization.serialize(v1) = " + serialization.serialize(v1))
  val v2 = ValidatedWrapper(invalid)
  println("v2 = " + v2)
  serialization.serialize(v2)
  println("serialization.serialize(v2) = " + serialization.serialize(v2))
  
  println("oooooooooooooo    OK    oooooooooooooo")
  system.terminate()
}




case object TakeSnapshot

case object GetState

case object Boom

case object SnapshotSaveSuccess

case object SnapshotSaveFail

case class Person(fName: String, lName: String)

case class ExampleState(received: List[Person] = Nil) {
  def updated(s: Person): ExampleState = copy(s :: received)

  override def toString = received.reverse.toString
}


case class Test(s: String)

case class ValidatedWrapper(v: ValidatedNel[String, Int])
