package basic.storage

import chisel3._

// TODO:  finish it
//        1. addr[x:2]
//        2. generate mask
//        3. consider cancel mask input, change to byte select
//        4. dataIn dataOut wrap/unwrap
class MaskRam(val depth: Int, val wordWidth: Int, val wordNum: Int, val aWidth: Int) extends Module{
  val dWidth = wordWidth*wordNum
  val io = IO(new Bundle() {
    val enable = Input(Bits(1.W))
    val write = Input(Bits(1.W))
    val addr = Input(Bits(aWidth.W))
    val mask = Input(Bits(wordNum.W))
    val dataIn = Input(Bits(dWidth.W))
    val dataOut = Output(Bits(dWidth.W))
  })


  val u = SyncReadMem(depth, Vec(wordNum, Bits(wordWidth.W)))


  when(io.enable.asBool()){
    when(io.write.asBool()){

    }
  }.otherwise{

  }

}
