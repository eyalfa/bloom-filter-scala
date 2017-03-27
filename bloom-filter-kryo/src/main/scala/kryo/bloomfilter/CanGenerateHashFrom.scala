package kryo.bloomfilter

import bloomfilter.{CanGenerateHashFrom => CGHF}
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}

/**
  * Created by eyal on 26/03/17.
  */
object CanGenerateHashFrom {

  class Serializer( inst : CGHF[_] with Serializable ) extends com.esotericsoftware.kryo.Serializer[CGHF[_] with Serializable]{
    override def write(kryo: Kryo, output: Output, `object`: CGHF[_] with Serializable): Unit = {}

    override def read(kryo: Kryo, input: Input, `type`: Class[CGHF[_] with Serializable]): CGHF[_] with Serializable = inst

    def register( kryo : Kryo ) = {
      kryo.register(inst.getClass).setSerializer(this)
    }
  }

  case object CanGenerateHashFromLong extends Serializer(CGHF.CanGenerateHashFromLong)
  case object CanGenerateHashFromByteArray extends Serializer(CGHF.CanGenerateHashFromByteArray)
  case object CanGenerateHashFromString extends Serializer(CGHF.CanGenerateHashFromString)

  def register( kryo : Kryo ) = {
    CanGenerateHashFromLong.register(kryo)
    CanGenerateHashFromByteArray.register(kryo)
    CanGenerateHashFromString.register(kryo)
  }

}
