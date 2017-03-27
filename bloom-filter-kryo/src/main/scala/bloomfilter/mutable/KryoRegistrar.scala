package bloomfilter.mutable

import bloomfilter.CanGenerateHashFrom.{CanGenerateHashFromByteArray, CanGenerateHashFromLong, CanGenerateHashFromString}
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import com.twitter.chill.{IKryoRegistrar, ObjectSerializer}
import org.objenesis.strategy.StdInstantiatorStrategy

import scala.reflect.ClassTag

/**
  * Created by eyal on 27/03/17.
  */
object KryoRegistrar extends IKryoRegistrar {
  import scala.reflect.classTag
  override def apply(k: Kryo): Unit = {
    implicit val k_ = k
    k.register(classOf[UnsafeBitArray]).setSerializer(UnsafeBitArraySerializer)
    registerWithStdInstantiator[BloomFilter[_]]
    registerObj[CanGenerateHashFromLong.type]
    registerObj[CanGenerateHashFromByteArray.type]
    registerObj[CanGenerateHashFromString.type]
  }

  val stdInstantiatorStrategy = new StdInstantiatorStrategy()
  private def registerWithStdInstantiator[A : ClassTag](implicit k : Kryo) = {
    k.register(classTag[A].runtimeClass).setInstantiator(stdInstantiatorStrategy.newInstantiatorOf(classTag[A].runtimeClass))
  }

  private def registerObj[T : ClassTag](implicit k : Kryo) = {
    k.register(classTag[T].runtimeClass).setSerializer( new ObjectSerializer[T] )
  }

  case object UnsafeBitArraySerializer extends com.esotericsoftware.kryo.Serializer[UnsafeBitArray]{
    override def write(kryo: Kryo, output: Output, inst: UnsafeBitArray): Unit = {
      output.writeLong(inst.numberOfBits)
      inst.writeTo(output)
    }

    override def read(kryo: Kryo, input: Input, `type`: Class[UnsafeBitArray]): UnsafeBitArray = {
      val numberOfBits = input.readLong()
      val inst = new UnsafeBitArray(numberOfBits)
      inst.readFrom(input)
      inst
    }
  }
}
