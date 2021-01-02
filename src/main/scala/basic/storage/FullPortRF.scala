package basic.storage

import chisel3._

class FullPortRF(val depth: Int, val dWidth: Int, val aWidth: Int) extends Module {
  val io = IO(new Bundle() {
    val enable = Input(Bits(1.W))

    val writeEnable = Input(Vec(depth, Bits(1.W)))
    val writeData = Input(Vec(depth, Bits(dWidth.W)))

    val readEnable = Input(Vec(depth, Bits(1.W)))
    val readData = Output(Vec(depth, Bits(dWidth.W)))

  })

  var i = 0

  val regs = RegInit(VecInit(Seq.fill(depth)(0.U(dWidth.W))))

  for(i <- 0 until depth) {
    when(io.enable.asBool()){
      when(io.readEnable(i).asBool() && io.writeEnable(i).asBool()){
        io.readData(i) := io.writeData(i)
        regs(i) := io.writeData(i)
      }.elsewhen(io.readEnable(i).asBool() && (!io.writeEnable(i).asBool())){
        io.readData(i) := regs(i)
      }.elsewhen((!io.readEnable(i).asBool()) && (io.writeEnable(i).asBool())){
        regs(i) := io.writeData(i)
        io.readData(i) := 0.U(dWidth.W)
      }.otherwise{
        io.readData(i) := 0.U(dWidth.W)
      }
    }.otherwise{
      io.readData(i) := 0.U(dWidth.W)
    }
  }
}
