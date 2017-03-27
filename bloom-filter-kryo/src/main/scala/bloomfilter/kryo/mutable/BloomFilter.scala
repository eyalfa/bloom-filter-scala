package bloomfilter.kryo.mutable

import bloomfilter.mutable.{BloomFilter => BF}
import com.esotericsoftware.kryo.Kryo
import kryo.bloomfilter.CanGenerateHashFrom
import org.objenesis.strategy.StdInstantiatorStrategy

/**
  * Created by eyal on 25/03/17.
  */
object BloomFilter {
  def register( kryo : Kryo ) = {
    kryo.register(classOf[BF[_]]).setInstantiator(new StdInstantiatorStrategy().newInstantiatorOf(classOf[BF[_]]))
    UnsafeBitArray.register(kryo)
    CanGenerateHashFrom.register(kryo)
  }
}
