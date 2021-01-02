package basic.storage

// unTODO: implement a RF with multi port, multiple read and single write
//       this will be used as a shared instruction area

import chisel3._

class MultiPortRF(val depth: Int, val dWidth: Int, val aWidth: Int, val outPortNum: Int) extends Module{
  val io = IO(new Bundle() {
    val enable = Input(Bits(1.W))

    val writeEnable = Input(Bits(1.W))
    val writeAddr = Input(Bits(aWidth.W))
    val writeData = Input(Bits(dWidth.W))

    val readEnable = Input(Vec(outPortNum, Bits(1.W)))
    val readAddr = Input(Vec(outPortNum, Bits(aWidth.W)))
    val readData = Output(Vec(outPortNum, Bits(dWidth.W)))
  })

  var i = 0

  val regs = RegInit(VecInit(Seq.fill(depth)(0.U(dWidth.W))))

  when(io.enable.asBool()){
    // read
    for(i <- 0 until outPortNum){
      when(io.readEnable(i).asBool()){
        io.readData(i) := regs(io.readAddr(i))
      }.otherwise{
        io.readData(i) := 0.U(dWidth.W)
      }
    }

    // write
    when(io.writeEnable.asBool()) {
      regs(io.writeAddr) := io.writeData
    }

  }.otherwise{
    for(i <- 0 until outPortNum){
      io.readData(i) := 0.U(dWidth.W)
    }
  }

}
