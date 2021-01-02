package pe

import chisel3._

class PE extends Module{
  val numDataBusSlave = 4
  val numDataBusMaster = 2
  val aWidth = 10
  val dWidth = 16
  val instructionWidth = 16

  val io = IO(new Bundle() {
    // including filterIn, featureMapIn, pSumOutToGLB, pSumOutToBelow
    // acting as slave mode
    val dataBusSlave = Seq.fill(numDataBusSlave)(
      new Bundle(){
        val enable = Input(Bits(1.W))
        val addr = Input(Bits(aWidth.W))
        val write = Input(Bits(1.W))
        val dataIn = Input(Bits(dWidth.W))
        val dataOut = Output(Bits(dWidth.W))
      }
    )

    // including pSumIn, maybe shared data ram
    // acting as master mode
    val dataBusMaster = Seq.fill(numDataBusMaster)(
      new Bundle(){
        val ready = Input(Bits(1.W))
        val enable = Output(Bits(1.W))
        val addr = Output(Bits(aWidth.W))
        val write = Output(Bits(1.W))
        val dataIn = Input(Bits(dWidth.W))
        val dataOut = Output(Bits(dWidth.W))
      }
    )

    // accessing to the shared instruction area
    val instructionBus = new Bundle() {
      val enable = Output(Bits(1.W))
      val addr = Output(Bits(aWidth.W))
      val dataIn = Input(Bits(instructionWidth.W))

      // default not allowed to change the instruction from pe, but keep for more flexibility
      val write = Output(Bits(1.W))
      val dataOut = Output(Bits(instructionWidth.W))
    }

    // direct instruction from above, for more flexible control
    val directInstruction = new Bundle() {
      val enable = Input(Bits(1.W))
      val dataIn = Input(Bits(instructionWidth.W))

      // keep for passing instructions between PEs, default disabled
      val dataOut = Output(Bits(instructionWidth.W))
    }

    // control signals
    val controls = new Bundle() {
      val enable = Input(Bits(1.W))
      val clearError = Input(Bits(1.W))
    }

    // PE status
    val status = new Bundle() {
      val idle = Output(Bits(1.W))
      val running = Output(Bits(1.W))
      val error = Output(Bits(1.W))
      val pc = Output(Bits(aWidth.W))
    }
  })


}
