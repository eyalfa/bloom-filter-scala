package bloomfilter.kryo.mutable

import bloomfilter.mutable.{UnsafeBitArray => UBA}
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}

/**
  * Created by eyal on 25/03/17.
  */
object UnsafeBitArray {

  object Serializer extends com.esotericsoftware.kryo.Serializer[UBA]{
    override def write(kryo: Kryo, output: Output, inst: UBA): Unit = {
      output.writeLong(inst.numberOfBits)
      inst.writeTo(output)
    }

    override def read(kryo: Kryo, input: Input, `type`: Class[UBA]): UBA = {
      val numberOfBits = input.readLong()
      val inst = new UBA(numberOfBits)
      inst.readFrom(input)
      inst
    }
  }

  def register( kryo : Kryo ) = {
    kryo.register(classOf[UBA]).setSerializer(Serializer)
  }

}
