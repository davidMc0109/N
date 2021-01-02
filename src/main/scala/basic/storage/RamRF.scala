package basic.storage

import chisel3._

/**
 * A Register File Implemented by BRAM
 * PORTS:
 *    enable:  true for selection, false for unselect, also output 0
 *    writeEnable:  write data, true for write, false do nothing
 *    writeData:    data to be wrote
 *    writeAddr:    addr to be wrote
 *    readEnable:   read data, true for data, false for DontCare,
 *                  TODO: solve below
 *                  if the corresponding cell is being written, behavior not known
 *    readData:     data to be read or DontCare
 *    readAddr:     addr to be read
 * @param depth
 * @param dWidth
 * @param aWidth
 */
class RamRF(val depth: Int, val dWidth: Int, val aWidth: Int) extends Module{
  val io = IO(new Bundle() {
    val enable = Input(Bits(1.W))

    val writeEnable = Input(Bits(1.W))
    val writeAddr = Input(Bits(aWidth.W))
    val writeData = Input(Bits(dWidth.W))

    val readEnable = Input(Bits(1.W))
    val readAddr = Input(Bits(aWidth.W))
    val readData = Output(Bits(dWidth.W))
  })

  val u = SyncReadMem(depth, Bits(dWidth.W))

  when(io.enable.asBool()){
    io.readData := u.read(io.readAddr, io.readEnable.asBool())
    when(io.writeEnable.asBool()) {
      u.write(io.writeAddr, io.writeData)
    }
  }.otherwise{
    io.readData := 0.U(dWidth.W)
  }
}
