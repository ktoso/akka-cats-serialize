# akka-cats-serialize

Standalone reproducer causing JVM to crash on Kryo serializer.
Seems to be a Kryo issue as far as I was able to debug it.


Failure run:

```
$ sbt run
[info] Set current project to akka-cats-serialize (in build file:/Users/ktoso/code/akka-cats-serialize/)
[warn] Multiple resolvers having different access mechanism configured with same name 'typesafe-releases'. To avoid conflict, Remove duplicate project resolvers (`resolvers`) or rename publishing resolver (`publishTo`).
[info] Running com.romix.akka.serialization.kryo.Example
v1 = ValidatedWrapper(Valid(1))
Implicitly registered class with id: com.romix.akka.serialization.kryo.ValidatedWrapper=415019382
Implicitly registered class with id: cats.data.Validated$Valid=1209028387
Implicitly registered class with id: cats.data.NonEmptyList=413260567
#
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x000000010c174c8c, pid=50909, tid=29187
#
# JRE version: Java(TM) SE Runtime Environment (8.0_65-b17) (build 1.8.0_65-b17)
# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.65-b01 mixed mode bsd-amd64 compressed oops)
# Problematic frame:
# V  [libjvm.dylib+0x574c8c]  Unsafe_GetObject+0x51
#
# Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
#
# An error report file with more information is saved as:
# /Users/ktoso/code/akka-cats-serialize/hs_err_pid50909.log
#
# If you would like to submit a bug report, please visit:
#   http://bugreport.java.com/bugreport/crash.jsp
#
Abort trap: 6
```


Success run (with `Log.TRACE()` which seems to fix the issue):

```
$ sbt run
[info] Set current project to akka-cats-serialize (in build file:/Users/ktoso/code/akka-cats-serialize/)
[warn] Multiple resolvers having different access mechanism configured with same name 'typesafe-releases'. To avoid conflict, Remove duplicate project resolvers (`resolvers`) or rename publishing resolver (`publishTo`).
[info] Compiling 1 Scala source to /Users/ktoso/code/akka-cats-serialize/target/scala-2.11/classes...
[warn] /Users/ktoso/code/akka-cats-serialize/src/main/scala/Example.scala:7: imported `KryoSerializer' is permanently hidden by definition of class KryoSerializer in package kryo
[warn] import com.romix.akka.serialization.kryo.KryoSerializer
[warn]                                          ^
[warn] one warning found
[info] Running com.romix.akka.serialization.kryo.Example
v1 = ValidatedWrapper(Valid(1))
00:00 TRACE: [kryo] Register class ID 0: int (com.esotericsoftware.kryo.serializers.DefaultSerializers$IntSerializer)
00:00 TRACE: [kryo] Register class ID 1: String (com.esotericsoftware.kryo.serializers.DefaultSerializers$StringSerializer)
00:00 TRACE: [kryo] Register class ID 2: float (com.esotericsoftware.kryo.serializers.DefaultSerializers$FloatSerializer)
00:00 TRACE: [kryo] Register class ID 3: boolean (com.esotericsoftware.kryo.serializers.DefaultSerializers$BooleanSerializer)
00:00 TRACE: [kryo] Register class ID 4: byte (com.esotericsoftware.kryo.serializers.DefaultSerializers$ByteSerializer)
00:00 TRACE: [kryo] Register class ID 5: char (com.esotericsoftware.kryo.serializers.DefaultSerializers$CharSerializer)
00:00 TRACE: [kryo] Register class ID 6: short (com.esotericsoftware.kryo.serializers.DefaultSerializers$ShortSerializer)
00:00 TRACE: [kryo] Register class ID 7: long (com.esotericsoftware.kryo.serializers.DefaultSerializers$LongSerializer)
00:00 TRACE: [kryo] Register class ID 8: double (com.esotericsoftware.kryo.serializers.DefaultSerializers$DoubleSerializer)
00:00 TRACE: [kryo] Register class ID 9: void (com.esotericsoftware.kryo.serializers.DefaultSerializers$VoidSerializer)
00:00 TRACE: [kryo] Register class ID 10: scala.Enumeration$Val (com.romix.scala.serialization.kryo.EnumerationSerializer)
00:00 TRACE: [kryo] Register class ID 11: scala.Enumeration$Value (com.romix.scala.serialization.kryo.EnumerationSerializer)
00:00 TRACE: [kryo] Registration required: false
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Field persistenceId: class java.lang.String
00:00 TRACE: [kryo] Field sequenceNr: long
00:00 TRACE: [kryo] Field timestamp: long
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Field persistenceId: class java.lang.String
00:00 TRACE: [kryo] Field sequenceNr: long
00:00 TRACE: [kryo] Field timestamp: long
00:00 TRACE: [kryo] Register class ID 113: akka.persistence.SnapshotMetadata (com.esotericsoftware.kryo.serializers.FieldSerializer)
00:00 TRACE: [kryo] Register class ID 49: scala.collection.immutable.List (com.romix.scala.serialization.kryo.ScalaCollectionSerializer)
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Field fName: class java.lang.String
00:00 TRACE: [kryo] Field lName: class java.lang.String
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Field fName: class java.lang.String
00:00 TRACE: [kryo] Field lName: class java.lang.String
00:00 TRACE: [kryo] Register class ID 56: com.romix.akka.serialization.kryo.Person (com.esotericsoftware.kryo.serializers.FieldSerializer)
00:00 TRACE: [kryo] Register class ID 48: scala.collection.immutable.$colon$colon (com.romix.scala.serialization.kryo.ScalaCollectionSerializer)
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Field data: class java.lang.Object
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Field data: class java.lang.Object
00:00 TRACE: [kryo] Register class ID 108: akka.persistence.serialization.Snapshot (com.esotericsoftware.kryo.serializers.FieldSerializer)
00:00 TRACE: [kryo] References: false
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Field 'v' of type class cats.data.Validated of generic type cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object>
00:00 TRACE: [kryo] Processing actual type cats.data.NonEmptyList<java.lang.String> (sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl)
00:00 TRACE: [kryo] Processing actual type class java.lang.Object (java.lang.Class)
00:00 TRACE: [kryo] Class cats.data.Validated has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for E is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: java.lang.Object
00:00 TRACE: [kryo] Generics scope of field 'v' of class cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object> is {A=class java.lang.Object, E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'v' to be cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object> where type parameters are [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Field 'v' of type class cats.data.Validated of generic type cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object>
00:00 TRACE: [kryo] Processing actual type cats.data.NonEmptyList<java.lang.String> (sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl)
00:00 TRACE: [kryo] Processing actual type class java.lang.Object (java.lang.Class)
00:00 TRACE: [kryo] Class cats.data.Validated has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for E is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: java.lang.Object
00:00 TRACE: [kryo] Generics scope of field 'v' of class cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object> is {A=class java.lang.Object, E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'v' to be cats.data.Validated<cats.data.NonEmptyList<java.lang.String>, java.lang.Object> where type parameters are [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Register class ID 415019382: com.romix.akka.serialization.kryo.ValidatedWrapper (com.esotericsoftware.kryo.serializers.FieldSerializer)
Implicitly registered class with id: com.romix.akka.serialization.kryo.ValidatedWrapper=415019382
00:00 TRACE: [kryo] Write class 415019382: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 DEBUG: [kryo] Write: ValidatedWrapper(Valid(1))
00:00 TRACE: [kryo] FieldSerializer.write fields of class: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 TRACE: [kryo] Write field: v (com.romix.akka.serialization.kryo.ValidatedWrapper) pos=5
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Class cats.data.Validated$Valid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Trying to use kryo.getGenericScope
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {}
00:00 TRACE: [kryo] Field 'a' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'a' of class A is null
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Class cats.data.Validated$Valid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Trying to use kryo.getGenericScope
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {}
00:00 TRACE: [kryo] Field 'a' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'a' of class A is null
00:00 TRACE: [kryo] Register class ID 1209028387: cats.data.Validated$Valid (com.esotericsoftware.kryo.serializers.FieldSerializer)
Implicitly registered class with id: cats.data.Validated$Valid=1209028387
00:00 TRACE: [kryo] Write class 1209028387: cats.data.Validated$Valid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Valid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'a' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'a' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'a' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: Valid(1)
00:00 TRACE: [kryo] FieldSerializer.write fields of class: cats.data.Validated$Valid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Valid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'a' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'a' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'a' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: a (cats.data.Validated$Valid) pos=10
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Trying to use kryo.getGenericScope
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Trying to use kryo.getGenericScope
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Register class ID 413260567: cats.data.NonEmptyList (com.esotericsoftware.kryo.serializers.FieldSerializer)
Implicitly registered class with id: cats.data.NonEmptyList=413260567
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Write: 1
00:00 TRACE: [kryo] FieldSerializer.write fields of class: java.lang.Integer
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head (java.lang.Integer) pos=11
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Object graph complete.
00:00 TRACE: [kryo] Write class 415019382: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 DEBUG: [kryo] Write: ValidatedWrapper(Valid(1))
00:00 TRACE: [kryo] FieldSerializer.write fields of class: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 TRACE: [kryo] Write field: v (com.romix.akka.serialization.kryo.ValidatedWrapper) pos=5
00:00 TRACE: [kryo] Write class 1209028387: cats.data.Validated$Valid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Valid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'a' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'a' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'a' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: Valid(1)
00:00 TRACE: [kryo] FieldSerializer.write fields of class: cats.data.Validated$Valid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Valid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'a' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'a' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'a' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Valid: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: a (cats.data.Validated$Valid) pos=10
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Write: 1
00:00 TRACE: [kryo] FieldSerializer.write fields of class: java.lang.Integer
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head (java.lang.Integer) pos=11
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Object graph complete.
serialization.serialize(v1) = Failure(com.esotericsoftware.kryo.KryoException: java.lang.NullPointerException
Serialization trace:
head (java.lang.Integer)
a (cats.data.Validated$Valid)
v (com.romix.akka.serialization.kryo.ValidatedWrapper))
v2 = ValidatedWrapper(Invalid(NonEmptyList(errrr)))
00:00 TRACE: [kryo] Write class 415019382: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 DEBUG: [kryo] Write: ValidatedWrapper(Invalid(NonEmptyList(errrr)))
00:00 TRACE: [kryo] FieldSerializer.write fields of class: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 TRACE: [kryo] Write field: v (com.romix.akka.serialization.kryo.ValidatedWrapper) pos=5
00:00 TRACE: [kryo] Optimize ints: true
00:00 TRACE: [kryo] Class cats.data.Validated$Invalid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Trying to use kryo.getGenericScope
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {}
00:00 TRACE: [kryo] Field 'e' of type class java.lang.Object of generic type E
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'e' of class E is null
00:00 TRACE: [kryo] setIgnoreSyntheticFields: false
00:00 TRACE: [kryo] Class cats.data.Validated$Invalid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Trying to use kryo.getGenericScope
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {}
00:00 TRACE: [kryo] Field 'e' of type class java.lang.Object of generic type E
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'e' of class E is null
00:00 TRACE: [kryo] Register class ID 35569944: cats.data.Validated$Invalid (com.esotericsoftware.kryo.serializers.FieldSerializer)
Implicitly registered class with id: cats.data.Validated$Invalid=35569944
00:00 TRACE: [kryo] Write class 35569944: cats.data.Validated$Invalid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Invalid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for E is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'e' of type class java.lang.Object of generic type E
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'e' of class E is {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'e' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: Invalid(NonEmptyList(errrr))
00:00 TRACE: [kryo] FieldSerializer.write fields of class: cats.data.Validated$Invalid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Invalid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for E is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'e' of type class java.lang.Object of generic type E
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'e' of class E is {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'e' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: e (cats.data.Validated$Invalid) pos=9
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: NonEmptyList(errrr)
00:00 TRACE: [kryo] FieldSerializer.write fields of class: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head (cats.data.NonEmptyList) pos=10
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Write: errrr
00:00 TRACE: [kryo] FieldSerializer.write fields of class: java.lang.String
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head (java.lang.String) pos=11
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: char[]
00:00 TRACE: [kryo] FieldSerializer.write fields of class: [C
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head ([C) pos=12
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Object graph complete.
00:00 TRACE: [kryo] Write class 415019382: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 DEBUG: [kryo] Write: ValidatedWrapper(Invalid(NonEmptyList(errrr)))
00:00 TRACE: [kryo] FieldSerializer.write fields of class: com.romix.akka.serialization.kryo.ValidatedWrapper
00:00 TRACE: [kryo] Write field: v (com.romix.akka.serialization.kryo.ValidatedWrapper) pos=5
00:00 TRACE: [kryo] Write class 35569944: cats.data.Validated$Invalid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Invalid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for E is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'e' of type class java.lang.Object of generic type E
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'e' of class E is {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'e' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: Invalid(NonEmptyList(errrr))
00:00 TRACE: [kryo] FieldSerializer.write fields of class: cats.data.Validated$Invalid
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList, class java.lang.Object]
00:00 TRACE: [kryo] Class cats.data.Validated$Invalid has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=E type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for E is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'e' of type class java.lang.Object of generic type E
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'e' of class E is {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'e' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.Validated$Invalid: {E=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: e (cats.data.Validated$Invalid) pos=9
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: NonEmptyList(errrr)
00:00 TRACE: [kryo] FieldSerializer.write fields of class: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head (cats.data.NonEmptyList) pos=10
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Write: errrr
00:00 TRACE: [kryo] FieldSerializer.write fields of class: java.lang.String
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head (java.lang.String) pos=11
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 DEBUG: [kryo] Write: char[]
00:00 TRACE: [kryo] FieldSerializer.write fields of class: [C
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Write field: head ([C) pos=12
00:00 TRACE: [kryo] Generic type parameters: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Class cats.data.NonEmptyList has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Settting a new generics scope for class cats.data.NonEmptyList: {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Field 'head' of type class java.lang.Object of generic type A
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
00:00 TRACE: [kryo] Generics scope of field 'head' of class A is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of 'head' to be cats.data.NonEmptyList
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field 'tail' of type class scala.collection.immutable.List of generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Field generic type is of class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
00:00 TRACE: [kryo] Processing generic type scala.collection.immutable.List<A>
00:00 TRACE: [kryo] Processing actual type A (sun.reflect.generics.reflectiveObjects.TypeVariableImpl)
00:00 TRACE: [kryo] Class scala.collection.immutable.List has generic type parameters
00:00 TRACE: [kryo] Type parameter variable: name=A type bounds=[class java.lang.Object]
00:00 TRACE: [kryo] Concrete type used for A is: cats.data.NonEmptyList
00:00 TRACE: [kryo] Generics scope of field 'tail' of class scala.collection.immutable.List<A> is {A=class cats.data.NonEmptyList}
00:00 TRACE: [kryo] Determined concrete class of parametrized 'tail' to be scala.collection.immutable.List<A> where type parameters are [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Field generics: [class cats.data.NonEmptyList]
00:00 TRACE: [kryo] Object graph complete.
serialization.serialize(v2) = Failure(com.esotericsoftware.kryo.KryoException: java.lang.NullPointerException
Serialization trace:
head ([C)
head (java.lang.String)
head (cats.data.NonEmptyList)
e (cats.data.Validated$Invalid)
v (com.romix.akka.serialization.kryo.ValidatedWrapper))
oooooooooooooo    OK    oooooooooooooo
[success] Total time: 6 s, completed 12-Oct-2016 17:22:42
```
